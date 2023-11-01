package com.lpb.esb.service.auth.controller;

import com.lpb.esb.service.auth.model.response.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2021-07-09
 */
@RestController
public class PingController {
    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel<String>> ping() {
        ResponseModel<String> responseModel = ResponseModel.<String>builder()
            .data("Ping ok")
            .errorCode("00")
            .errorDesc("Ping success")
            .build();
        return ResponseEntity.ok(responseModel);
    }
}
