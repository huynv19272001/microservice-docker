package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.config.service.EsbErrorPartnerMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2021-11-16
 */
@RestController
@RequestMapping(value = "partner", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EsbErrorPartnerMessageController {
    @Autowired
    EsbErrorPartnerMessageService esbErrorPartnerMessageService;

    @RequestMapping(value = "get-error-message", method = RequestMethod.GET)
    ResponseEntity<ResponseModel> getErrorMessage(@RequestParam(value = "serviceId") String serviceId
        , @RequestParam(value = "partnerId") String partnerId
        , @RequestParam(value = "partnerCode") String partnerCode
    ) {
        ResponseModel responseModel = esbErrorPartnerMessageService.getErrorMessage(serviceId, partnerId, partnerCode);

        return ResponseEntity.ok(responseModel);
    }
}
