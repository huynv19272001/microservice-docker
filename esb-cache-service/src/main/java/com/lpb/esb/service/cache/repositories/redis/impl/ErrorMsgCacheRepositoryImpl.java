package com.lpb.esb.service.cache.repositories.redis.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.cache.model.oracle.EsbErrorMessageEntity;
import com.lpb.esb.service.cache.repositories.redis.ErrorMsgCacheRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tudv1 on 2021-08-26
 */
@Repository
@Log4j2
public class ErrorMsgCacheRepositoryImpl implements ErrorMsgCacheRepository {
    final String KEY_ESB = "ESB";
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void saveErrMsg(EsbErrorMessageEntity esbErrorMessageEntity) {
        try {
            redisTemplate.opsForHash()
                .put(this.KEY_ESB, esbErrorMessageEntity.getCodeNumber() + "", objectMapper.writeValueAsString(esbErrorMessageEntity));
        } catch (Exception e) {
            log.error("error when save redis entity {}: {}", esbErrorMessageEntity, e.getMessage(), e);
        }
    }

    @Override
    public void saveErrMsg(List<EsbErrorMessageEntity> list) {
        for (EsbErrorMessageEntity esbErrorMessageEntity : list) {
            saveErrMsg(esbErrorMessageEntity);
        }
    }

    @Override
    public EsbErrorMessageEntity getErrorMessageByCodeNumber(int codeNumber) {
        String json = (String) redisTemplate
            .opsForHash()
            .get(this.KEY_ESB, codeNumber + "");
        EsbErrorMessageEntity res = null;
        try {
            res = objectMapper.readValue(json, EsbErrorMessageEntity.class);
        } catch (JsonProcessingException e) {
            log.error("error when parse json {}: {}", json, e.getMessage(), e);
        }
        return res;
    }
}
