package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.service.EsbPaymentMethodChannelEvnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "api/v1/evn", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PaymentMethodChannelEvnController {
    @Autowired
    EsbPaymentMethodChannelEvnService esbPaymentMethodChannelEvnService;

    @GetMapping(value = "bill_print")
    public ResponseEntity<ResponseModel> getPartnerAccountInfo(@RequestParam(value = "product_code") String productCode, @RequestParam(value = "payment_method") String paymentMethod, @RequestParam(value = "channel") String channel
        , @RequestParam(value = "bill_print") String billPrint
    ) {
        ResponseModel responseModel = esbPaymentMethodChannelEvnService.findPaymentMethodChannelEvn(productCode, paymentMethod, channel, billPrint);
        if (ErrorMessage.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
