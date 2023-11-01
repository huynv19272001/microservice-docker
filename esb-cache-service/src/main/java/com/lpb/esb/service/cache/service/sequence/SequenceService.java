package com.lpb.esb.service.cache.service.sequence;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.ResponseModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by tudv1 on 2021-08-19
 */
@Service
public interface SequenceService {
    ResponseModel getSequenceNextVal(String sequenceName);
}
