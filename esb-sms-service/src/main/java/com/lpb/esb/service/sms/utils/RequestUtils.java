//package com.lpb.esb.service.sms.utils;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.ResourceAccessException;
//import org.springframework.web.client.RestClientResponseException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//
///**
// * Created by tudv1 on 2022-04-28
// */
//@Component
//@Log4j2
//public class RequestUtils {
//    @Autowired
//    RestTemplate restTemplate;
//
//    public ResponseEntity<String> executePostReq(String url, String body) {
//        ResponseEntity<String> responseEntity = null;
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
//
//            HttpEntity<String> entity = new HttpEntity<>(body, headers);
//            responseEntity = restTemplate.postForEntity(url, entity, String.class);
//        } catch (RestClientResponseException e) {
//            responseEntity = ResponseEntity
//                .status(e.getRawStatusCode())
//                .body(e.getResponseBodyAsString());
//        } catch (ResourceAccessException e) {
//
//        }
//        log.info("Response code [{}] body {}", responseEntity.getStatusCode(), responseEntity.getBody());
//        return responseEntity;
//    }
//
//}
