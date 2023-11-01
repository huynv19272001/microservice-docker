package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.service.EsbSettleAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Created by tudv1 on 2022-07-08
 */
@RestController
@RequestMapping(value = "api/v1/settle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SettleAccountController {
    @Autowired
    EsbSettleAccountService esbSettleAccountService;

    @GetMapping(value = "account")
    public ResponseEntity<ResponseModel> getSettleAccountInfo(@RequestParam(value = "service_id") String serviceId
        , @RequestParam(value = "provider_id") String providerId
    ) {
        ResponseModel responseModel = esbSettleAccountService.findSettleAccount(serviceId, Arrays.asList(providerId.split(",")));
        if (ErrorMessage.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
