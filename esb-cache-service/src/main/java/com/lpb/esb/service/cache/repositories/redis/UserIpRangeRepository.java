package com.lpb.esb.service.cache.repositories.redis;

import com.lpb.esb.service.cache.model.redis.UserIpRangeEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by tudv1 on 2021-07-14
 */
@Repository
public interface UserIpRangeRepository {
    boolean saveUserIpRange(UserIpRangeEntity userIpRangeEntity);

    UserIpRangeEntity getIpRangeByUsername(String username);
}
