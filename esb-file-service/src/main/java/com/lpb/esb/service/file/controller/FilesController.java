package com.lpb.esb.service.file.controller;

import com.jcraft.jsch.JSchException;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.file.dto.FileInfo;
import com.lpb.esb.service.file.service.FileTransferService;
import com.lpb.esb.service.file.service.FilesStorageService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/v1/file")
@Log4j2
public class FilesController {

    @Autowired
    FilesStorageService storageService;

    @Autowired
    FileTransferService fileTransferService;


    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            ResponseModel responseModel = fileTransferService.uploadFile(file);
            if (responseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                //save file trên server
                storageService.save(file);
                return new ResponseEntity<>(responseModel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (CommonException e) {
            return new ResponseEntity<>(e.getResponseModel(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(ErrorMessage.FAIL.label)
                .errorDesc(ErrorMessage.FAIL.description)
                .build();
            ResponseModel responseModel = ResponseModel.builder().resCode(lpbResCode).build();
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/upload-multiple-files", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel<List<String>>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        ResponseModel<List<String>> responseModel = ResponseModel.<List<String>>builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc(EsbErrorCode.SUCCESS.toString())
            .build();
        List<String> listMessage = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                storageService.save(file);
                String message = "Uploaded the file successfully: " + file.getOriginalFilename();
                listMessage.add(message);
            } catch (Exception e) {
                String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                listMessage.add(message);
            }
        }
        responseModel.setData(listMessage);
        return ResponseEntity.ok(responseModel);
    }


    @GetMapping(value = "/get-list-files", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel<List<FileInfo>>> getListFiles() throws IOException {
        ResponseModel<List<FileInfo>> responseModel = ResponseModel.<List<FileInfo>>builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc(EsbErrorCode.SUCCESS.toString())
            .build();
        try {
            FTPFile[] fileArray = fileTransferService.getListFile();
            if (fileArray.length == 0) {
                responseModel.setData(null);
                responseModel.setErrorCode(EsbErrorCode.FAIL.label);
                responseModel.setErrorDesc(EsbErrorCode.FAIL.toString());
                return ResponseEntity.ok(responseModel);
            } else {
                List<FileInfo> fileInfos = new ArrayList<>();
                for (FTPFile file : fileArray) {
                    if (file.isFile()) {
                        FileInfo fileInfo = new FileInfo();
                        String url = MvcUriComponentsBuilder
                            .fromMethodName(FilesController.class, "getFile", file.getName().toString()).build().toString();
                        fileInfo.setUrl(url);
                        fileInfo.setName(file.getName());
                        fileInfos.add(fileInfo);
                    }
                }
                responseModel.setData(fileInfos);
                return ResponseEntity.ok(responseModel);
            }
        } catch (Exception e) {
            responseModel.setData(null);
            responseModel.setErrorCode(EsbErrorCode.FAIL.label);
            responseModel.setErrorDesc(EsbErrorCode.FAIL.toString());
            return ResponseEntity.ok(responseModel);
        }
    }

    @GetMapping(value = "/check-file/{filename:.+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> checkFile(@PathVariable String filename) throws IOException {
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .data(null)
            .build();
        try {
            Boolean checkFile = fileTransferService.checkFile(filename);
            if (checkFile == true) {
                lpbResCode.setErrorCode(EsbErrorCode.SUCCESS.label);
                lpbResCode.setErrorDesc("File exists");
                responseModel.setResCode(lpbResCode);
                return ResponseEntity.ok(responseModel);
            } else {
                lpbResCode.setErrorCode(EsbErrorCode.FAIL.label);
                lpbResCode.setErrorDesc("File does not exists");
                responseModel.setResCode(lpbResCode);
                return ResponseEntity.ok(responseModel);
            }
        } catch (Exception e) {
            lpbResCode.setErrorCode(EsbErrorCode.FAIL.label);
            lpbResCode.setErrorDesc("File does not exists");
            responseModel.setResCode(lpbResCode);
            return ResponseEntity.ok(responseModel);
        }
    }


    @GetMapping("/download-files/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        ResponseModel responseModel = ResponseModel.<List<String>>builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc(EsbErrorCode.SUCCESS.toString())
            .build();
        try {
            Boolean checkDownload = fileTransferService.downloadFile(filename);
            if (checkDownload == true) {
                Resource file = storageService.load(filename);
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
            } else {
                responseModel.setData("File does not exists");
                responseModel.setErrorCode(EsbErrorCode.FAIL.label);
                responseModel.setErrorDesc(EsbErrorCode.FAIL.toString());
                return ResponseEntity.ok(responseModel);
            }
        } catch (Exception e) {
            responseModel.setErrorCode(EsbErrorCode.FAIL.label);
            responseModel.setErrorDesc(EsbErrorCode.FAIL.toString());
            responseModel.setData("Other error");
            return ResponseEntity.ok(responseModel);
        }
    }

    @GetMapping("/download-files-sftp/{filename:.+}")
    public ResponseEntity<ResponseModel<String>> getFileSftp(@PathVariable String filename) throws JSchException, IOException {
        ResponseModel responseModel = ResponseModel.<List<String>>builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc(EsbErrorCode.SUCCESS.toString())
            .build();
        try {
            Boolean checkDownload = fileTransferService.downloadFile(filename);
            if (checkDownload == true) {
                responseModel.setData("Download Success");
                return ResponseEntity.ok(responseModel);
            } else {
                responseModel.setData("File does not exists");
                responseModel.setErrorCode(EsbErrorCode.FAIL.label);
                responseModel.setErrorDesc(EsbErrorCode.FAIL.toString());
                return ResponseEntity.ok(responseModel);
            }
        } catch (Exception e) {
            responseModel.setErrorCode(EsbErrorCode.FAIL.label);
            responseModel.setErrorDesc(EsbErrorCode.FAIL.toString());
            responseModel.setData("Other error");
            return ResponseEntity.ok(responseModel);
        }
    }
}
