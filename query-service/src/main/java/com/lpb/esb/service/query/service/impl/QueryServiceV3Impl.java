package com.lpb.esb.service.query.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.query.model.EsbRequestDTO;
import com.lpb.esb.service.query.service.QueryServiceV3;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class QueryServiceV3Impl implements QueryServiceV3 {

    @Autowired
    RestTemplate restTemplateLB;

    @Override
    public ResponseModel search(EsbRequestDTO data) {
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Service Success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .build();

        try {
            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplateLB
                , data.getHeader().getServiceId()
                , data.getHeader().getProductCode());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(data), headers);

//            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            String response = restTemplateLB.exchange
                (serviceInfo.get(0).getUrlApi(), HttpMethod.POST, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);
/*
            EsbBodyInfoDTO bodyResponse;
            if (data.getBody() != null && data.getBody().getTransactionInfo() != null) {
                bodyResponse = EsbBodyInfoDTO.builder()
                    .transactionInfo(data.getBody().getTransactionInfo())
                    .data(objectMapper.readTree(response).path("data"))
                    .build();
            } else {
                bodyResponse = EsbBodyInfoDTO.builder()
                    .data(objectMapper.readTree(response).path("data"))
                    .build();
            }

            EsbResponseDTO esbResponse = EsbResponseDTO.builder()
                .header(data.getHeader())
                .body(bodyResponse)
                .build();
            JSONObject dataJson = new JSONObject(objectMapper.writeValueAsString(esbResponse));
 */
            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(objectMapper.readTree(response).path("data"))
                .build();
            return responseModel;
        } catch (HttpClientErrorException exception) {
            log.info(data.getHeader().getMsgId() + " | " + exception.getMessage());
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

                responseModel = objectMapper.readValue(exception.getResponseBodyAsString(), ResponseModel.class);
            } catch (JsonProcessingException jsonProcessingException) {
                log.info(data.getHeader().getMsgId() + "_" + exception.getMessage());
                lpbResCode = LpbResCode.builder()
                    .errorCode(EsbErrorCode.FAIL.label)
                    .errorDesc(exception.getMessage())
                    .build();
                responseModel = ResponseModel.builder()
                    .resCode(lpbResCode)
                    .build();
            }
        } catch (Exception e) {
            log.error(data.getHeader().getMsgId() + "_" + e);
            e.printStackTrace();
            lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc(e.toString())
                .build();
            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
        }
        return responseModel;
    }
}
