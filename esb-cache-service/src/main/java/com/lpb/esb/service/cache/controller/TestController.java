package com.lpb.esb.service.cache.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2022-06-16
 */
@RestController
@RequestMapping(value = "test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Log4j2
public class TestController {
    @RequestMapping(value = "log-body", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> logBody(@RequestBody String body) {
        log.info("body log: {}", body);
        ResponseModel responseModel = ResponseModel.builder()
            .data(body)
            .build();
        return ResponseEntity.ok(responseModel);
    }
}
