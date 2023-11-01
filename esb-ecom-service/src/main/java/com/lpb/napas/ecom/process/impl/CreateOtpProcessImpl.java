package com.lpb.napas.ecom.process.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.napas.ecom.dto.CreateOtpREQDTO;
import com.lpb.napas.ecom.dto.CreateOtpRESDTO;
import com.lpb.napas.ecom.model.config.ServiceApiConfig;
import com.lpb.napas.ecom.process.ICreateOtpProcess;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.lpb.esb.service.common.model.response.ResponseModel;

import java.util.Arrays;

@Log4j2
@Service
public class CreateOtpProcessImpl implements ICreateOtpProcess {
    @Autowired
    ServiceApiConfig serviceApiConfig;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseModel executeCreateOtpRequest(CreateOtpREQDTO createOtpRequest, String requestorTransId, String appId) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<CreateOtpREQDTO> entity = new HttpEntity<>(createOtpRequest, headers);
            String responseCreateOtp = restTemplate.exchange
                (serviceApiConfig.getOtpCreateOtp(), HttpMethod.POST, entity, String.class).getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseModel dataResponseModel = objectMapper.readValue(responseCreateOtp, ResponseModel.class);
            CreateOtpRESDTO createOtpResponse = objectMapper.convertValue(
                dataResponseModel.getData(),
                new TypeReference<CreateOtpRESDTO>() {
                }
            );
            if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                responseModel.setData(createOtpResponse);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            log.info(requestorTransId + "-" + appId + " Create OTP: " + lpbResCode.getErrorDesc());
        } catch (HttpClientErrorException e) {
            log.error(requestorTransId + "-" + appId + " Exception Create OTP: " + e.getResponseBodyAsString());
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        } catch (Exception e) {
            log.error(requestorTransId + "-" + appId + " Exception Create OTP" + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }
}
