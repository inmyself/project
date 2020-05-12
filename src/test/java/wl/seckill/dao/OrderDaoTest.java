package wl.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wl.seckill.entity.SuccessKilled;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Test
    public void queryByPhone() {
        long phone = 12345678901l;
        List<SuccessKilled> killeds = orderDao.queryByPhone(phone, 0, 10);
        System.out.println(killeds);
    }

    @Test
    public void deleteBySeckill() {
        long phone = 12345678901l;
        long seckillId = 1001;
        int i = orderDao.deleteBySeckill(phone, seckillId);
        System.out.println("i: " + i);
    }
}