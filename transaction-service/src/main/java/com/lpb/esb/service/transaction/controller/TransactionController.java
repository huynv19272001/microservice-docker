package com.lpb.esb.service.transaction.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.transaction.model.EsbRequestDTO;
import com.lpb.esb.service.transaction.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping(value = "transaction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "bill", method = RequestMethod.POST)
    public ResponseEntity<?> bill(@RequestBody EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO);
        ResponseModel responseModel = transactionService.bill(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "revert", method = RequestMethod.POST)
    public ResponseEntity<?> revert(@RequestBody EsbRequestDTO esbRequestDTO){
        log.info(esbRequestDTO);
        ResponseModel responseModel = transactionService.revert(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "v2/bill", method = RequestMethod.POST)
    public ResponseEntity<?> billV2(@RequestBody EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO);
        ResponseModel responseModel = transactionService.billV2(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
