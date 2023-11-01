package com.lpb.insurance.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.insurance.configuration.RestTemplateConfig;
import com.lpb.insurance.configuration.ServiceConfig;
import com.lpb.insurance.dto.TokenInfoDTO;
import com.lpb.insurance.dto.request.DanhSachHoSoDTO;
import com.lpb.insurance.dto.request.GetHuyHopDong;
import com.lpb.insurance.dto.response.ErrorDTO;
import com.lpb.insurance.model.EsbRequestDTO;
import com.lpb.insurance.service.ContractService;
import com.lpb.insurance.service.TokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class ContractServiceImpl implements ContractService {
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
    public ResponseModel list(EsbRequestDTO data) {
        log.info("MSGREQ:" + data);
        ObjectMapper objectMapper = new ObjectMapper();

        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        try {
            DanhSachHoSoDTO request = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), DanhSachHoSoDTO.class);
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

            HttpEntity<DanhSachHoSoDTO> entity = new HttpEntity<>( (request), headers );

            String response = restTemplate.getRestTemplate().exchange(serviceInfo.get(0).getConnectorURL(), HttpMethod.POST, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);
            JsonNode res = objectMapper.readTree(response);
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
            responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(res)
                .build();
        } catch (HttpClientErrorException httpException) {
            httpException.printStackTrace();
            log.error("HttpClientErrorException: " + httpException);
            JsonNode res = null;
            try {
                res = objectMapper.readTree(httpException.getResponseBodyAsString());
            }
            catch (Exception jsonException){
                log.error("JSON Exception: " + jsonException);
            }
            resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service Failure")
                .refDesc(res.asText())
                .build();
            responseModel = ResponseModel.builder()
                .resCode(resCode)
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception:" + data.getHeader().getMsgId() + "_" + e.getMessage());
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc(e.getMessage()).build();
            responseModel = ResponseModel.builder().resCode(resCode).build();
        }
        return responseModel;
    }

    @Override
    public ResponseModel cancel(EsbRequestDTO data) {
        log.info("MSGREQ:" + data);
        ObjectMapper objectMapper = new ObjectMapper();

        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        try {
            GetHuyHopDong request = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), GetHuyHopDong.class);
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
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(tokenInfoDTO.getAccessToken());

            HttpEntity<DanhSachHoSoDTO> entity = new HttpEntity<>( null, headers );

            URI uri = UriComponentsBuilder.fromUriString(serviceInfo.get(0).getConnectorURL())
                .queryParam("dSoId", request.getDSoId())
                .queryParam("sSoHD", request.getSSoHD())
                .build().toUri();
            log.info("URI: " + uri);

            String response = restTemplate.getRestTemplate().exchange(uri, HttpMethod.GET, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);
            JsonNode res = objectMapper.readTree(response);
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
            responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(res)
                .build();
        } catch (HttpClientErrorException httpException) {
            httpException.printStackTrace();
            log.error("HttpClientErrorException: " + httpException);
            ErrorDTO errorCodeMessage = null;
            JsonNode res = null;
            String responseBody = httpException.getResponseBodyAsString();
            try {
                errorCodeMessage = objectMapper.readValue(responseBody, ErrorDTO.class);
                res = objectMapper.readTree(responseBody);
            }
            catch (Exception jsonException){
                log.error("JSON Exception: " + jsonException);
            }
            resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Service Failure")
                .refCode(errorCodeMessage.getErrorCode())
                .refDesc(errorCodeMessage.getErrorMess())
                .build();
            responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(res)
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception:" + data.getHeader().getMsgId() + "_" + e.getMessage());
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.FAIL.label).errorDesc(e.getMessage()).build();
            responseModel = ResponseModel.builder().resCode(resCode).build();
        }
        return responseModel;
    }

    @Override
    public ResponseModel status(EsbRequestDTO data) {
        log.info("MSGREQ:" + data);
        ObjectMapper objectMapper = new ObjectMapper();

        LpbResCode resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
        ResponseModel responseModel = ResponseModel.builder().resCode(resCode).build();

        try {
            GetHuyHopDong request = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), GetHuyHopDong.class);
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
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(tokenInfoDTO.getAccessToken());

            HttpEntity<DanhSachHoSoDTO> entity = new HttpEntity<>( null, headers );

            URI uri = UriComponentsBuilder.fromUriString(serviceInfo.get(0).getConnectorURL())
                .queryParam("dSoId", request.getDSoId())
                .build().toUri();
            log.info("URI" + uri);

            String response = restTemplate.getRestTemplate().exchange(uri, HttpMethod.GET, entity, String.class).getBody();

            log.info(data.getHeader().getMsgId() + "_" + response);
            //remove beginning and ending double quotes and all backslash
            response = response.replaceAll("\\\\", "");
            response = response.substring(1, response.length()-1);

            JsonNode res = objectMapper.readTree(response);
            resCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();
            responseModel = ResponseModel.builder()
                .resCode(resCode)
                .data(res)
                .build();
        }
        catch (RestClientException e) {
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
