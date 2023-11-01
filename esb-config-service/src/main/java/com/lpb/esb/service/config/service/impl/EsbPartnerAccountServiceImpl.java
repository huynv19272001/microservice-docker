package com.lpb.esb.service.config.service.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.PartnerAccountInfo;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.model.entities.EsbPartnerAccountDetailEntity;
import com.lpb.esb.service.config.repository.EsbPartnerAccountDetailRepository;
import com.lpb.esb.service.config.service.EsbPartnerAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Log4j2
@Service
public class EsbPartnerAccountServiceImpl implements EsbPartnerAccountService {
    @Autowired
    EsbPartnerAccountDetailRepository esbPartnerAccountDetailRepository;

    @Override
    public ResponseModel findPartnerAccount(String serviceId, String productCode, String providerId) {
        ErrorMessage errorMessage = ErrorMessage.NODATA;
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(errorMessage.label)
            .errorDesc(errorMessage.description)
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .build();

        List<EsbPartnerAccountDetailEntity> esbPartnerAccountEntity = esbPartnerAccountDetailRepository.findByRecordStatAndServiceIdAndProductCodeAndProviderId("O", serviceId, productCode, providerId);

        if (esbPartnerAccountEntity == null) {
            return responseModel;
        }

        List<PartnerAccountInfo> list = new ArrayList<>();
        for (EsbPartnerAccountDetailEntity detailEntity : esbPartnerAccountEntity) {

            PartnerAccountInfo partnerAccountInfo = PartnerAccountInfo.builder()
                .accBranch(detailEntity.getAccBranch())
                .accDesc(detailEntity.getAccDesc())
                .accNo(detailEntity.getAccNo())
                .customerNo(detailEntity.getAccCusNo())
                .merchantId(detailEntity.getProviderId())
                .type(detailEntity.getAccountType())
                .build();
            list.add(partnerAccountInfo);
        }

        responseModel.setData(list);
        ErrorMessage message = ErrorMessage.SUCCESS;
        lpbResCode.setErrorCode(message.label);
        lpbResCode.setErrorDesc(message.description);
        return responseModel;
    }
}
