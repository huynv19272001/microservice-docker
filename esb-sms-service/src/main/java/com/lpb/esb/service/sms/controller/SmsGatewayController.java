package com.lpb.esb.service.sms.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.sms.model.request.SmsCategoryRequest;
import com.lpb.esb.service.sms.service.SmsGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2022-05-24
 */
@RestController
@RequestMapping(value = "sms/gateway")
public class SmsGatewayController {
    @Autowired
    SmsGatewayService smsGatewayService;

    @RequestMapping(value = "category", method = RequestMethod.POST)
    public ResponseEntity sendSmsCategory(@RequestBody SmsCategoryRequest request) {
        ResponseModel responseModel = smsGatewayService.smsGatewaySend(request);
        if (responseModel.getResCode().getErrorCode().equals("ESB-000")) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
