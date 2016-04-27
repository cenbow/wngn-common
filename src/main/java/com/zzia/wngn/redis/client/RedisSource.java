package com.zzia.wngn.redis.client;

import com.zzia.wngn.util.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisSource {

    private String password = null;
    private JedisPool pool = null;

    public RedisSource(String name) {
        Configuration conf = Configuration.getInstance();

        String host = conf.getString("redis." + name + ".host", "127.0.0.1");
        int port = conf.getInt("redis." + name + ".port", 6379);
        String password = conf.getString("redis." + name + ".password", null);

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxActive(100);
        config.setMaxIdle(10);
        config.setMaxWait(1800000L);
        config.setTimeBetweenEvictionRunsMillis(180000L);
        config.setTestOnBorrow(true);

        pool = new JedisPool(config, host, port, 180000);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public JedisPool getPool() {
        return pool;
    }
}
