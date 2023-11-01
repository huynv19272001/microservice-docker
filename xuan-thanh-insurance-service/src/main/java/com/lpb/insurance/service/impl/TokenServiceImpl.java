package com.lpb.insurance.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.CacheUtils;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.insurance.configuration.RestTemplateConfig;
import com.lpb.insurance.configuration.ServiceConfig;
import com.lpb.insurance.dto.TokenInfoDTO;
import com.lpb.insurance.service.TokenService;
import com.lpb.insurance.utils.ESBUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@Log4j2
public class TokenServiceImpl implements TokenService {
    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    RestTemplateConfig restTemplate;

    @Override
    public ResponseModel getToken() {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        ObjectMapper objectMapper = new ObjectMapper();
        TokenInfoDTO tokenInfoDTO = null;

        String token = CacheUtils.getCacheValue(restTemplate.getRestTemplateLB(), serviceConfig.getServiceId() + serviceConfig.getToken(), serviceConfig.getToken());
        if (token != null) {
            try {
                tokenInfoDTO = objectMapper.readValue(token, TokenInfoDTO.class);
            } catch (JsonProcessingException e) {
                tokenInfoDTO = null;
            }
        }

        if (tokenInfoDTO != null && validateExpiresToken(tokenInfoDTO)) {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
            responseModel = ResponseModel.builder().data(tokenInfoDTO).resCode(resCode).build();
            return responseModel;
        }

        responseModel = executeGetToken();

        if (responseModel.getResCode().getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
            tokenInfoDTO = (TokenInfoDTO) responseModel.getData();
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(ESBUtils.yyyyMMddTHHmmssSSSZ);
        OffsetDateTime expDateTime = OffsetDateTime.parse(ESBUtils.formatDate(tokenInfoDTO.getExpires(), ESBUtils.yyyyMMddTHHmmssSSSZ), timeFormatter);
        OffsetDateTime currentDateTime = OffsetDateTime.now();

        Long tokenExp = ChronoUnit.SECONDS.between(currentDateTime, expDateTime);
        log.info("tokenExp: " + currentDateTime + "_" + expDateTime + "_" + tokenExp);
        try {
            boolean status = CacheUtils.putDataToCache(restTemplate.getRestTemplateLB(), serviceConfig.getServiceId() + serviceConfig.getToken(), serviceConfig.getToken(), objectMapper.writeValueAsString(tokenInfoDTO), tokenExp);
            if (status) {
                resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
                responseModel = ResponseModel.builder().data(tokenInfoDTO).resCode(resCode).build();
            } else {
                resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Token putDataToCache fail!").build();
                responseModel = ResponseModel.builder().resCode(resCode).build();
            }
        } catch (JsonProcessingException e) {
            log.error("Token putDataToCache fail: " + e.getMessage());

            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Token putDataToCache fail!").build();
            responseModel = ResponseModel.builder().resCode(resCode).build();
        }
        return responseModel;
    }

    private boolean validateExpiresToken(TokenInfoDTO token) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(ESBUtils.yyyyMMddTHHmmssSSSZ);
        OffsetDateTime expDateTime = OffsetDateTime.parse(ESBUtils.formatDate(token.getExpires(), ESBUtils.yyyyMMddTHHmmssSSSZ), timeFormatter);
        OffsetDateTime currentDateTime = OffsetDateTime.now();

        Long tokenExp = ChronoUnit.SECONDS.between(currentDateTime, expDateTime);
        if (tokenExp <= Long.valueOf(serviceConfig.getExpiresToken())) return false;
        else return true;
    }

    @Override
    public ResponseModel executeGetToken() {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(), serviceConfig.getServiceId(), serviceConfig.getToken(), serviceConfig.getHasRole());

        if (serviceInfo == null || serviceInfo.size() <= 0) {
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Exception Get Token").build();
            responseModel = ResponseModel.builder().resCode(resCode).build();
            return responseModel;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);

            String body = "grant_type=password&username=" + serviceInfo.get(0).getUdf1() + "&password=" + serviceInfo.get(0).getUdf2() + "&client_id=" + serviceInfo.get(0).getUdf3() + "&client_secret=" + serviceInfo.get(0).getUdf4() + "&redirect_uri=";

            HttpEntity entity = new HttpEntity<>(body, headers);
            log.info(serviceInfo.get(0).getConnectorURL() + "_" + entity);

            ResponseEntity<String> response = restTemplate.getRestTemplate().exchange(serviceInfo.get(0).getConnectorURL(), HttpMethod.POST, entity, String.class);
            TokenInfoDTO dataResponseModel = objectMapper.readValue(response.getBody(), TokenInfoDTO.class);

            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
            responseModel = ResponseModel.builder().data(dataResponseModel).resCode(resCode).build();
        } catch (HttpClientErrorException e) {
            log.error("Exception Get Token:" + e.getResponseBodyAsString());
            log.error("Exception Get Token:" + e);

            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Exception Get Token: " + e.getMessage()).build();
            responseModel = ResponseModel.builder().resCode(resCode).build();
        } catch (Exception e) {
            log.error("Exception Get Token:" + e);

            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc(ErrorMessage.FAIL.description).build();
            responseModel = ResponseModel.builder().resCode(resCode).build();
        }
        return responseModel;
    }
}
