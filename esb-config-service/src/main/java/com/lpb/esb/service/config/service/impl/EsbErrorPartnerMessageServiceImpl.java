package com.lpb.esb.service.config.service.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.config.model.dto.EsbErrorPartnerMessage;
import com.lpb.esb.service.config.model.entities.EsbErrorPartnerMessageEntity;
import com.lpb.esb.service.config.repository.EsbErrorPartnerMessageRepository;
import com.lpb.esb.service.config.service.EsbErrorPartnerMessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsbErrorPartnerMessageServiceImpl implements EsbErrorPartnerMessageService {
    @Autowired
    EsbErrorPartnerMessageRepository esbErrorPartnerMessageRepository;

    @Override
    public ResponseModel getErrorMessage(String serviceId, String partnerId, String partnerCode) {
        try {
            EsbErrorPartnerMessageEntity entity = esbErrorPartnerMessageRepository.getErrorMessage(serviceId, partnerId, partnerCode);
            EsbErrorPartnerMessage errorPartnerMessage = new EsbErrorPartnerMessage();
            BeanUtils.copyProperties(entity, errorPartnerMessage);
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Success")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(errorPartnerMessage)
                .build();

            return responseModel;
        } catch (Exception e) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Failed")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();

            return responseModel;
        }
    }
}
