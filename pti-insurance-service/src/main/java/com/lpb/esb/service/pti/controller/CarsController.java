package com.lpb.esb.service.pti.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;
import com.lpb.esb.service.pti.service.CarService;
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
@RequestMapping(value = "/api/v1/cars", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CarsController {
    @Autowired
    CarService carService;
    @Autowired
    RequestScopedInfo requestScopedInfo;

    @RequestMapping(value = "/khoi-dong", method = RequestMethod.POST)
    public ResponseEntity<?> category(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = carService.khoiDong(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/hieu-xe", method = RequestMethod.POST)
    public ResponseEntity<?> hieuxe(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = carService.hieuXe(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/loai-xe", method = RequestMethod.POST)
    public ResponseEntity<?> loaixe(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = carService.loaiXe(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/chi-tiet-loai-xe", method = RequestMethod.POST)
    public ResponseEntity<?> chitietloaixe(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = carService.chiTietLoaiXe(request);
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
        ResponseModel responseModel = carService.tinhPhi(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/chia-ky-phi", method = RequestMethod.POST)
    public ResponseEntity<?> chiakyphi(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = carService.chiaKyPhi(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/duyet-don", method = RequestMethod.POST)
    public ResponseEntity<?> duyetdon(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = carService.duyetDon(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/in-don", method = RequestMethod.POST)
    public ResponseEntity<?> inDon(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = carService.inDon(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/lay-ma", method = RequestMethod.POST)
    public ResponseEntity<?> layMa(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = carService.layMa(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/cap-don", method = RequestMethod.POST)
    public ResponseEntity<?> capDon(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = carService.capDon(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
