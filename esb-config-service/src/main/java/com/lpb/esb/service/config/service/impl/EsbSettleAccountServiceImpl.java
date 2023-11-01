package com.lpb.esb.service.config.service.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.SettleAccountInfo;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.model.entities.EsbPartnerAccountEntity;
import com.lpb.esb.service.config.model.entities.EsbSettleAccountDetailEntity;
import com.lpb.esb.service.config.repository.EsbPartnerAccountRepository;
import com.lpb.esb.service.config.repository.EsbSettleAccountDetailRepository;
import com.lpb.esb.service.config.service.EsbSettleAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tudv1 on 2022-07-08
 */
@Service
public class EsbSettleAccountServiceImpl implements EsbSettleAccountService {
    @Autowired
    EsbSettleAccountDetailRepository esbSettleAccountDetailRepository;
    @Autowired
    EsbPartnerAccountRepository esbPartnerAccountRepository;

    @Override
    public ResponseModel findSettleAccount(String serviceId, List<String> providerId) {
        ErrorMessage errorMessage = ErrorMessage.NODATA;
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(errorMessage.label)
            .errorDesc(errorMessage.description)
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .build();

        List<EsbPartnerAccountEntity> esbPartnerAccountEntity = esbPartnerAccountRepository.findByRecordStatAndServiceIdAndProviderIdIn(
            "O"
            , serviceId
            , providerId
        );


        if (esbPartnerAccountEntity == null) {
            return responseModel;
        }
        Map<String, String> mapAccount = new HashMap<>();
        List<String> listAccountId = new ArrayList<>();
        for (EsbPartnerAccountEntity entity : esbPartnerAccountEntity) {
            mapAccount.put(entity.getAccountId(), entity.getProviderId());
            listAccountId.add(entity.getAccountId());
        }

        List<EsbSettleAccountDetailEntity> entity = esbSettleAccountDetailRepository.findByRecordStatAndAccountIdIn(
            "O"
            , listAccountId

        );

        if (entity == null) {
            return responseModel;
        }
        List<SettleAccountInfo> list = new ArrayList<>();
        for (EsbSettleAccountDetailEntity detailEntity : entity) {
            SettleAccountInfo settleAccountInfo = SettleAccountInfo.builder()
                .accBranch(detailEntity.getAccBranch())
                .accDesc(detailEntity.getAccDesc())
                .accNo(detailEntity.getAccNo())
                .customerNo(detailEntity.getAccCusNo())
                .merchantId(mapAccount.getOrDefault(detailEntity.getAccountId(), "empty"))
                .build();
            list.add(settleAccountInfo);
        }

        responseModel.setData(list);
        ErrorMessage message = ErrorMessage.SUCCESS;
        lpbResCode.setErrorCode(message.label);
        lpbResCode.setErrorDesc(message.description);
        return responseModel;
    }
}
