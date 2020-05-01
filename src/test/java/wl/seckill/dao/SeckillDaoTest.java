package wl.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wl.seckill.entity.Seckill;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * junit需要的IOC和配置文件
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SeckillDaoTest {
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() {
        Date killTime = new Date();
        int i = seckillDao.reduceNumber(1000l, killTime);
        System.out.println("i : " + i);
    }

    @Test
    public void queryById() {
        long id = 1000l;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
        //对于多个参数，Java没有绑定参数名字的习惯。
        //List<Seckill> queryAll(int offset, int limit); --》 List<Seckill> queryAll(int arg1, int arg2);
        //所以在接口的方法中要使用@param绑定
        List<Seckill> list = seckillDao.queryAll(0, 10);
        for (Seckill seckill :
                list) {
            System.out.println(seckill);
        }
    }
}