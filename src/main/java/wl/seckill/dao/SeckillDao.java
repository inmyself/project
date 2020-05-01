package wl.seckill.dao;

import org.apache.ibatis.annotations.Param;
import wl.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 影响的行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

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
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 使用存储过程执行秒杀
     */
    void killByProcedure(Map<String, Object> paramMap);
}
