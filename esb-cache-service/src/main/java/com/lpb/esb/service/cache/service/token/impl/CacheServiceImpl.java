package com.lpb.esb.service.cache.service.token.impl;

import com.lpb.esb.service.cache.repositories.redis.CacheRepository;
import com.lpb.esb.service.cache.service.token.CacheService;
import com.lpb.esb.service.common.model.request.CacheEntity;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2022-04-01
 */
@Service
@Log4j2
public class CacheServiceImpl implements CacheService {
    @Autowired
    CacheRepository cacheRepository;

    @Override
    public ResponseModel putCache(CacheEntity cacheEntity) {
        cacheRepository.putCache(cacheEntity);
        LpbResCode resCode = LpbResCode.builder()
            .errorDesc(EsbErrorCode.SUCCESS.label)
            .errorDesc("Put cache success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .data(cacheEntity)
            .build();
        return responseModel;
    }

    @Override
    public ResponseModel putHashCache(CacheEntity cacheEntity) {
        cacheRepository.putHashCache(cacheEntity);
        LpbResCode resCode = LpbResCode.builder()
            .errorDesc(EsbErrorCode.SUCCESS.label)
            .errorDesc("Put cache success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .data(cacheEntity)
            .build();
        return responseModel;
    }

    @Override
    public ResponseModel getCacheValue(CacheEntity cacheEntity) {
        String key = cacheEntity.getModule() + "-" + cacheEntity.getKey();
        String valueCache = cacheRepository.getCacheValue(key, cacheEntity.getDb());
        if (valueCache == null) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("No data")
                .refDesc(key)
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(null)
                .build();
            return responseModel;
        } else {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Get cache success")
                .refDesc(key)
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(valueCache)
                .build();
            return responseModel;
        }
    }

    @Override
    public ResponseModel getHashCacheValue(CacheEntity cacheEntity) {
        String key = cacheEntity.getModule() + "-" + cacheEntity.getHash();
        String valueCache = cacheRepository.getHashCacheValue(key, cacheEntity.getKey(), cacheEntity.getDb());
        if (valueCache == null) {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("No data")
                .refDesc(key)
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(null)
                .build();
            return responseModel;
        } else {
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Get cache success")
                .refDesc(key)
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(valueCache)
                .build();
            return responseModel;
        }
    }
}
