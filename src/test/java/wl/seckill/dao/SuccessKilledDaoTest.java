package wl.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wl.seckill.entity.SuccessKilled;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        int i = successKilledDao.insertSuccessKilled(1001l, 12345678901l);
        System.out.println("影响行数：" + i);
    }

    @Test
    public void queryByIdWithSeckill() {
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1000l, 12345678901l);
        System.out.println(successKilled);
    }
}