package com.lpb.esb.service.cache.repositories.redis.impl;

import com.lpb.esb.service.cache.config.redis.RedisFactory;
import com.lpb.esb.service.common.model.request.CacheEntity;
import com.lpb.esb.service.cache.repositories.redis.CacheRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 * Created by tudv1 on 2022-04-01
 */
@Repository
@Log4j2
public class CacheRepositoryImpl implements CacheRepository {
//    @Autowired
//    RedisTemplate redisTemplate;


    Jedis jedisResource;

    @Override
    public boolean putCache(CacheEntity cacheEntity) {
        String key = cacheEntity.getModule() + "-" + cacheEntity.getKey();
        try {
            jedisResource = RedisFactory.getInstance().getJedisPool().getResource();
            jedisResource.select(cacheEntity.getDb());
            jedisResource.set(key, cacheEntity.getValue());
            jedisResource.expire(key, Math.toIntExact(cacheEntity.getTtl()));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            jedisResource.close();
        }
//        redisTemplate.opsForValue().set(key, cacheEntity.getValue());
//        redisTemplate.expire(key, cacheEntity.getTtl(), TimeUnit.SECONDS);
//        return true;
    }

    @Override
    public boolean putHashCache(CacheEntity cacheEntity) {
        String hash = cacheEntity.getModule() + "-" + cacheEntity.getHash();
        try {
            jedisResource = RedisFactory.getInstance().getJedisPool().getResource();
            jedisResource.select(cacheEntity.getDb());
            jedisResource.hset(hash, cacheEntity.getKey(), cacheEntity.getValue());
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        } finally {
            jedisResource.close();
        }
//        redisTemplate.opsForValue().set(key, cacheEntity.getValue());
//        redisTemplate.expire(key, cacheEntity.getTtl(), TimeUnit.SECONDS);
//        return true;
    }

    @Override
    public String getCacheValue(String key, int db) {
        String value = null;
        try {
            jedisResource = RedisFactory.getInstance().getJedisPool().getResource();
            jedisResource.select(db);
            value = jedisResource.get(key);
            return value;
        } catch (Exception e) {
            log.error(e.getMessage());
            return value;
        } finally {
            jedisResource.close();
        }
//        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public String getHashCacheValue(String key, String field, int db) {
        String value = null;
        try {
            jedisResource = RedisFactory.getInstance().getJedisPool().getResource();
            jedisResource.select(db);
            value = jedisResource.hget(key, field);
            return value;
        } catch (Exception e) {
            log.error(e.getMessage());
            return value;
        } finally {
            jedisResource.close();
        }
//        return (String) redisTemplate.opsForValue().get(key);
    }
}
