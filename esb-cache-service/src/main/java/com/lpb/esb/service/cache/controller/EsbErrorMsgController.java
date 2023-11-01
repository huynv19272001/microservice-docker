package com.lpb.esb.service.cache.controller;

import com.lpb.esb.service.cache.model.oracle.EsbErrorMessageEntity;
import com.lpb.esb.service.cache.service.sync.ErrorMessageService;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbControllerResponseCode;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2021-08-26
 */
@RestController
@RequestMapping(value = "esb/error-message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EsbErrorMsgController {
    @Autowired
    ErrorMessageService errorMessageService;

    @RequestMapping(value = "{codeNumber}", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> getErrorMessage(@PathVariable int codeNumber) {
        EsbErrorMessageEntity esbErrorMessageEntity = errorMessageService.getErrorMessageByCodeNumber(codeNumber);
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorCode(EsbControllerResponseCode.SUCCESS.label)
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .errorDesc("Get error message success for code " + codeNumber)
            .data(esbErrorMessageEntity)
            .build();

        return ResponseEntity.ok(responseModel);
    }
}
