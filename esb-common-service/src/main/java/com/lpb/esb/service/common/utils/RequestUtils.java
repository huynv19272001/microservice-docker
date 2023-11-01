package com.lpb.esb.service.common.utils;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by tudv1 on 2022-04-28
 */
@Log4j2
public class RequestUtils {

    public static ResponseEntity<String> executePostReq(RestTemplate restTemplate, String url, String body) {
        ResponseEntity<String> responseEntity = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            responseEntity = restTemplate.postForEntity(url, entity, String.class);
        } catch (RestClientResponseException e) {
            log.error("error from api: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(e.getRawStatusCode())
                .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("error from server: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
        log.info("Response code [{}] body {}", responseEntity.getStatusCode(), responseEntity.getBody());
        return responseEntity;
    }

    public static ResponseEntity<String> executePutReq(RestTemplate restTemplate, String url, String body) {
        ResponseEntity<String> responseEntity = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            responseEntity = restTemplate.exchange(
                url
                , HttpMethod.PUT
                , entity
                , String.class)
            ;
        } catch (RestClientResponseException e) {
            log.error("error from api: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(e.getRawStatusCode())
                .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("error from server: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
        log.info("Response code [{}] body {}", responseEntity.getStatusCode(), responseEntity.getBody());
        return responseEntity;
    }

    public static ResponseModel executePostXmlReq(RestTemplate restTemplate, String url, String body) {
        ResponseEntity<String> responseEntity = null;
        try {
            HttpHeaders headers = new HttpHeaders();

//            Tue Jul 19 14:03:59 ICT 2022:DEBUG: >> "Content-Type: text/xml;charset=UTF-8[\r][\n]"
            headers.add("Content-Type", "text/xml");
//            headers.setContentType(MediaType.APPLICATION_XML);
//            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

            HttpEntity<String> entity = new HttpEntity<>(body, headers);
            responseEntity = restTemplate.postForEntity(url, entity, String.class);
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.SUCCESS.label)
                .errorDesc("Success")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .build();
            return responseModel;
        } catch (RestClientResponseException e) {
            log.error("error from api: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(e.getRawStatusCode())
                .body(e.getResponseBodyAsString());
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Failed")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .build();
            return responseModel;
        } catch (Exception e) {
            log.error("error from server: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
            LpbResCode resCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Failed")
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(resCode)
                .build();
            return responseModel;
        }
    }


    public static ResponseEntity<String> executePostReqFormBody(RestTemplate restTemplate, String url, HttpEntity httpEntity) {
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        } catch (RestClientResponseException e) {
            log.error("error from api: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(e.getRawStatusCode())
                .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("error from server: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
        log.info("Response code [{}] body {}", responseEntity.getStatusCode(), responseEntity.getBody());
        return responseEntity;
    }


    public static ResponseEntity<String> executePutReqFormBody(RestTemplate restTemplate, String url, HttpEntity httpEntity) {
        ResponseEntity<String> responseEntity = null;
        try {

            responseEntity = restTemplate.exchange(
                url
                , HttpMethod.PUT
                , httpEntity
                , String.class)
            ;
        } catch (RestClientResponseException e) {
            log.error("error from api: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(e.getRawStatusCode())
                .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("error from server: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
        log.info("Response code [{}] body {}", responseEntity.getStatusCode(), responseEntity.getBody());
        return responseEntity;
    }

    public static ResponseEntity<String> executeGetReq(RestTemplate restTemplate, String url) {
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity(url, String.class);
        } catch (RestClientResponseException e) {
            log.error("error from api: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(e.getRawStatusCode())
                .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("error from server: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
        log.info("Response code [{}] body {}", responseEntity.getStatusCode(), responseEntity.getBody());
        return responseEntity;
    }

    public static ResponseEntity<String> executeGetReq(RestTemplate restTemplate, String url, HttpEntity httpEntity) {
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                url
                , HttpMethod.GET
                , httpEntity
                , String.class
            );
        } catch (RestClientResponseException e) {
            log.error("error from api: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(e.getRawStatusCode())
                .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("error from server: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
        log.info("Response code [{}] body {}", responseEntity.getStatusCode(), responseEntity.getBody());
        return responseEntity;
    }

    public static ResponseEntity<String> executeGetReqNoLog(RestTemplate restTemplate, String url, HttpEntity httpEntity) {
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                url
                , HttpMethod.GET
                , httpEntity
                , String.class
            );
        } catch (RestClientResponseException e) {
            log.error("error from api: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(e.getRawStatusCode())
                .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("error from server: {}", e.getMessage());
            responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
//        log.info("Response code [{}] body {}", responseEntity.getStatusCode(), responseEntity.getBody());
        return responseEntity;
    }
}
