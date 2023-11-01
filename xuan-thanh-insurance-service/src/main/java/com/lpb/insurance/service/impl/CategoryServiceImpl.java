package com.lpb.insurance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.insurance.configuration.RestTemplateConfig;
import com.lpb.insurance.configuration.ServiceConfig;
import com.lpb.insurance.dto.TokenInfoDTO;
import com.lpb.insurance.model.EsbRequestDTO;
import com.lpb.insurance.service.CategoryService;
import com.lpb.insurance.service.TokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    RestTemplateConfig restTemplate;

    @Autowired
    ServiceConfig serviceConfig;

    @Autowired
    TokenService tokenService;

    @Override
    public ResponseModel executeFunction(EsbRequestDTO data) {
        return null;
    }

    @Override
    public ResponseModel category(EsbRequestDTO data) {
        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        try {
            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(), data.getHeader().getServiceId(), data.getHeader().getProductCode(), serviceConfig.getHasRole());

            if (serviceInfo == null || serviceInfo.size() <= 0) {
                resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Service not exits!").build();
                responseModel = ResponseModel.builder().resCode(resCode).build();
                return responseModel;
            }

            responseModel = tokenService.getToken();
            if (!responseModel.getResCode().getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
                return responseModel;
            }

            TokenInfoDTO tokenInfoDTO = (TokenInfoDTO) responseModel.getData();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(tokenInfoDTO.getAccessToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);
            String response = restTemplate.getRestTemplate().exchange(serviceInfo.get(0).getConnectorURL(), HttpMethod.GET, entity, String.class).getBody();
            log.info(serviceInfo.get(0).getConnectorURL());
            log.info(data.getHeader().getMsgId() + "_" + response);
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            List<Object> dataObject = objectMapper.readValue(response, typeFactory.constructCollectionType(List.class, Object.class));
            responseModel = ResponseModel.builder().data(dataObject).resCode(resCode).build();
        } catch (RestClientException e) {
            e.printStackTrace();
            log.error("RestClientException:" + data.getHeader().getMsgId() + "_" + e.getMessage());
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc(e.getMessage()).build();
            responseModel = ResponseModel.builder().resCode(resCode).build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception:" + data.getHeader().getMsgId() + "_" + e.getMessage());
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc(e.getMessage()).build();
            responseModel = ResponseModel.builder().resCode(resCode).build();
        }
        return responseModel;
    }
}
