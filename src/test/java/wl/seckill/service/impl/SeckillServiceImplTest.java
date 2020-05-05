package wl.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wl.seckill.dto.Exposer;
import wl.seckill.dto.SeckillExecution;
import wl.seckill.dto.SeckillList;
import wl.seckill.entity.Seckill;
import wl.seckill.exception.RepeatKillException;
import wl.seckill.exception.SeckillClosedException;
import wl.seckill.service.SeckillService;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SeckillServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        SeckillList<Seckill> list = seckillService.getSeckillList(0);
        logger.info("list{}", list.getSeckillList());
        //16:34:49.781 [main] INFO  w.s.s.impl.SeckillServiceImplTest - list[
        // Seckill{seckillId=1000, name='1000元秒杀iPhone X', number=99, startTime=Fri Apr 24 00:00:00 CST 2020, endTime=Thu Apr 30 12:00:00 CST 2020, createTime=Fri Apr 24 17:17:20 CST 2020},
        // Seckill{seckillId=1001, name='800元秒杀小米6', number=100, startTime=Fri Dec 13 00:00:00 CST 2019, endTime=Fri Dec 13 12:00:00 CST 2019, createTime=Fri Apr 24 17:17:20 CST 2020},
        // Seckill{seckillId=1002, name='1000元秒杀华为 mate 30', number=100, startTime=Fri Dec 13 00:00:00 CST 2019, endTime=Fri Dec 13 12:00:00 CST 2019, createTime=Fri Apr 24 17:17:20 CST 2020},
        // Seckill{seckillId=1003, name='600元秒杀荣耀 30', number=100, startTime=Fri Dec 13 00:00:00 CST 2019, endTime=Fri Dec 13 12:00:00 CST 2019, createTime=Fri Apr 24 17:17:20 CST 2020},
        // Seckill{seckillId=1004, name='1200元秒杀iPad Air3', number=100, startTime=Fri Dec 13 00:00:00 CST 2019, endTime=Fri Dec 13 12:00:00 CST 2019, createTime=Fri Apr 24 17:17:20 CST 2020}]
    }

    @Test
    public void getSeckillById() {
        long seckillId = 1000l;
        Seckill seckill = seckillService.getSeckillById(seckillId);
        logger.info("seckill{}", seckill);
        //16:37:53.085 [main] INFO  w.s.s.impl.SeckillServiceImplTest -
        // seckillSeckill{seckillId=1000, name='1000元秒杀iPhone X', number=99, startTime=Fri Apr 24 00:00:00 CST 2020, endTime=Thu Apr 30 12:00:00 CST 2020, createTime=Fri Apr 24 17:17:20 CST 2020}
    }

    @Test
    public void seckillLogic() {
        long seckillId = 1000l;

        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){
            long userPhone = 12345678901l;
            String md5 = exposer.getMd5();
            logger.info("exposer{}", exposer);
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                logger.info("seckillExecution{}", seckillExecution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage());
            }catch (SeckillClosedException e){
                logger.error(e.getMessage());
            }
        }else {
            logger.warn("exposer={}", exposer);
        }
    }

    @Test
    public void executeSeckill() {
        long seckillId = 1000l;
        long userPhone = 12345678901l;
        String md5 = "5aa9ab9a55cb0cb01ed823c6a5e87643";
        SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
        logger.info("seckillExecution", seckillExecution);
    }

    @Test
    public void executeSeckillProcedure(){
        long seckiiId = 1001l;
        long userPhone = 12345678901l;
        Exposer exposer = seckillService.exportSeckillUrl(seckiiId);
        if (exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckiiId, userPhone, md5);
            logger.info(seckillExecution.getStateInfo());
        }
    }
}