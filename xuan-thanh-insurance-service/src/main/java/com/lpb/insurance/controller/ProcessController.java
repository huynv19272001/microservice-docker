package com.lpb.insurance.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.insurance.model.EsbRequestDTO;
import com.lpb.insurance.service.ProcessService;
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
@RequestMapping(value = "/api/v1/process", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProcessController {
    @Autowired
    private ProcessService processService;


    @RequestMapping(value = "home",  method = RequestMethod.POST)
    public ResponseEntity<?> homeInsurance(@RequestBody EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO);
        ResponseModel responseModel = processService.homeInsurance(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "health",  method = RequestMethod.POST)
    public ResponseEntity<?> health(@RequestBody EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO);
        ResponseModel responseModel = processService.healthCreditInsurance(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
