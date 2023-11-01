package com.lpb.esb.service.file.controller;

import com.lpb.esb.service.common.model.request.BaseRequestDTO;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.file.process.FileProcess;
import com.lpb.esb.service.file.service.FilesStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/file")
@Log4j2
public class FilesV2Controller {
    @Autowired
    FilesStorageService storageService;

    @Autowired
    FileProcess fileProcess;


    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity uploadFile(@RequestPart("file") MultipartFile file, @RequestPart("request") BaseRequestDTO baseRequestDTO) {
        try {
            ResponseModel responseModel = fileProcess.uploadFile(file, baseRequestDTO);
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

    @PostMapping(value = "/get-list-file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getListFile(@RequestBody BaseRequestDTO baseRequestDTO) {
        try {
            ResponseModel responseModel = fileProcess.getListFile(baseRequestDTO);
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

    @PostMapping(value = "/check-file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity checkFile(@RequestBody BaseRequestDTO baseRequestDTO) {
        try {
            ResponseModel responseModel = fileProcess.checkFile(baseRequestDTO);
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

    @PostMapping(value = "/download-file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity downloadFile(@RequestBody BaseRequestDTO baseRequestDTO) {
        try {
            ResponseModel responseModel = fileProcess.downloadFile(baseRequestDTO);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                Resource file = storageService.load(baseRequestDTO.getHeader().getDestination());
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
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
