package com.lpb.esb.service.cache.controller;

import com.lpb.esb.service.cache.model.redis.RedisConnect;
import com.lpb.esb.service.cache.service.token.CacheService;
import com.lpb.esb.service.common.model.request.CacheEntity;
import com.lpb.esb.service.common.model.response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2022-04-01
 */
@RestController
@RequestMapping(value = "cache", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CacheController {
    @Autowired
    CacheService cacheService;

    @RequestMapping(value = "put", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> putCache(@RequestBody CacheEntity cacheEntity) {
        ResponseModel responseModel = cacheService.putCache(cacheEntity);
        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "get", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getKeyCache(@RequestBody CacheEntity cacheEntity) {
        ResponseModel responseModel = cacheService.getCacheValue(cacheEntity);
        if (responseModel.getData() == null) {
            return ResponseEntity.badRequest().body(responseModel);
        } else {
            return ResponseEntity.ok(responseModel);
        }
    }

    @RequestMapping(value = "put/hash", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> putHashCache(@RequestBody CacheEntity cacheEntity) {
        ResponseModel responseModel = cacheService.putHashCache(cacheEntity);
        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "get/hash", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getHashKeyCache(@RequestBody CacheEntity cacheEntity) {
        ResponseModel responseModel = cacheService.getHashCacheValue(cacheEntity);
        if (responseModel.getData() == null) {
            return ResponseEntity.badRequest().body(responseModel);
        } else {
            return ResponseEntity.ok(responseModel);
        }
    }
}
