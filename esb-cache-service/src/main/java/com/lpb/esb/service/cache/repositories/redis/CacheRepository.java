package com.lpb.esb.service.cache.repositories.redis;

import com.lpb.esb.service.common.model.request.CacheEntity;

/**
 * Created by tudv1 on 2022-04-01
 */
public interface CacheRepository {
    boolean putCache(CacheEntity cacheEntity);

    boolean putHashCache(CacheEntity cacheEntity);

    String getCacheValue(String key, int db);

    String getHashCacheValue(String key, String field, int db);
}
