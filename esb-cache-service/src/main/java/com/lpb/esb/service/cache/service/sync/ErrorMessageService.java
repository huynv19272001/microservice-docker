package com.lpb.esb.service.cache.service.sync;

import com.lpb.esb.service.cache.model.oracle.EsbErrorMessageEntity;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-08-26
 */
@Service
public interface ErrorMessageService {
    void syncErrorMsgToRedis();

    EsbErrorMessageEntity getErrorMessageByCodeNumber(int codeNumber);
}
