package com.lpb.esb.service.cache.repositories.redis;

import com.lpb.esb.service.cache.model.oracle.EsbErrorMessageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tudv1 on 2021-08-26
 */
@Repository
public interface ErrorMsgCacheRepository {
    void saveErrMsg(EsbErrorMessageEntity esbErrorMessageEntity);

    void saveErrMsg(List<EsbErrorMessageEntity> list);

    EsbErrorMessageEntity getErrorMessageByCodeNumber(int codeNumber);
}
