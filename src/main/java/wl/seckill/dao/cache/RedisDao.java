package wl.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import wl.seckill.entity.Seckill;

/**
 * Redis缓存数据
 * create by wule on 2020/4/30
 */
public class RedisDao {
    //连接池对象
    private final JedisPool jedisPool;

    //日志
    private Logger logger = LoggerFactory.getLogger(RedisDao.class);

    //自定义序列化约束
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    /**
     * 从缓存拿Seckill
     *
     * @param seckillId
     * @return
     */
    public Seckill getSeckill(long seckillId) {
        //redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //使用自定义序列化
                //get -> byte[] -> 反序列化 -> Object(Seckill)
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    //空对象
                    Seckill seckill = schema.newMessage();
                    //反序列化为Seckill
                    ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将Seckill对象存入redis
     *
     * @param seckill
     * @return
     */
    public String putSeckill(Seckill seckill) {
        try {
            try (Jedis jedis = jedisPool.getResource()) {
                try {
                    String key = "seckill:" + seckill.getSeckillId();
                    byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema,
                            LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                    //超时缓存
                    //采用固定+随机值，防止key在同一时间全部失效，致使请求全部落在数据库上，从而引发缓存雪崩
                    int random = (int) (Math.random() * 60 * 10);//0-10分钟随机值
                    int timeout = 60 * 60 + random;
                    String result = jedis.setex(key.getBytes(), timeout, bytes);
                } finally {
                    jedis.close();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
