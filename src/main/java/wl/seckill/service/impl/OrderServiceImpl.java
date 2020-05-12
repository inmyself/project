package wl.seckill.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import wl.seckill.dao.OrderDao;
import wl.seckill.dto.ResultInfo;
import wl.seckill.dto.SeckillList;
import wl.seckill.entity.SuccessKilled;
import wl.seckill.enums.OrderStateEnum;
import wl.seckill.service.OrderService;

import java.util.List;

/**
 * create by wule on 2020/5/11
 */
@Service
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderDao orderDao;

    @Override
    public SeckillList<SuccessKilled> queryOrder(long userPhone, int page) {
        int count = orderDao.countByPhone(userPhone);
        if (page > (count / 10))
            page = count / 10;
        else if (page < 0)
            page = 0;
        List<SuccessKilled> list = orderDao.queryByPhone(userPhone, page * 10, 10);
        return new SeckillList<>(page + 1, list);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ResultInfo deleteOrder(long userPhone, long seckillId) {
        try {
            int i = orderDao.deleteBySeckill(userPhone, seckillId);
            if (i > 0){//删除成功
                return new ResultInfo(true, OrderStateEnum.DELETE_SUCCESS);
            }else {//删除错误
                return new ResultInfo(true, OrderStateEnum.DELETE_ERROR);
            }
        }catch (Exception e){
            logger.error("order error : " + e.getMessage(), e);
            throw new RuntimeException(OrderStateEnum.DELETE_ERROR.getStateInfo(), e);
        }
    }
}
