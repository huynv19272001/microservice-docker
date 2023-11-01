package com.lpb.esb.service.file.exception;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseModel<String>> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        ResponseModel<String> responseModel = ResponseModel.<String>builder()
            .errorCode(EsbErrorCode.FAIL.label)
            .errorDesc(EsbErrorCode.FAIL.toString())
            .build();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseModel);
    }
}
