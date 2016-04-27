package com.zzia.wngn.redis.client;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zzia.wngn.util.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisConnection {

    private static Map<String, RedisSource> REDIS_SOURCE_MAP = new ConcurrentHashMap<String, RedisSource>();

    static {
        Configuration conf = Configuration.getInstance();
        String namesString = conf.getString("redis.names", "main");
        String[] names = namesString.split(",");
        for (String name : names) {
            RedisSource redisSource = new RedisSource(name);
            REDIS_SOURCE_MAP.put(name, redisSource);
        }
    }

    public static void shutdown() {
        for (Iterator<RedisSource> it = REDIS_SOURCE_MAP.values().iterator(); it.hasNext();) {
            RedisSource rs = it.next();
            JedisPool pool = rs.getPool();
            pool.destroy();
        }
    }

    public static void close(String name, Jedis jedis) {
        if (jedis == null) {
            return;
        }

        RedisSource rs = REDIS_SOURCE_MAP.get(name);
        JedisPool pool = rs.getPool();
        if (pool == null)
            jedis.disconnect();

        try {
            pool.returnResource(jedis);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
        }
        jedis = null;
    }

    public static Jedis getInstance(String name) {
        RedisSource rs = REDIS_SOURCE_MAP.get(name);
        if (rs == null) {
            return null;
        }
        JedisPool pool = rs.getPool();
        if (pool == null) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String password = rs.getPassword();

            if (password != null && !password.isEmpty()) {
                jedis.auth(password);
            }

        } catch (Exception e) {
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
            }
        }
        return jedis;
    }
}