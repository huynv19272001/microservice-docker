package com.lpb.esb.service.cache.service.token;

import com.lpb.esb.service.common.model.request.CacheEntity;
import com.lpb.esb.service.common.model.response.ResponseModel;

/**
 * Created by tudv1 on 2022-04-01
 */
public interface CacheService {
    ResponseModel putCache(CacheEntity cacheEntity);

    ResponseModel putHashCache(CacheEntity cacheEntity);

    ResponseModel getCacheValue(CacheEntity cacheEntity);

    ResponseModel getHashCacheValue(CacheEntity cacheEntity);
}
