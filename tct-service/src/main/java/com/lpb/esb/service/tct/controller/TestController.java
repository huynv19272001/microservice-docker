package com.lpb.esb.service.tct.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.tct.model.config.TctFileConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tudv1 on 2022-02-28
 */
@RestController
@RequestMapping(value = "test")
@Log4j2
public class TestController {
    @Autowired
    TctFileConfig tctFileConfig;

    @RequestMapping(value = "cert", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity test(@RequestBody String data) throws Exception {
//        String sign = CertSign.sign(data, tctFileConfig.getCertPrefix() + tctFileConfig.getCertPfx(), tctFileConfig.getCertPrefix() + tctFileConfig.getCertCer());
        String sign = null;

//        ResponseModel responseModel = ResponseModel.builder()
//            .data(sign)
//            .build();

        return ResponseEntity.ok(sign);
    }

    @RequestMapping(value = "log-body", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> logBodyPost(@RequestBody String body, HttpServletRequest request) {
        getAllHeader(request).forEach((k, v) -> log.info("Header {} : {}", k, v));
        log.info("body log: {}", body);
        ResponseModel responseModel = ResponseModel.builder()
            .data(body)
            .build();
        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "log-body", method = RequestMethod.PUT)
    public ResponseEntity<ResponseModel> logBodyPut(@RequestBody String body, HttpServletRequest request) {
        getAllHeader(request).forEach((k, v) -> log.info("Header {} : {}", k, v));
        log.info("body log: {}", body);
        ResponseModel responseModel = ResponseModel.builder()
            .data(body)
            .build();
        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "log-body", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseModel> logBodyDelete(@RequestBody String body, HttpServletRequest request) {
        getAllHeader(request).forEach((k, v) -> log.info("Header {} : {}", k, v));
        log.info("body log: {}", body);
        ResponseModel responseModel = ResponseModel.builder()
            .data(body)
            .build();
        return ResponseEntity.ok(responseModel);
    }

    private Map<String, String> getAllHeader(HttpServletRequest request) {
        Map<String, String> mapHeader = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            mapHeader.put(name, request.getHeader(name));
        }
        return mapHeader;

    }
}
