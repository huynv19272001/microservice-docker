package com.lpb.service.payoo.controller;

import com.lpb.service.payoo.service.RequestPayooService;
import com.lpb.service.payoo.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ControllerAdvice
@RequestMapping("/api/v1")
public class CommonController {
    @Autowired
    RequestPayooService requestPayooService;
    private static final Logger logger = LogManager.getLogger(CommonController.class);

    @PostMapping("/info/queryBillEx")
    public ResponseEntity<ESBResponse> queryBillEx(@RequestBody ESBRequest payooRequest) throws Throwable {
        logger.info("<--- resquest queryBillEx PAYOORequest: " + payooRequest.toString());

        ESBResponse responseModel = requestPayooService.queryBillBE(payooRequest);
        if (responseModel == null) {
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }

    @PostMapping("/payment/payOnlineBillEx")
    public ResponseEntity<ESBSettleBillResponse> payOnlineBillEx(@RequestBody ESBSettleBillRequest payooRequest) throws Throwable {
        logger.info("<--- resquest payOnlineBillEx PAYOORequest: " + payooRequest.toString());

        ESBSettleBillResponse responseModel = requestPayooService.payOnlineBillEx(payooRequest);
        if (responseModel == null) {
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }

    @PostMapping("/info/getTransactionStatusBE")
    public ResponseEntity<ESBQueryTransResponse> getTransactionStatusBE(@RequestBody ESBQueryTransRequest payooRequest) throws Throwable {
        logger.info("<--- resquest getTransactionStatusBE PAYOORequest: " + payooRequest.toString());

        ESBQueryTransResponse responseModel = requestPayooService.getTransactionStatusBE(payooRequest);
        if (responseModel == null) {
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }
}
