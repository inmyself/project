package wl.seckill.service;

import wl.seckill.dto.Exposer;
import wl.seckill.dto.SeckillExecution;
import wl.seckill.dto.SeckillList;
import wl.seckill.entity.Seckill;
import wl.seckill.exception.RepeatKillException;
import wl.seckill.exception.SeckillClosedException;
import wl.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口， 站在“使用者”角度实现
 * 1.方法粒度， 2. 参数， 3.返回类型（类型/异常)
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    SeckillList<Seckill> getSeckillList(Integer page);

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getSeckillById(long seckillId);

    /**
     * 秒杀开启输出秒杀接口地址
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException, RepeatKillException, SeckillClosedException;

    /**
     * 执行秒杀, 存储过程
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);
}
