package com.lpb.esb.service.pti.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;
import com.lpb.esb.service.pti.service.PrivateHouseService;
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
@RequestMapping(value = "/api/v1/private-house", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PrivateHouseController {
    @Autowired
    PrivateHouseService privateHouseService;
    @Autowired
    RequestScopedInfo requestScopedInfo;

    @RequestMapping(value = "/khoi-dong", method = RequestMethod.POST)
    public ResponseEntity<?> khoiDong(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.khoiDong(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/hoi-so-tien-bao-hiem", method = RequestMethod.POST)
    public ResponseEntity<?> hoiSoTienBaoHiem(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.hoiSoTienBaoHiem(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/tinh-thoi-han-vay", method = RequestMethod.POST)
    public ResponseEntity<?> tinhThoiHanVay(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.tinhThoiHanVay(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/lay-muc-khau-tru", method = RequestMethod.POST)
    public ResponseEntity<?> layMucKhauTru(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.layMucKhauTru(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/lay-danh-sach-doi-tuong-bao-hiem", method = RequestMethod.POST)
    public ResponseEntity<?> layDanhSachDoiTuongBaoHiem(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.layDanhSachDoiTuongBaoHiem(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/lay-danh-sach-chuong-trinh-bao-hiem", method = RequestMethod.POST)
    public ResponseEntity<?> layDanhSachChuongTrinhBaoHiem(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.layDanhSachChuongTrinhBaoHiem(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/lay-danh-sach-loai-hinh-nghiep-vu-bao-hiem", method = RequestMethod.POST)
    public ResponseEntity<?> layDanhSachLoaiHinhNghiepVuBaoHiem(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.layDanhSachLoaiHinhNghiepVuBaoHiem(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/lay-danh-sach-quy-tac-bao-hiem", method = RequestMethod.POST)
    public ResponseEntity<?> layDanhSachQuyTacBaoHiem(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.layDanhSachQuyTacBaoHiem(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/lay-danh-sach-tinh-thanh", method = RequestMethod.POST)
    public ResponseEntity<?> layDanhSachTinhThanh(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.layDanhSachTinhThanh(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/lay-danh-sach-quan-huyen", method = RequestMethod.POST)
    public ResponseEntity<?> layDanhSachQuanHuyen(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.layDanhSachQuanHuyen(request);
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
        ResponseModel responseModel = privateHouseService.tinhPhi(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "/lay-ty-le-phi-mac-dinh-va-nam-su-dung", method = RequestMethod.POST)
    public ResponseEntity<?> layTyLePhiMacDinhVaNamSuDung(@RequestBody EsbRequestDTO request) {
        String msgId = request.getHeader().getMsgId();
        log.info("msgId: " + msgId + " | REQ: " + request);
        requestScopedInfo.setMsgId(msgId);
        ResponseModel responseModel = privateHouseService.layTyLePhiMacDinhVaNamSuDung(request);
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
        ResponseModel responseModel = privateHouseService.nhapDon(request);
        if (EsbErrorCode.SUCCESS.label.equals(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok(responseModel);
        } else {
            return ResponseEntity.badRequest().body(responseModel);
        }
    }
}
