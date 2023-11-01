package com.lpb.service.mafc.controller;

import com.lpb.service.mafc.service.RequestMAFCService;
import com.lpb.service.mafc.model.*;
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
    RequestMAFCService requestMAFCService;
    private static final Logger logger = LogManager.getLogger(CommonController.class);

    @PostMapping("/info/getLoanInforReq")
    public ResponseEntity<MAFCResponse> getLoanInfor(@RequestBody MAFCRequest getLoanInforReq) {
        logger.info("<--- MAFCRequest: " + getLoanInforReq.toString());

        MAFCResponse responseModel = requestMAFCService.getLoanInfor(getLoanInforReq);
        logger.info("<--- MAFCResponse: " + responseModel.toString());

        if (responseModel == null) {
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }

    @PostMapping("/payment/payBillMafc")
    public ResponseEntity<MAFCSettleBillResponse> payBillMafc(@RequestBody MAFCSettleBillRequest payBillMafcReq) {
        logger.info("<--- MAFCSettleBillRequest: " + payBillMafcReq.toString());

        MAFCSettleBillResponse responseModel = requestMAFCService.payBillMafc(payBillMafcReq);
        logger.info("<--- MAFCSettleBillResponse: " + responseModel.toString());

        if (responseModel == null) {
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }
    }

}
