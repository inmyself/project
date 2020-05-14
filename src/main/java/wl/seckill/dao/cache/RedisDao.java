package wl.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import wl.seckill.entity.Seckill;

import java.util.ArrayList;
import java.util.List;

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
    public void putSeckill(Seckill seckill) {
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
    }

    /**
     * 从缓存中拿首页分页展示信息
     * @param page
     */
    public List<Seckill> getSeckillList(int page){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:page" + page;
                List<byte[]> list = jedis.lrange(key.getBytes(), page * 10, 10);
                List<Seckill> res = new ArrayList<>();
                if (list != null){
                    for (byte[] bytes : list){
                        Seckill seckill = schema.newMessage();
                        ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
                        res.add(seckill);
                    }
                    if (res.size() == 0)
                        return null;
                    return res;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 首页分页信息存入Redis
     * @param list
     */
    public void putSeckillList(int page, List<Seckill> list){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:page" + page;
                for (Seckill seckill :
                        list) {
                    byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema,
                            LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                    jedis.rpush(key.getBytes(), bytes);
                }
                int random = (int) (Math.random() * 60 * 10);//0-10分钟随机值
                int timeout = 60 * 60 * 12 + random;
                jedis.expire(key.getBytes(), timeout);
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }

}
