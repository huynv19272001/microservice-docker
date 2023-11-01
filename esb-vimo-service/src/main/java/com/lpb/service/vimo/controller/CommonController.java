package com.lpb.service.vimo.controller;

import com.lpb.service.vimo.service.RequestVIMOService;
import com.lpb.service.vimo.model.*;
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
    RequestVIMOService requestVIMOService;
    private static final Logger logger = LogManager.getLogger(CommonController.class);

    @PostMapping("/info/querybill")
    public ResponseEntity<ESBResponse> getQueryBill(@RequestBody ESBRequest queryBillReq) {
        logger.info("<--- VIMORequest: " + queryBillReq.toString());

        ESBResponse responseModel = requestVIMOService.querybill(queryBillReq);
        logger.info("<--- VIMOresponseModel: " + responseModel.toString());

        if (responseModel == null) {
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }

    @PostMapping("/payment/paybillv2")
    public ResponseEntity<ESBSettleBillResponse> paybillv2(@RequestBody ESBSettleBillRequest payBillv2Req) {
        logger.info("<--- VIMORequest: " + payBillv2Req.toString());

        ESBSettleBillResponse responseModel = requestVIMOService.paybillv2(payBillv2Req);
        logger.info("<--- VIMOresponseModel: " + responseModel.toString());

        if (responseModel == null) {
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

    }

    @PostMapping("/payment/paybillpartial")
    public ResponseEntity<ESBSettleBillResponse> paybillpartial(@RequestBody ESBSettleBillRequest payBillPartialReq) {
        logger.info("<--- VIMORequest: " + payBillPartialReq.toString());

        ESBSettleBillResponse responseModel = requestVIMOService.paybillpartial(payBillPartialReq);
        logger.info("<--- VIMOresponseModel: " + responseModel.toString());

        if (responseModel == null) {
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }

    @PostMapping("/info/getaddfee")
    public ResponseEntity<ESBResponse> getaddfee(@RequestBody ESBRequest getAddFeeReq) {
        logger.info("<--- VIMORequest: " + getAddFeeReq.toString());

        ESBResponse responseModel = requestVIMOService.getaddfee(getAddFeeReq);
        logger.info("<--- VIMOresponseModel: " + responseModel.toString());

        if (responseModel == null) {
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }
}
