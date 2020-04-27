package wl.seckill.dao;

import wl.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 影响的行数
     */
    int reduceNumber(long seckillId, Date killTime);

    /**
     * 根据id查询秒杀商品
     * @param seckillId
     * @return 秒杀商品类型
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表（分页）
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(int offset, int limit);
}
