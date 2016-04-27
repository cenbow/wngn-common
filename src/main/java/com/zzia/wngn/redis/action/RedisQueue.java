package com.zzia.wngn.redis.action;

import com.zzia.wngn.redis.client.RedisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;


public class RedisQueue {

    public static Logger logger = LoggerFactory.getLogger(RedisQueue.class);

    public final static RedisQueue INSTANCE = new RedisQueue();

    public final static RedisQueue getInstance() {
        return INSTANCE;
    }

    public String pop(String key) {
        Jedis jedis = RedisConnection.getInstance("queue");
        if (jedis == null) {
            logger.warn("get connection faild");
            return null;
        }

        try {
            String line = jedis.rpop(key);
            return line;
        } catch (Exception e) {
            logger.warn("queue pop faild");
            return null;
        } finally {
            RedisConnection.close("queue", jedis);
        }
    }

    public boolean push(String key, String value) {
        Jedis jedis = RedisConnection.getInstance("queue");
        if (jedis == null) {
            logger.warn("get connection faild");
            return false;
        }
        try {
            jedis.lpush(key, value);
            return true;
        } catch (Exception e) {
            logger.warn("queue push faild");
            return false;
        } finally {
            RedisConnection.close("queue", jedis);
        }
    }
}
