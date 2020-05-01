package wl.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import wl.seckill.dao.SeckillDao;
import wl.seckill.dao.SuccessKilledDao;
import wl.seckill.dao.cache.RedisDao;
import wl.seckill.dto.Exposer;
import wl.seckill.dto.SeckillExecution;
import wl.seckill.entity.Seckill;
import wl.seckill.entity.SuccessKilled;
import wl.seckill.enums.SeckillSatteEnum;
import wl.seckill.exception.RepeatKillException;
import wl.seckill.exception.SeckillClosedException;
import wl.seckill.exception.SeckillException;
import wl.seckill.service.SeckillService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //md5的混合字符，自定义，用来和秒杀地址拼接然后加密
    final private String slat = "asdhfoi34r90()78*&8r93W#RWERTEW$#$%#@";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 10);
    }

    @Override
    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        //数据查询优化：redis
        // 1.查询 redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null){
            //2. 查询MySQL
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null)
                return new Exposer(false, seckillId);
            else {
                //seckill放入redis
                redisDao.putSeckill(seckill);
            }
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date now = new Date();
        if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime())//不在秒杀时间范围
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());

        //转化特定字符串的过程，不可逆
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Override
    /**
     * 保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP
     * 不是所有的方法都需要事务，如果只有一条修改操作、只读操作不需要事务
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillClosedException {
        if (md5 == null || !md5.equals(getMD5(seckillId)))
            throw new SeckillException("seckillId was rewrite");
        //执行秒杀：减库存+记录购买行为 ： 优化 -> 记录购买行为 + 减库存
        Date nowTime = new Date();
        try {
            //记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {//重复购买
                throw new RepeatKillException("repeat kill");
            } else {//减库存
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0)//减库存失败，秒杀结束
                    throw new SeckillClosedException("seckill is closed");
                else {//减库存成功，秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillSatteEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillClosedException e1) {//捕获前面抛出的自定义异常子类，防止后面同化成自定义异常父类
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }

    /**
     * 使用存储过程执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillClosedException
     */
    @Override
    public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5)  {
        if (md5 == null || !md5.equals(getMD5(seckillId)))
            return new SeckillExecution(seckillId, SeckillSatteEnum.DATA_REWRITE);
        Date killTime = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("seckillId", seckillId);
        map.put("userPhone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);
        try {
            //执行存储过程，result被赋值
            seckillDao.killByProcedure(map);
            Integer result = MapUtils.getInteger(map, "result", -2);
            if (result == 1){
                SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillSatteEnum.SUCCESS, successKilled);
            }else {
                //根据错误结果返回
                return new SeckillExecution(seckillId, SeckillSatteEnum.stateOf(result));
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillSatteEnum.INNER_ERROR);
        }
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
