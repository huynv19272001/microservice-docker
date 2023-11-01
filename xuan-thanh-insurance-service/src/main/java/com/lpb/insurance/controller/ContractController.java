package com.lpb.insurance.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.insurance.model.EsbRequestDTO;
import com.lpb.insurance.service.ContractService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@Log4j2
@RequestMapping(value = "/api/v1/contract", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ContractController {
    @Autowired
    private ContractService contractService;

    @RequestMapping(value = "list",  method = RequestMethod.POST)
    public ResponseEntity<?> danhSachHoSo(@RequestBody EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO);
        ResponseModel responseModel = contractService.list(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "cancel",  method = RequestMethod.POST)
    public ResponseEntity<?> getHuyDopDong(@RequestBody EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO);
        ResponseModel responseModel = contractService.cancel(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "status",  method = RequestMethod.POST)
    public ResponseEntity<?> getTTinTThaiHoSo(@RequestBody EsbRequestDTO esbRequestDTO) {
        log.info(esbRequestDTO);
        ResponseModel responseModel = contractService.status(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
