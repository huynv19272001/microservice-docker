package com.lpb.esb.service.config.service.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.model.entities.EsbPaymentMethodChannelEvnEntity;
import com.lpb.esb.service.config.repository.EsbPaymentMethodChannelEvnRepository;
import com.lpb.esb.service.config.service.EsbPaymentMethodChannelEvnService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class EsbPaymentMethodChannelEvnServiceImpl implements EsbPaymentMethodChannelEvnService {
    @Autowired
    EsbPaymentMethodChannelEvnRepository esbPaymentMethodChannelEvnRepository;

    @Override
    public ResponseModel findPaymentMethodChannelEvn(String productCode, String paymentMethod, String channel, String billPrint) {
        ErrorMessage errorMessage = ErrorMessage.INPUT_ERROR;
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(errorMessage.label)
            .errorDesc(errorMessage.description)
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .build();

        EsbPaymentMethodChannelEvnEntity esbPartnerAccountEntity = esbPaymentMethodChannelEvnRepository.findByRecordStatAndProductCodeAndPaymentMethodAndChannelAndBillPrint("O", productCode, paymentMethod, channel, billPrint);

        if (esbPartnerAccountEntity == null) {
            return responseModel;
        }

        responseModel.setData(esbPartnerAccountEntity.getMapEvn());
        ErrorMessage message = ErrorMessage.SUCCESS;
        lpbResCode.setErrorCode(message.label);
        lpbResCode.setErrorDesc(message.description);
        return responseModel;
    }
}
