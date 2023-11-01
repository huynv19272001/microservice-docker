package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.service.EsbPartnerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "api/v1/partner", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PartnerAccountController {
    @Autowired
    EsbPartnerAccountService esbPartnerAccountService;

    @GetMapping(value = "account")
    public ResponseEntity<ResponseModel> getPartnerAccountInfo(@RequestParam(value = "service_id") String serviceId, @RequestParam(value = "product_code") String productCode
        , @RequestParam(value = "provider_id") String providerId
    ) {
        ResponseModel responseModel = esbPartnerAccountService.findPartnerAccount(serviceId, productCode, providerId);
        if (ErrorMessage.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
