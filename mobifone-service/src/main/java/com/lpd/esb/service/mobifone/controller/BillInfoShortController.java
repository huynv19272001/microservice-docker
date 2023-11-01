package com.lpd.esb.service.mobifone.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpd.esb.service.mobifone.model.Request;
import com.lpd.esb.service.mobifone.service.BillInfoShortService;
import com.lpd.esb.service.mobifone.util.RequestScopedInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/billinfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Log4j2
public class BillInfoShortController {
    @Autowired
    BillInfoShortService billInfoShortService;
    @Autowired
    RequestScopedInfo requestScopedInfo;

    @RequestMapping(value = "/short", method = RequestMethod.POST)
    public ResponseEntity<?> billInfoShort(@RequestBody Request request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = billInfoShortService.getInfoShort(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
