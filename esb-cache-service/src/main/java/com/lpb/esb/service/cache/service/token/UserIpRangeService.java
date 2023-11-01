package com.lpb.esb.service.cache.service.token;

import com.lpb.esb.service.cache.model.redis.UserIpRangeEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-07-14
 */
@Service
public interface UserIpRangeService {
    boolean addUserIpRangeCache(UserIpRangeEntity userIpRangeEntity);

    @Async
    void addUserIpRangeCacheAsync(UserIpRangeEntity userIpRangeEntity);

    UserIpRangeEntity findIpRangeByUsername(String username);
}
