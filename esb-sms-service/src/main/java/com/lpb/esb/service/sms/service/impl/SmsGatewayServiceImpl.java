package com.lpb.esb.service.sms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.sms.model.entities.EsbSmsCategoryEntity;
import com.lpb.esb.service.sms.model.entities.MidSmsCategoryEntity;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import com.lpb.esb.service.sms.model.request.SmsCategoryRequest;
import com.lpb.esb.service.sms.process.SmsGatewayProcess;
import com.lpb.esb.service.sms.repositories.EsbSmsCategoryRepository;
import com.lpb.esb.service.sms.repositories.MidSmsCategoryRepository;
import com.lpb.esb.service.sms.repositories.PkgEsbUtilRepository;
import com.lpb.esb.service.sms.service.SmsGatewayService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by tudv1 on 2022-05-24
 */
@Service
@Log4j2
public class SmsGatewayServiceImpl implements SmsGatewayService {
    @Autowired
    SmsGatewayProcess smsGatewayProcess;
    @Autowired
    EsbSmsCategoryRepository esbSmsCategoryRepository;
    @Autowired
    PkgEsbUtilRepository pkgEsbUtilRepository;
    @Autowired
    RestTemplate restTemplateLoadBalancer;
    @Autowired
    MidSmsCategoryRepository midSmsCategoryRepository;


    @Override
    public ResponseModel smsGatewaySend(SmsCategoryRequest smsCategoryRequest) {
        MidSmsCategoryEntity entity = midSmsCategoryRepository.findById(smsCategoryRequest.getCategory()).orElse(null);
        LpbResCode lpbResCode = new LpbResCode();
        ResponseModel responseModel = new ResponseModel();
        responseModel.setResCode(lpbResCode);
        if (entity == null) {
            lpbResCode = LpbResCode.builder()
                .errorCode("ESB-099")
                .errorDesc("Category Not Found")
                .build();
            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            return responseModel;
        } else {
            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLoadBalancer,
                entity.getServiceId(),
                entity.getProductCode(),
                entity.getHasRole());
            if (list.size() == 0) {
                lpbResCode = LpbResCode.builder()
                    .errorCode("ESB-099")
                    .errorDesc("Service Info Not Found")
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .build();
                return responseModel;
            }
            ServiceInfo serviceInfo = list.get(0);
            EsbSmsRequest smsRequest = smsGatewayProcess.buildMsgSmsGateway(serviceInfo, smsCategoryRequest, entity.getHasRole());
            try {
                responseModel = smsGatewayProcess.executeRequestSendSms(smsRequest, serviceInfo);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return responseModel;
        }
    }

//    @Override
//    public ResponseModel sendSms(SmsCategoryRequest smsCategoryRequest) {
//        if (smsCategoryRequest.getCategory().contains("SMS_LPB_VNP")) {
//            EsbSmsCategoryEntity entity = esbSmsCategoryRepository.findById(smsCategoryRequest.getCategory()).orElse(null);
//            LpbResCode lpbResCode = new LpbResCode();
//            ResponseModel responseModel = new ResponseModel();
//            responseModel.setResCode(lpbResCode);
//            // check category
//            if (entity == null) {
//                lpbResCode.setErrorCode("99");
//                lpbResCode.setErrorCode("Fail");
//                return responseModel;
//            }
//
//            // check serviceinfo
//            // Get service info
//            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLoadBalancer
//                , entity.getServiceId()
//                , entity.getProductCode()
//                , "VNP_SMS_ALL"
//            );
//
//            if (list.size() == 0) {
//                lpbResCode.setErrorCode("1");
//                pkgEsbUtilRepository.esbLoadErrorDesc(lpbResCode);
//                return responseModel;
//            }
//            ServiceInfo serviceInfo = list.get(0);
//
//            // Start routing API send sms
//            // Build message
//            EsbSmsRequest smsRequest = smsGatewayProcess.buildMsgSmsRequestVnp(serviceInfo, smsCategoryRequest);
//            // Send request
//            try {
//                responseModel = smsGatewayProcess.executeRequestSendSms(smsRequest, serviceInfo);
//            } catch (JsonProcessingException e) {
//                log.error("error: {}", e.getMessage(), e);
//            }
//            if (responseModel.getResCode().getErrorCode().length() < 6) {
//                pkgEsbUtilRepository.esbLoadErrorDesc(responseModel.getResCode());
//            }
//
//            return responseModel;
//        } else {
//            EsbSmsCategoryEntity entity = esbSmsCategoryRepository.findById(smsCategoryRequest.getCategory()).orElse(null);
//            LpbResCode lpbResCode = new LpbResCode();
//            ResponseModel responseModel = new ResponseModel();
//            responseModel.setResCode(lpbResCode);
//
//            // check category
//            if (entity == null) {
//                lpbResCode.setErrorCode("8");
//                pkgEsbUtilRepository.esbLoadErrorDesc(lpbResCode);
//                return responseModel;
//            }
//
//            // check serviceinfo
//            // Get service info
//            List<ServiceInfo> list = ConnectInfoService.getServiceInfo(restTemplateLoadBalancer
//                , entity.getServiceId()
//                , entity.getProductCode()
//                , "FPT_SMS_ALL"
//            );
//
//            if (list.size() == 0) {
//                lpbResCode.setErrorCode("1");
//                pkgEsbUtilRepository.esbLoadErrorDesc(lpbResCode);
//                return responseModel;
//            }
//            ServiceInfo serviceInfo = list.get(0);
//
//            // Start routing API send sms
//            // Build message
//            EsbSmsRequest smsRequest = smsGatewayProcess.buildMsgSmsRequest(serviceInfo, smsCategoryRequest);
//            // Send request
//            try {
//                responseModel = smsGatewayProcess.executeRequestSendSms(smsRequest, serviceInfo);
//            } catch (JsonProcessingException e) {
//                log.error("error: {}", e.getMessage(), e);
//            }
//            if (responseModel.getResCode().getErrorCode().length() < 6) {
//                pkgEsbUtilRepository.esbLoadErrorDesc(responseModel.getResCode());
//            }
//
//            return responseModel;
//        }
//    }
}
