package wl.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wl.seckill.dto.SeckillList;
import wl.seckill.entity.SuccessKilled;
import wl.seckill.enums.OrderStateEnum;
import wl.seckill.service.OrderService;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void queryOrder() {
        long phone = 12345678901l;
        int page = 1;
        try {
            SeckillList<SuccessKilled> list = orderService.queryOrder(phone, page);
            System.out.println(list.getSeckillList());
        }catch (Exception e){
            throw new RuntimeException(OrderStateEnum.DELETE_ERROR.getStateInfo(), e);
        }
    }

    @Test
    public void deleteOrder() {
    }
}