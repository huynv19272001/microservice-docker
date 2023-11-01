package com.lpb.esb.service.sms.process.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.utils.RequestUtils;
import com.lpb.esb.service.sms.model.request.EsbBody;
import com.lpb.esb.service.sms.model.request.EsbHeader;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import com.lpb.esb.service.sms.model.request.SmsCategoryRequest;
import com.lpb.esb.service.sms.process.SmsGatewayProcess;
import com.lpb.esb.service.sms.utils.LogicUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tudv1 on 2022-05-24
 */
@Component
@Log4j2
public class SmsGatewayProcessImpl implements SmsGatewayProcess {
    @Autowired
    LogicUtils logicUtils;
    @Autowired
    RestTemplate restTemplateLoadBalancer;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public EsbSmsRequest buildMsgSmsGateway(ServiceInfo serviceInfo, SmsCategoryRequest request, String hasRole) {
        EsbHeader esbHeader = EsbHeader.builder()
            .msgId(request.getMsgId())
            .serviceId(serviceInfo.getServiceId())
            .productCode(serviceInfo.getProductCode())
            .hasRole(hasRole)
            .build();
        EsbBody esbBody = EsbBody.builder()
            .category(request.getCategory())
            .mobileNumber(logicUtils.phoneTo84(request.getMobileNumber()))
            .content(request.getContent())
            .serviceLog(request.getServiceLog())
            .build();
        EsbSmsRequest smsRequest = EsbSmsRequest.builder()
            .esbHeader(esbHeader)
            .esbBody(esbBody)
            .build();
        return smsRequest;
    }

    @Override
    public EsbSmsRequest buildMsgSmsRequest(ServiceInfo serviceInfo, SmsCategoryRequest request) {
        EsbHeader esbHeader = EsbHeader.builder()
            .msgId(request.getMsgId())
            .serviceId(serviceInfo.getServiceId())
            .productCode(serviceInfo.getProductCode())
            .hasRole("FPT_SMS_ALL")
            .build();
        EsbBody esbBody = EsbBody.builder()
            .category(request.getCategory())
            .mobileNumber(logicUtils.phoneTo84(request.getMobileNumber()))
            .content(request.getContent())
            .serviceLog(request.getServiceLog())
            .build();
        EsbSmsRequest smsRequest = EsbSmsRequest.builder()
            .esbHeader(esbHeader)
            .esbBody(esbBody)
            .build();
        return smsRequest;
    }

    @Override
    public EsbSmsRequest buildMsgSmsRequestVnp(ServiceInfo serviceInfo, SmsCategoryRequest request) {
        EsbHeader esbHeader = EsbHeader.builder()
            .msgId(request.getMsgId())
            .serviceId(serviceInfo.getServiceId())
            .productCode(serviceInfo.getProductCode())
            .hasRole("VNP_SMS_ALL")
            .build();
        EsbBody esbBody = EsbBody.builder()
            .category(request.getCategory())
            .mobileNumber(logicUtils.phoneTo84(request.getMobileNumber()))
            .content(request.getContent())
            .serviceLog(request.getServiceLog())
            .build();
        EsbSmsRequest smsRequest = EsbSmsRequest.builder()
            .esbHeader(esbHeader)
            .esbBody(esbBody)
            .build();
        return smsRequest;
    }

    @Override
    public ResponseModel executeRequestSendSms(EsbSmsRequest esbSmsRequest, ServiceInfo serviceInfo) throws JsonProcessingException {
        ResponseEntity<String> responseEntity = RequestUtils.executePostReq(restTemplateLoadBalancer, serviceInfo.getUrlApi(), objectMapper.writeValueAsString(esbSmsRequest));
        ResponseModel responseModel = null;
        if (responseEntity.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            try {
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                responseModel = objectMapper.readValue(responseEntity.getBody(), ResponseModel.class);
            } catch (Exception e) {
                log.error("error when parse body to object: {}", e.getMessage(), e);
                LpbResCode lpbResCode = LpbResCode.builder()
                    .errorCode("90")
                    .refCode(responseEntity.getStatusCode().value() + "")
                    .refDesc(responseEntity.getBody())
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .build();
            }
        } else {
            responseModel = objectMapper.readValue(responseEntity.getBody(), ResponseModel.class);
        }
        return responseModel;
    }
}
