package wl.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wl.seckill.dao.SeckillDao;
import wl.seckill.entity.Seckill;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    private long seckillId = 1000l;

    /**
     * 测试redis缓存
     */
    @Test
    public void redisLogic(){
        //get and put
        Seckill seckill = redisDao.getSeckill(seckillId);
        System.out.println("redis1:" + seckill);
        if (seckill == null){
            seckill = seckillDao.queryById(seckillId);
            System.out.println("mysql:" + seckill);
            if (seckill != null){
                redisDao.putSeckill(seckill);
            }
            seckill = redisDao.getSeckill(seckillId);
            System.out.println("redis2:" + seckill);
        }
    }
}