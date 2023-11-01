package com.lpb.insurance.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.insurance.configuration.RestTemplateConfig;
import com.lpb.insurance.configuration.ServiceConfig;
import com.lpb.insurance.dto.TokenInfoDTO;
import com.lpb.insurance.dto.request.FeeCarsInsuranceDTO;
import com.lpb.insurance.dto.request.FeeCreditInfoDTORequest;
import com.lpb.insurance.dto.request.FeeInfoDTORequest;
import com.lpb.insurance.dto.response.FeeCarsInsuranceDTOResponse;
import com.lpb.insurance.dto.response.FeeCreditInfoDTOResponse;
import com.lpb.insurance.dto.response.FeeInfoDTOResponse;
import com.lpb.insurance.model.EsbRequestDTO;
import com.lpb.insurance.service.FeeService;
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
public class FeeServiceImpl implements FeeService {
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
    public ResponseModel feePrivateInsurance(EsbRequestDTO data) {
        log.info("MSGREQ:" + data);
        ObjectMapper objectMapper = new ObjectMapper();

        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        try {
            FeeInfoDTORequest request = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), FeeInfoDTORequest.class);
            log.info("REQ:" + request);

            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(), data.getHeader().getServiceId(), data.getHeader().getProductCode(), serviceConfig.getHasRole());

            if (serviceInfo == null || serviceInfo.size() <= 0) {
                resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Service not exits!").build();
                responseModel = ResponseModel.builder().resCode(resCode).build();
                return responseModel;
            }

            //validate
//            FeeInfoDTORequest request = (FeeInfoDTORequest) data.getBody().getData();
//            log.info("REQ:" + request);

            responseModel = tokenService.getToken();
            if (!responseModel.getResCode().getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
                return responseModel;
            }

            TokenInfoDTO tokenInfoDTO = (TokenInfoDTO) responseModel.getData();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(tokenInfoDTO.getAccessToken());


            log.info("xuan thanh request: " + objectMapper.writeValueAsString(data.getBody().getData()));

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(data.getBody().getData()), headers);
            String response = restTemplate.getRestTemplate().exchange(serviceInfo.get(0).getConnectorURL(), HttpMethod.POST, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
            FeeInfoDTOResponse feeInfoDTO = objectMapper.readValue(response, FeeInfoDTOResponse.class);
            responseModel = ResponseModel.builder().data(feeInfoDTO).resCode(resCode).build();
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

    @Override
    public ResponseModel feeCreditInsurance(EsbRequestDTO data) {
        log.info("MSGREQ:" + data);
        ObjectMapper objectMapper = new ObjectMapper();

        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        try {
            FeeCreditInfoDTORequest request = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), FeeCreditInfoDTORequest.class);
            log.info("REQ:" + request);

            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(), data.getHeader().getServiceId(), data.getHeader().getProductCode(), serviceConfig.getHasRole());

            if (serviceInfo == null || serviceInfo.size() <= 0) {
                resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Service not exits!").build();
                responseModel = ResponseModel.builder().resCode(resCode).build();
                return responseModel;
            }

            //validate
//            FeeInfoDTORequest request = (FeeInfoDTORequest) data.getBody().getData();
//            log.info("REQ:" + request);

            responseModel = tokenService.getToken();
            if (!responseModel.getResCode().getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
                return responseModel;
            }

            TokenInfoDTO tokenInfoDTO = (TokenInfoDTO) responseModel.getData();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(tokenInfoDTO.getAccessToken());

            log.info("xuan thanh request: " + objectMapper.writeValueAsString(data.getBody().getData()));
            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(data.getBody().getData()), headers);
            String response = restTemplate.getRestTemplate().exchange(serviceInfo.get(0).getConnectorURL(), HttpMethod.POST, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
            FeeCreditInfoDTOResponse feeCreditInfoDTO = objectMapper.readValue(response, FeeCreditInfoDTOResponse.class);
            responseModel = ResponseModel.builder().data(feeCreditInfoDTO).resCode(resCode).build();
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

    @Override
    public ResponseModel feeBaoAnCredit(EsbRequestDTO data) {
        log.info("MSGREQ:" + data);
        ObjectMapper objectMapper = new ObjectMapper();

        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        try {
            FeeCreditInfoDTORequest request = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), FeeCreditInfoDTORequest.class);
            log.info("REQ:" + request);

            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(), data.getHeader().getServiceId(), data.getHeader().getProductCode(), serviceConfig.getHasRole());

            if (serviceInfo == null || serviceInfo.size() <= 0) {
                resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Service not exits!").build();
                responseModel = ResponseModel.builder().resCode(resCode).build();
                return responseModel;
            }

            //validate
//            FeeInfoDTORequest request = (FeeInfoDTORequest) data.getBody().getData();
//            log.info("REQ:" + request);

            responseModel = tokenService.getToken();
            if (!responseModel.getResCode().getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
                return responseModel;
            }

            TokenInfoDTO tokenInfoDTO = (TokenInfoDTO) responseModel.getData();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(tokenInfoDTO.getAccessToken());

            log.info("xuan thanh request: " + objectMapper.writeValueAsString(data.getBody().getData()));

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(data.getBody().getData()), headers);
            String response = restTemplate.getRestTemplate().exchange(serviceInfo.get(0).getConnectorURL(), HttpMethod.POST, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
            FeeCreditInfoDTOResponse feeCreditInfoDTO = objectMapper.readValue(response, FeeCreditInfoDTOResponse.class);
            responseModel = ResponseModel.builder().data(feeCreditInfoDTO).resCode(resCode).build();
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

    @Override
    public ResponseModel feeCarsInsurance(EsbRequestDTO data) {
        log.info("MSGREQ:" + data);
        ObjectMapper objectMapper = new ObjectMapper();

        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        try {
            FeeCarsInsuranceDTO request = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), FeeCarsInsuranceDTO.class);
            log.info("REQ:" + request);

            List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(), data.getHeader().getServiceId(), data.getHeader().getProductCode(), serviceConfig.getHasRole());

            if (serviceInfo == null || serviceInfo.size() <= 0) {
                resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc("Service not exits!").build();
                responseModel = ResponseModel.builder().resCode(resCode).build();
                return responseModel;
            }

            //validate
//            FeeInfoDTORequest request = (FeeInfoDTORequest) data.getBody().getData();
//            log.info("REQ:" + request);

            responseModel = tokenService.getToken();
            if (!responseModel.getResCode().getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
                return responseModel;
            }

            TokenInfoDTO tokenInfoDTO = (TokenInfoDTO) responseModel.getData();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(tokenInfoDTO.getAccessToken());

            log.info("xuan thanh request: " + objectMapper.writeValueAsString(data.getBody().getData()));

            HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(data.getBody().getData()), headers);
            String response = restTemplate.getRestTemplate().exchange(serviceInfo.get(0).getConnectorURL(), HttpMethod.POST, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
            FeeCarsInsuranceDTOResponse feeCarsInfoDTO = objectMapper.readValue(response, FeeCarsInsuranceDTOResponse.class);
            responseModel = ResponseModel.builder().data(feeCarsInfoDTO).resCode(resCode).build();
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
