package com.lpb.esb.service.cache.service.token.impl;

import com.lpb.esb.service.cache.model.redis.UserIpRangeEntity;
import com.lpb.esb.service.cache.repositories.redis.UserIpRangeRepository;
import com.lpb.esb.service.cache.service.token.UserIpRangeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-07-14
 */
@Service
@Log4j2
public class UserIpRangeServiceImpl implements UserIpRangeService {
    @Autowired
    UserIpRangeRepository userIpRangeRepository;

    @Override
    public boolean addUserIpRangeCache(UserIpRangeEntity userIpRangeEntity) {
        userIpRangeRepository.saveUserIpRange(userIpRangeEntity);
        return true;
    }

    @Async
    @Override
    public void addUserIpRangeCacheAsync(UserIpRangeEntity userIpRangeEntity) {
        log.info("add cache async: {}", userIpRangeEntity.toString());
        addUserIpRangeCache(userIpRangeEntity);
    }

    @Override
    public UserIpRangeEntity findIpRangeByUsername(String username) {
        return userIpRangeRepository.getIpRangeByUsername(username);
    }
}
