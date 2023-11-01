package com.lpb.esb.service.cache.config.redis;

import com.lpb.esb.service.cache.config.StaticApplicationContext;
import com.lpb.esb.service.cache.model.redis.RedisConnect;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

public class RedisFactory {

    private static JedisPool jedisPool;
    private static RedisFactory ourInstance = new RedisFactory();

    public static RedisFactory getInstance() {
        return ourInstance;
    }

    private RedisFactory() {
        RedisConnect redis  = StaticApplicationContext.getContext().getBean(RedisConnect.class);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(Integer.parseInt(redis.getMaxTotal()));
        poolConfig.setMaxIdle(Integer.parseInt(redis.getMaxIdle()));
        poolConfig.setMinIdle(Integer.parseInt(redis.getMinIdle()));
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(1).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(1).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        jedisPool = new JedisPool(poolConfig, redis.getHostName(), Integer.parseInt(redis.getPort()));

    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

}
