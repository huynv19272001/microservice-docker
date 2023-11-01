package com.lpb.esb.service.tct.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.tct.model.request.RequestData;
import com.lpb.esb.service.tct.service.TcnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2022-02-24
 */
@RestController
@RequestMapping(value = "thue-ca-nhan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ThueCaNhanController {
    @Autowired
    TcnService tcnService;

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> search(@RequestBody RequestData requestData) throws Exception {
        ResponseModel responseModel = tcnService.search(requestData);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
