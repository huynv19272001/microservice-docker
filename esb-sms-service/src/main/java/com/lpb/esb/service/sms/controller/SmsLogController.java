package com.lpb.esb.service.sms.controller;

import com.lpb.esb.service.common.model.response.ListModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.sms.model.request.SmsLogRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by tudv1 on 2021-08-03
 */
@RestController
@RequestMapping("sms/log")
public class SmsLogController {

    @RequestMapping(value = "save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel> saveLog(
        HttpServletRequest request
        , @RequestBody List<SmsLogRequest> listSms
    ) {
        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Save success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .data(new ListModel<>(listSms))
            .build();
        return ResponseEntity.ok(responseModel);
    }
}
