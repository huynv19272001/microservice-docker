package com.lpd.esb.service.mobifone.exception;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpd.esb.service.mobifone.util.RequestScopedInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @Autowired
    RequestScopedInfo requestScopedInfo;

    private LpbResCode lpbResCode;
    private ResponseModel responseModel;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        String exStackTrace = ExceptionUtils.getStackTrace(ex);
        log.error("msgId: " + requestScopedInfo.getMsgId() + " | Exception stack: " + exStackTrace);

        lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.FAIL.label)
            .errorDesc(exStackTrace)
            .build();

        responseModel = ResponseModel.builder().resCode(lpbResCode).build();
        return ResponseEntity.badRequest().body(responseModel);
    }
}
