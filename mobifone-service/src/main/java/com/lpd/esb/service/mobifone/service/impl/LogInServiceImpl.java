package com.lpd.esb.service.mobifone.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpd.esb.service.mobifone.configuration.RestTemplateConfig;
import com.lpd.esb.service.mobifone.model.Request;
import com.lpd.esb.service.mobifone.repository.EsbServiceProcessRepository;
import com.lpd.esb.service.mobifone.service.LogInService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Instant;
import java.util.Arrays;

/**
 * @reviewer: Trung.Nguyen
 * @since: 25-Aug-2022
 * */
@Log4j2
@Service
public class LogInServiceImpl implements LogInService {
    @Autowired
    RestTemplateConfig restTemplate;
    @Autowired
    EsbServiceProcessRepository esbServiceProcessRepository;

    private static final String LOGIN_PRODUCT_CODE = "MOBIFONE_LOGIN";
    private static final long TWO_MINUTES = 120000;
    private String tokenKey;

    @SneakyThrows
    @Transactional
    @Override
    public ResponseModel logIn(Request request) {
        String serviceId = request.getHeader().getServiceId();
        String tokenKey;

        LpbResCode lpbResCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel;

        JsonNode loginRes = null;
        ServiceInfo loginServiceInfo = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(),
            serviceId, LOGIN_PRODUCT_CODE, HAS_ROLE).get(0);
        /**
         * Check for an empty UDF1 value or ensure that this value is set to default (example: 0)
         *
         * Solution 1: set the value of UDF1 to zero
         * Solution 2: add check block
         *
         * 		long timeExp = 0L;
         * 		if (loginServiceInfo.getUdf1() != null) timeExp = Long.parseLong(loginServiceInfo.getUdf1());
         *
         * */
        long timeExp = Long.parseLong(loginServiceInfo.getUdf1());
        log.info("timeExp: " + timeExp);

        //if there is no time left
        /**
         * The value of 120000 should be configured in the database (example: using field UDF5 to represent this value)
         * */
        if (Instant.now().toEpochMilli() > (timeExp - TWO_MINUTES)) {
            //header
            HttpHeaders loginHeaders = new HttpHeaders();
            loginHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            loginHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("userName", loginServiceInfo.getUdf3());
            map.add("password", loginServiceInfo.getUdf4());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, loginHeaders);
            ResponseEntity<String> loginResponse = restTemplate.getRestTemplate().postForEntity(
                loginServiceInfo.getConnectorURL(), entity, String.class);
            log.info("loginRes: " + loginResponse);

            ObjectMapper tokenResMapper = new ObjectMapper();
            loginRes = tokenResMapper.readTree(loginResponse.getBody());
            log.info("loginRes: " + loginRes);

//                tokenKey = tokenRes.path("tokenKey").toString().replaceAll("\"", "");
            JsonNode token = loginRes.path("payload").path("token");
            tokenKey = token.path("tokenKey").asText();
            String expDate = token.path("expDate").asText();

            log.info("tokenKey: " + tokenKey);
            //update DB
            if (tokenKey != null && !tokenKey.isEmpty() && !tokenKey.trim().isEmpty()) {
                esbServiceProcessRepository.updateToken(expDate, tokenKey, serviceId, LOGIN_PRODUCT_CODE);
            } else {
                lpbResCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.FAIL.label)
                    .errorDesc("Failure")
                    .build();
            }
        } else {
            tokenKey = loginServiceInfo.getUdf2();
            log.info("tokenKey: " + tokenKey);
        }
        this.tokenKey = tokenKey;
        responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(loginRes)
            .build();
        return responseModel;
    }

    @Override
    public String getToken() {
        return tokenKey;
    }
}
