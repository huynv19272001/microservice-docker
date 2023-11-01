package com.lpb.esb.service.cache.repositories.redis.impl;

import com.google.gson.Gson;
import com.lpb.esb.service.cache.model.redis.UserIpRangeEntity;
import com.lpb.esb.service.cache.repositories.redis.UserIpRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by tudv1 on 2021-07-14
 */
@Repository
public class UserIpRangeRepositoryImpl implements UserIpRangeRepository {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean saveUserIpRange(UserIpRangeEntity userIpRangeEntity) {
        redisTemplate.opsForValue().set(userIpRangeEntity.getUsername(), userIpRangeEntity.toString());
        return true;
    }

    @Override
    public UserIpRangeEntity getIpRangeByUsername(String username) {
        Gson gson = new Gson();
        String value = (String) redisTemplate.opsForValue().get(username);
        UserIpRangeEntity userIpRangeEntity = gson.fromJson(value, UserIpRangeEntity.class);
        return userIpRangeEntity;
    }
}
