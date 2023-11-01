package com.lpb.esb.service.cache.service.sequence.impl;

import com.lpb.esb.service.cache.repositories.oracle.SequenceRepository;
import com.lpb.esb.service.cache.service.sequence.SequenceService;
import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.common.utils.code.EsbControllerResponseCode;
import com.lpb.esb.service.common.utils.code.ExecuteCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by tudv1 on 2021-08-19
 */
@Service
@Log4j2
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    SequenceRepository sequenceRepository;

    @Override
    public ResponseModel getSequenceNextVal(String sequenceName) {
        try {
            BigDecimal seqNumber = sequenceRepository.getSequenceId(sequenceName);
            ExecuteModel<BigDecimal> executeModel = ExecuteModel.<BigDecimal>builder()
                .executeCode(ExecuteCode.SUCCESS)
                .data(seqNumber)
                .message(sequenceName)
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorCode(EsbControllerResponseCode.SUCCESS.label)
                .errorDesc("Get next sequence success")
                .data(executeModel)
                .build();

            return responseModel;
        } catch (Exception e) {
            log.error("error when get next seq of {}: {}", sequenceName, e.getCause(), e);
            ExecuteModel executeModel = ExecuteModel.builder()
                .executeCode(ExecuteCode.FAIL)
                .data(System.currentTimeMillis() + "")
                .message(sequenceName)
                .build();

            ResponseModel responseModel = ResponseModel.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorCode(EsbControllerResponseCode.FAIL.label)
                .errorDesc(e.getMessage())
                .data(executeModel)
                .build();
            return responseModel;
        }
    }
}
