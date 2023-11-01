package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.service.SettleBillService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/settle-bill", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SettleBillController {
    @Autowired
    SettleBillService settleBillService;

    @PostMapping(value = "init-settle-bill")
    public ResponseEntity<?> initSettleBill(@RequestBody BaseRequestDTO requestDTO) {
        try {
            ResponseModel responseModel = settleBillService.initSettleBill(requestDTO);

            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.info(requestDTO.getHeader().getMsgId() + " Exception: " + e);
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(ErrorMessage.FAIL.label)
                .errorDesc(ErrorMessage.FAIL.description)
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @PostMapping(value = "get-settle-bill")
    public ResponseEntity<?> getSettleBill(@RequestBody BaseRequestDTO requestDTO) {
        try {
            ResponseModel responseModel = settleBillService.getSettleBill(requestDTO);

            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.info(requestDTO.getHeader().getMsgId() + " Exception: " + e);
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(ErrorMessage.FAIL.label)
                .errorDesc(ErrorMessage.FAIL.description)
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @PostMapping(value = "update-settle-bill")
    public ResponseEntity<?> updateSettleBill(@RequestBody BaseRequestDTO requestDTO) {
        try {
            ResponseModel responseModel = settleBillService.updateSettleBill(requestDTO);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.info(requestDTO.getHeader().getMsgId() + " Exception: " + e);
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(ErrorMessage.FAIL.label)
                .errorDesc(ErrorMessage.FAIL.description)
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @PostMapping(value = "billing_log")
    public ResponseEntity<?> billingLog(@RequestBody BaseRequestDTO requestDTO) {
        try {
            ResponseModel responseModel = settleBillService.billingLog(requestDTO);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.info(requestDTO.getHeader().getMsgId() + " Exception: " + e);
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(ErrorMessage.FAIL.label)
                .errorDesc(ErrorMessage.FAIL.description)
                .build();
            ResponseModel responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
