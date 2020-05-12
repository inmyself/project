package wl.seckill.dao;

import org.apache.ibatis.annotations.Param;
import wl.seckill.entity.SuccessKilled;

import java.util.List;

public interface OrderDao {

    /***
     * 根据手机号查询订单(分页）
     * @param userPhone
     * @return 秒杀记录列表
     */
    List<SuccessKilled> queryByPhone(@Param("userPhone") long userPhone, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 删除购买记录
     * @param userPhone
     * @param seckillId
     * @return 影响行数
     */
    int deleteBySeckill(@Param("userPhone") long userPhone, @Param("seckillId") long seckillId);

    /**
     * 查询用户订单总数
     * @param userPhone
     * @return 行数
     */
    int countByPhone(@Param("userPhone") long userPhone);
}
