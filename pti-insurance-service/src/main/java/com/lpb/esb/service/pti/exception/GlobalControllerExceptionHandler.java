package com.lpb.esb.service.pti.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.pti.model.pti.PtiResponse;
import com.lpb.esb.service.pti.util.RequestScopedInfo;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@Log4j2
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @Autowired
    RequestScopedInfo requestScopedInfo;

    private LpbResCode lpbResCode;
    private ResponseModel responseModel;

    @ExceptionHandler(FieldValidationException.class)
    public ResponseEntity<?> handleFieldValidationException(FieldValidationException ex) {
        log.error("msgId: " + requestScopedInfo.getMsgId() + " | FieldValidationException: " + ex);
        ex.printStackTrace();

        lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.FAIL.label)
            .errorDesc(ex.getErrorList().toString())
            .build();
        responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .build();
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    @SneakyThrows
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex) {
        log.error("msgId: " + requestScopedInfo.getMsgId() + " | HttpClientErrorException: " + ex);
        ex.printStackTrace();

        ObjectMapper objectMapper = new ObjectMapper();
        PtiResponse ptiResponse = objectMapper.readValue(ex.getResponseBodyAsString(), PtiResponse.class);
        lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.FAIL.label)
            .errorDesc("Service Failure")
            .refCode(ptiResponse.getCode())
            .refDesc(ptiResponse.getMessage())
            .build();
        responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .build();
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("msgId: " + requestScopedInfo.getMsgId() + " | Exception: " + ex);
        ex.printStackTrace();

        lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.FAIL.label)
            .errorDesc(ex.toString())
            .build();
        responseModel = ResponseModel.builder().resCode(lpbResCode).build();
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }
}
