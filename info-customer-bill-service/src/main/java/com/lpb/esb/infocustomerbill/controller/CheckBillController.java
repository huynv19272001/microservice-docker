package com.lpb.esb.infocustomerbill.controller;

import com.lpb.esb.infocustomerbill.service.CheckBillService;
import com.lpb.esb.service.common.model.request.infocustomerbill.DataQueryDTO;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("deprecation")
@RestController
@RequestMapping(value = "/api/v1/info", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Log4j2
public class CheckBillController {
    @Autowired
    CheckBillService checkBillService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/check-bill")
    public ResponseEntity<?> checkBill(@RequestBody DataQueryDTO Req_DTO) {
        try {
            ResponseModel responseModel = checkBillService.excuteCheckBill(Req_DTO);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        }
    }
}
