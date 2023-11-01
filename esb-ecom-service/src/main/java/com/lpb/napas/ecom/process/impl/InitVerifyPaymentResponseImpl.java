package com.lpb.napas.ecom.process.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.napas.ecom.common.Constant;
import com.lpb.napas.ecom.dto.*;
import com.lpb.napas.ecom.model.EsbCardCoreUserInfo;
import com.lpb.napas.ecom.model.config.ServiceConfig;
import com.lpb.napas.ecom.process.IInitVerifyPaymentResponse;
import com.lpb.napas.ecom.service.EsbCardCoreUserInfoService;
import com.lpb.napas.ecom.utils.AESUtils;
import com.lpb.napas.ecom.utils.RSAUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class InitVerifyPaymentResponseImpl implements IInitVerifyPaymentResponse {
    @Autowired
    EsbCardCoreUserInfoService esbCardCoreUserInfoService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public VerifyPaymentResponse initVerifyPaymentResponse(VerifyPaymentRequest verifyPaymentRequest,
                                                           InitVerifyPaymentRequest initVerifyPaymentRequest,
                                                           DataVerifyPaymentRequest dataVerifyPaymentRequest,
                                                           String appId) throws Exception {
        DataVerifyPaymentResponse dataVerifyPaymentResponse = DataVerifyPaymentResponse.builder()
            .approvedCode(null)
            .resCode(initVerifyPaymentRequest.getResponseCode())
            .resMessage(initVerifyPaymentRequest.getResponseMessage())
            .otherInfo(dataVerifyPaymentRequest.getOtherInfo())
            .redirectUrl(null).build();

        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(dataVerifyPaymentResponse);
        log.info(verifyPaymentRequest.getRequestorTransId() + "-" + appId + " OutputData: " + data);
        String dataEncrypt = AESUtils.encryptWithAES(data);

        EsbCardCoreUserInfo esbCardCoreUserInfo = this.esbCardCoreUserInfoService.
            getEsbCardCoreUserInfoByUsername(Constant.ESB_CARD_CORE_USER_INFO_NAPAS_ECOM);
        String inputSignatureData = String.format("%s%s%s%s", initVerifyPaymentRequest.getPartnerTransId(),
            verifyPaymentRequest.getRequestorTransId(), dataEncrypt, serviceConfig.getSecretKey());

        log.info(verifyPaymentRequest.getRequestorTransId() + "-" + appId + " OutputSignatureData: " + inputSignatureData);
        String signature = RSAUtils.makeSignature(inputSignatureData, RSAUtils
            .getPrivateKey(esbCardCoreUserInfo.getEsbPrivateKey()), Constant.SHA256withRSA);
        log.info(verifyPaymentRequest.getRequestorTransId() + "-" + appId + " OutputSignature: " + signature);

        VerifyPaymentResponse verifyPaymentResponse = VerifyPaymentResponse.builder()
            .partnerTransId(initVerifyPaymentRequest.getPartnerTransId())
            .signature(signature)
            .requestorTransId(verifyPaymentRequest.getRequestorTransId())
            .data(dataEncrypt).build();
        return verifyPaymentResponse;
    }
}
