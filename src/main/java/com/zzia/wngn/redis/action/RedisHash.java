package com.zzia.wngn.redis.action;

import com.zzia.wngn.redis.client.RedisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class RedisHash {

    protected static Logger logger = LoggerFactory.getLogger(RedisHash.class);

    public final static RedisHash INSTANCE = new RedisHash();

    public final static RedisHash getInstance() {
        return INSTANCE;
    }

    public boolean put(String key, String field, String value) {
        Jedis jedis = RedisConnection.getInstance("hash");
        if (jedis == null) {
            logger.warn("get connection faild");
            return false;
        }
        try {
            jedis.hset(key, field, value);
            return true;
        } catch (Exception e) {
            logger.warn("hset faild");
            return false;
        } finally {
            RedisConnection.close("hash", jedis);
        }
    }

    public String get(String key, String field) {
        Jedis jedis = RedisConnection.getInstance("hash");
        if (jedis == null) {
            logger.warn("get connection faild");
            return null;
        }
        try {
            return jedis.hget(key, field);
        } catch (Exception e) {
            logger.warn("hset faild");
            return null;
        } finally {
            RedisConnection.close("hash", jedis);
        }
    }

    public boolean remove(String key, String field) {
        Jedis jedis = RedisConnection.getInstance("hash");
        if (jedis == null) {
            logger.warn("get connection faild");
            return false;
        }
        try {
            jedis.hdel(key, field);
            return true;
        } catch (Exception e) {
            logger.warn("hset faild");
            return false;
        } finally {
            RedisConnection.close("hash", jedis);
        }
    }
}
