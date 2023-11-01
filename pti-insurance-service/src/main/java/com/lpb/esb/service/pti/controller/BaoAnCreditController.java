package com.lpb.esb.service.pti.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;
import com.lpb.esb.service.pti.service.CreditBaoAnService;
import com.lpb.esb.service.pti.util.RequestScopedInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping(value = "/api/v1/credit-bao-an", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BaoAnCreditController {
    @Autowired
    CreditBaoAnService creditBaoAnService;
    @Autowired
    RequestScopedInfo requestScopedInfo;

    @RequestMapping(value = "/khoi-dong", method = RequestMethod.POST)
    public ResponseEntity<?> khoiDong(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = creditBaoAnService.khoiDong(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/tinh-phi", method = RequestMethod.POST)
    public ResponseEntity<?> tinhPhi(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = creditBaoAnService.tinhPhi(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/nhap-don", method = RequestMethod.POST)
    public ResponseEntity<?> nhapDon(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = creditBaoAnService.nhapDon(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/chia-ky-phi", method = RequestMethod.POST)
    public ResponseEntity<?> chiaKyPhi(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = creditBaoAnService.chiaKyPhi(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/hinh-thuc-tham-gia", method = RequestMethod.POST)
    public ResponseEntity<?> hinhThucThamGia(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = creditBaoAnService.hinhThucThamGia(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/cat-ky-phi", method = RequestMethod.POST)
    public ResponseEntity<?> catKyPhi(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = creditBaoAnService.catKyPhi(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/chi-tiet", method = RequestMethod.POST)
    public ResponseEntity<?> chiTiet(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = creditBaoAnService.chiTiet(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/tinh-du-no", method = RequestMethod.POST)
    public ResponseEntity<?> tinhDuNo(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = creditBaoAnService.tinhDuNo(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
