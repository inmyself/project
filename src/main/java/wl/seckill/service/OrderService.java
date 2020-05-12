package wl.seckill.service;

import wl.seckill.dto.ResultInfo;
import wl.seckill.dto.SeckillList;
import wl.seckill.entity.SuccessKilled;

import java.util.List;

public interface OrderService {

    /**
     * 查询用户订单
     * @param userPhone
     * @param page
     * @return 用户订单分页列表
     */
    SeckillList<SuccessKilled> queryOrder(long userPhone, int page);

    /**
     * 删除用户订单
     * @param userPhone
     * @param seckillId
     * @return 行数
     */
    ResultInfo deleteOrder (long userPhone, long seckillId);
}