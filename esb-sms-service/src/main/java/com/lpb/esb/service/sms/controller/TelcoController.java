package com.lpb.esb.service.sms.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import com.lpb.esb.service.sms.model.request.TelcoRequest;
import com.lpb.esb.service.sms.service.TelcoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TelcoController {
    @Autowired
    TelcoService telcoService;

    @RequestMapping(value = "v1/telco", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> updateTelco(@RequestBody TelcoRequest telcoRequest) {
        ResponseModel responseModel = telcoService.updateTelco(telcoRequest);
        if (!responseModel.getErrorCode().equals(EsbErrorCode.SUCCESS.label)) {
            return ResponseEntity.badRequest().body(responseModel);
        } else {
            return ResponseEntity.ok(responseModel);
        }
    }
}
