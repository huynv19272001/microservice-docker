package com.lpb.esb.service.auth.controller;

import com.lpb.esb.service.auth.model.response.ResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2021-07-08
 */
@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController {
    @Value(value = "${server.port}")
    private String port;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel<String>> test() {
        ResponseModel<String> responseModel = ResponseModel.<String>builder()
            .data("Res from port: " + port)
            .errorCode("00")
            .errorDesc("Success")
            .build();

        return ResponseEntity.ok(responseModel);
    }
}
