package com.lpd.esb.service.econtract.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpd.esb.service.econtract.model.*;
import com.lpd.esb.service.econtract.service.EcontractService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.lpb.esb.service.common.utils.CacheUtils;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class EcontractServiceImpl implements EcontractService {
    @Autowired
    RestTemplate restTemplateLB;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseDTO econtract(RequestDTO request) {
        EsbDTO header = EsbDTO.builder()
            .serviceId(request.getHeader().getServiceId())
            .productCode(request.getHeader().getProductCode())
            .build();

        ResponseDTO response = new ResponseDTO();

        List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplateLB,
            request.getHeader().getServiceId(), request.getHeader().getProductCode(), "FEService");

        ObjectMapper reqBodyMapper = new ObjectMapper();
        reqBodyMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //get access token
        String expTime = CacheUtils.getCacheValue(restTemplateLB, "econtract", "expTime");
        if(expTime != null){
            Long expTimeSeconds = Long.parseLong(expTime);
            if(expTimeSeconds < 120){
                setAccessToken(request);
            }
        }
        else{
            setAccessToken(request);
        }

        String accessToken = CacheUtils.getCacheValue(restTemplateLB, "econtract", "accessToken");
        headers.setBearerAuth(accessToken);

        try {
            //replace
            String reqBody = reqBodyMapper.writeValueAsString(request.getBody());
            reqBody = reqBody.replaceAll("is_marker", "isMarker")
                .replaceAll("is_esign", "isEsign");
//            log.info("reqBody: " + reqBody);
            //post request
            HttpEntity<String> entity = new HttpEntity<>(reqBody, headers);
            ResponseEntity<String> econtractResponse = restTemplate.postForEntity(
                serviceInfo.get(0).getConnectorURL(), entity, String.class);

            ObjectMapper resMapper = new ObjectMapper();
            JsonNode res = resMapper.readTree(econtractResponse.getBody());

            response = ResponseDTO.builder()
                .header(header)
                .body(res)
                .build();
        }
        catch (HttpClientErrorException exception){
            log.error(exception);

            ObjectMapper errorMapper = new ObjectMapper();
            try {
                JsonNode errorBody = errorMapper.readTree(exception.getResponseBodyAsString());

                ErrorBodyDTO error = ErrorBodyDTO.builder()
                    .status(exception.getStatusCode().toString())
                    .errorBody(errorBody)
                    .build();
                response = ResponseDTO.builder()
                    .header(header)
                    .body(error)
                    .build();
            }
            catch(Exception e){
                log.error("errorBody exception: " + e);
            }
        }
        catch(Exception e){
            log.error("exception: " + e);
        }
        return response;
    }

    private void setAccessToken(RequestDTO request){
        List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplateLB,
            request.getHeader().getServiceId(), "FPT_ECONTRACT_TOKEN", "FEService");

        EcontractGetTokenReqDTO getTokenReq = EcontractGetTokenReqDTO.builder()
            .username(serviceInfo.get(0).getUdf1())
            .password(serviceInfo.get(0).getUdf2())
            .rememberMe(false)
            .clientid("econtract_integrate")
            .clientsecret("econtract_integrate")
            .build();

        log.info("GetToken Request: " + getTokenReq);

        ObjectMapper reqBodyMapper = new ObjectMapper();
        reqBodyMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        try {
            HttpEntity<String> httpEntity = new HttpEntity<>(
                reqBodyMapper.writeValueAsString(getTokenReq), headers);

            ResponseEntity<EcontractGetTokenResDTO> response = restTemplate.postForEntity
                (serviceInfo.get(0).getConnectorURL(), httpEntity, EcontractGetTokenResDTO.class);

            log.info("GetToken Response: " + response);

            String accessToken = response.getBody().getAccessToken();
            String expTime = response.getBody().getExpTime();

            //get time in seconds until access token expires
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            OffsetDateTime expDateTime = OffsetDateTime.parse(expTime, timeFormatter);
            OffsetDateTime currentDateTime = OffsetDateTime.now();
            Long tokenExp = ChronoUnit.SECONDS.between(currentDateTime, expDateTime);
//            log.info("expDateTime: " + expDateTime);
//            log.info("currentDateTime: " + currentDateTime);
//            log.info(tokenExp);

            CacheUtils.putDataToCache(restTemplateLB, "econtract",
                "accessToken", accessToken, tokenExp);
            CacheUtils.putDataToCache(restTemplateLB, "econtract",
                "expTime", tokenExp.toString(), tokenExp);
        }
        catch(Exception e){
            log.info(e);
        }
    }
}
