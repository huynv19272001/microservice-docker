package com.lpb.esb.service.customer.controller;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
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
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode("00")
            .errorDesc("Ping success")
            .build();
        ResponseModel<String> responseModel = ResponseModel.<String>builder()
            .data("Ping ok")
            .resCode(lpbResCode)
            .build();
        return ResponseEntity.ok(responseModel);
    }
}
