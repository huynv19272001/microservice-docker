package com.lpb.napas.ecom.process.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.napas.ecom.common.Constant;
import com.lpb.napas.ecom.dto.*;
import com.lpb.napas.ecom.model.EsbCardCoreUserInfo;
import com.lpb.napas.ecom.model.config.ServiceConfig;
import com.lpb.napas.ecom.process.IInitPurchaseResponse;
import com.lpb.napas.ecom.service.EsbCardCoreUserInfoService;
import com.lpb.napas.ecom.service.EsbTransactionService;
import com.lpb.napas.ecom.utils.AESUtils;
import com.lpb.napas.ecom.utils.RSAUtils;
import com.lpb.napas.ecom.utils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class InitPurchaseResponseImpl implements IInitPurchaseResponse {
    @Autowired
    EsbCardCoreUserInfoService esbCardCoreUserInfoService;

    @Autowired
    EsbTransactionService esbTransactionService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public PurchaseResponse initPurchaseResponse(PurchaseRequest purchaseRequest,
                                                 InitPurchaseRequest initPurchaseRequest,
                                                 DataPurchaseRequest dataPurchaseRequest,
                                                 String appId) throws Exception {
        DataPurchaseResponse dataPurchaseResponse = DataPurchaseResponse.builder()
            .otherInfo(dataPurchaseRequest.getOtherInfo())
            .resCode(initPurchaseRequest.getResponseCode())
            .resMessage(initPurchaseRequest.getResponseMessage())
            .approvedCode(null).build();

        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(dataPurchaseResponse);
        log.info(purchaseRequest.getRequestorTransId() + "-" + appId + " OutputData: " + data);
        String dataEncrypt = AESUtils.encryptWithAES(data);

        EsbCardCoreUserInfo esbCardCoreUserInfo = this.esbCardCoreUserInfoService.
            getEsbCardCoreUserInfoByUsername(Constant.ESB_CARD_CORE_USER_INFO_NAPAS_ECOM);
        String inputSignatureData = String.format("%s%s%s%s", initPurchaseRequest.getPartnerTransId(),
            purchaseRequest.getRequestorTransId(), dataEncrypt, serviceConfig.getSecretKey());

        log.info(purchaseRequest.getRequestorTransId() + "-" + appId + " OutputSignatureData: " + inputSignatureData);

        String signature = RSAUtils.makeSignature(inputSignatureData, RSAUtils
            .getPrivateKey(esbCardCoreUserInfo.getEsbPrivateKey()), Constant.SHA256withRSA);
        log.info(purchaseRequest.getRequestorTransId() + "-" + appId + " OutputSignature: " + signature);

        PurchaseResponse purchaseResponse = PurchaseResponse.builder()
            .signature(signature)
            .requestorTransId(purchaseRequest.getRequestorTransId())
            .partnerTransId(initPurchaseRequest.getPartnerTransId())
            .data(dataEncrypt).build();

        return purchaseResponse;
    }

    public void updateTransaction(InitPurchaseRequest initPurchaseRequest,
                                  String appId) {
        if (StringUtil.isEmptyOrNull(initPurchaseRequest.getPartnerTransId())) {
            esbTransactionService.updateTransaction("000000", appId);
        } else {
            esbTransactionService.updateTransaction(initPurchaseRequest.getPartnerTransId().substring(0, 6), appId);
        }
    }
}
