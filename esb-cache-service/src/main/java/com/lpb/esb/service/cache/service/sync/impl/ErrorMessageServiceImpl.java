package com.lpb.esb.service.cache.service.sync.impl;

import com.lpb.esb.service.cache.model.oracle.EsbErrorMessageEntity;
import com.lpb.esb.service.cache.repositories.oracle.EsbErrorMessageRepository;
import com.lpb.esb.service.cache.repositories.redis.ErrorMsgCacheRepository;
import com.lpb.esb.service.cache.service.sync.ErrorMessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tudv1 on 2021-08-26
 */
@Service
@Log4j2
@EnableScheduling
public class ErrorMessageServiceImpl implements ErrorMessageService {
    @Autowired
    EsbErrorMessageRepository esbErrorMessageRepository;
    @Autowired
    ErrorMsgCacheRepository errorMsgCacheRepository;

    @Scheduled(fixedDelay = 1000 * 60 * 60, initialDelay = 1000 * 5)
    @Override
    public void syncErrorMsgToRedis() {
        List<EsbErrorMessageEntity> list = esbErrorMessageRepository.findAll();
        log.info("get error msg from DB size {}", list.size());
        errorMsgCacheRepository.saveErrMsg(list);
    }

    @Override
    public EsbErrorMessageEntity getErrorMessageByCodeNumber(int codeNumber) {
        return errorMsgCacheRepository.getErrorMessageByCodeNumber(codeNumber);
    }
}
