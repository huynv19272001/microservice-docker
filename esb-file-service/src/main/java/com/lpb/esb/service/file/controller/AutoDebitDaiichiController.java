package com.lpb.esb.service.file.controller;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.request.HeaderInfoDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.file.config.AutoDebitDaiichiConfig;
import com.lpb.esb.service.file.process.FileProcess;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/file")
@Log4j2
public class AutoDebitDaiichiController {
    @Autowired
    FileProcess fileProcess;

    @Autowired
    AutoDebitDaiichiConfig autoDebitDaiichiConfig;

    @PostMapping(value = "/uploadGiaoDich", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity uploadGiaoDich(@RequestPart("file") MultipartFile file) {
        try {
            HeaderInfoDTO header = HeaderInfoDTO.builder()
                .serviceId(autoDebitDaiichiConfig.getServiceId())
                .productCode(autoDebitDaiichiConfig.getProductCodeGiaoDich())
                .operation(autoDebitDaiichiConfig.getOperation())
                .build();
            BaseRequestDTO request = BaseRequestDTO.builder()
                .header(header)
                .build();

            ResponseModel responseModel = fileProcess.uploadFile(file, request);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(ErrorMessage.FAIL.label)
                .errorDesc(ErrorMessage.FAIL.description)
                .build();
            ResponseModel responseModel = ResponseModel.builder().resCode(lpbResCode).build();
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/uploadDangKi", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity uploadDangKi(@RequestPart("file") MultipartFile file) {
        try {
            HeaderInfoDTO header = HeaderInfoDTO.builder()
                .serviceId(autoDebitDaiichiConfig.getServiceId())
                .productCode(autoDebitDaiichiConfig.getProductCodeDangKy())
                .operation(autoDebitDaiichiConfig.getOperation())
                .build();
            BaseRequestDTO request = BaseRequestDTO.builder()
                .header(header)
                .build();

            ResponseModel responseModel = fileProcess.uploadFile(file, request);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(ErrorMessage.FAIL.label)
                .errorDesc(ErrorMessage.FAIL.description)
                .build();
            ResponseModel responseModel = ResponseModel.builder().resCode(lpbResCode).build();
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
    }
}
