package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.config.service.RevertBillService;
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
@RequestMapping(value = "/api/v1/revert-bill", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RevertBillController {
    @Autowired
    RevertBillService revertBillService;

    @PostMapping(value = "init-revert-bill")
    public ResponseEntity<?> initRevertBill(@RequestBody BaseRequestDTO requestDTO) {
        try {
            ResponseModel responseModel = revertBillService.initRevertBill(requestDTO);

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

    @PostMapping(value = "get-revert-bill")
    public ResponseEntity<?> getRevertBill(@RequestBody BaseRequestDTO requestDTO) {
        try {
            ResponseModel responseModel = revertBillService.getRevertBill(requestDTO);

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
