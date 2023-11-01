package com.lpb.esb.service.lv24.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.lv24.model.EsbRequestDTO;
import com.lpb.esb.service.lv24.service.DangKyBdsdQuaNotificationService;
import com.lpb.esb.service.lv24.service.DangKyBdsdQuaNotificationServiceV0;
import com.lpb.esb.service.lv24.service.QueryHrAndCardCifService;
import com.lpb.esb.service.lv24.util.RequestScopedInfo;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class Lv24Controller {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RequestScopedInfo requestScopedInfo;
    //    @Autowired
//    DangKyBdsdQuaNotificationServiceV0 dangKyBdsdQuaNotificationServiceV0;
//    @Autowired
//    QueryHrAndCardCifService queryHrAndCardCifService;
    @Autowired
    DangKyBdsdQuaNotificationService dangKyBdsdQuaNotificationService;

//    @RequestMapping(value = "/dang-ky-bdsd-qua-notification-v0", method = RequestMethod.POST)
//    public ResponseEntity<?> dangKyBdsdQuaNotificationV0(@RequestBody EsbRequestDTO request) {
//        String msgId = request.getHeader().getMsgId();
//        log.info("msgId: " + msgId + " | REQ: " + request);
//        requestScopedInfo.setMsgId(msgId);
//        ResponseModel responseModel = dangKyBdsdQuaNotificationServiceV0.sendMessages(request);
//        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
//            return ResponseEntity.ok(responseModel);
//        } else {
//            return ResponseEntity.badRequest().body(responseModel);
//        }
//    }

    @SneakyThrows
    @RequestMapping(value = "/dang-ky-bdsd-qua-notification", method = RequestMethod.POST)
    public ResponseEntity<?> dangKyBdsdQuaNotification(@RequestBody String request) {
        log.info("RAW REQ: " + request);
        EsbRequestDTO esbRequestDTO = objectMapper.readValue(request, EsbRequestDTO.class);
        String msgId = esbRequestDTO.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = dangKyBdsdQuaNotificationService.sendMessages(esbRequestDTO);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

//    @RequestMapping(value = "/query-hr-and-card-cif", method = RequestMethod.POST)
//    public ResponseEntity<?> queryHrAndCardCif(@RequestBody EsbRequestDTO request) {
//        String msgId = request.getHeader().getMsgId();
//        log.info("msgId: " + msgId + " | REQ: " + request);
//        requestScopedInfo.setMsgId(msgId);
//        ResponseModel responseModel = queryHrAndCardCifService.callTwoApis(request);
//        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
//            return ResponseEntity.ok(responseModel);
//        } else {
//            return ResponseEntity.badRequest().body(responseModel);
//        }
//    }
}
