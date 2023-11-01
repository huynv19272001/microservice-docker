package com.lpb.esb.service.pti.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.pti.exception.validation.FieldValidator;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;
import com.lpb.esb.service.pti.model.pti.KhoiDong;
import com.lpb.esb.service.pti.model.pti.PtiRequest;
import com.lpb.esb.service.pti.model.pti.privatehouse.*;
import com.lpb.esb.service.pti.model.pti.privatehouse.nhapdon.NhapDon;
import com.lpb.esb.service.pti.model.pti.privatehouse.tinhphi.TinhPhi;
import com.lpb.esb.service.pti.service.PrivateHouseService;
import com.lpb.esb.service.pti.util.PtiUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PrivateHouseServiceImpl implements PrivateHouseService {
    @Autowired
    PtiUtils ptiUtils;
    @Autowired
    FieldValidator fieldValidator;

    @SneakyThrows
    @Override
    public ResponseModel khoiDong(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        KhoiDong khoiDong = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), KhoiDong.class);
        fieldValidator.validateFields(khoiDong);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(khoiDong))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel hoiSoTienBaoHiem(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        HoiSoTienBaoHiem hoiSoTienBaoHiem = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), HoiSoTienBaoHiem.class);
        fieldValidator.validateFields(hoiSoTienBaoHiem);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(hoiSoTienBaoHiem))
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel tinhThoiHanVay(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        TinhThoiHanVay tinhThoiHanVay = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), TinhThoiHanVay.class);
        fieldValidator.validateFields(tinhThoiHanVay);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(tinhThoiHanVay))
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel layMucKhauTru(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        LayMucKhauTru layMucKhauTru = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), LayMucKhauTru.class);
        fieldValidator.validateFields(layMucKhauTru);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(layMucKhauTru))
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel layDanhSachDoiTuongBaoHiem(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        LayDanhSachDoiTuongBaoHiem layDanhSachDoiTuongBaoHiem = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), LayDanhSachDoiTuongBaoHiem.class);
        fieldValidator.validateFields(layDanhSachDoiTuongBaoHiem);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(layDanhSachDoiTuongBaoHiem))
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel layDanhSachChuongTrinhBaoHiem(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        LayDanhSachChuongTrinhBaoHiem layDanhSachChuongTrinhBaoHiem = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), LayDanhSachChuongTrinhBaoHiem.class);
        fieldValidator.validateFields(layDanhSachChuongTrinhBaoHiem);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(layDanhSachChuongTrinhBaoHiem))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel layDanhSachLoaiHinhNghiepVuBaoHiem(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        LayDanhSachLoaiHinhNghiepVuBaoHiem layDanhSachLoaiHinhNghiepVuBaoHiem = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), LayDanhSachLoaiHinhNghiepVuBaoHiem.class);
        fieldValidator.validateFields(layDanhSachLoaiHinhNghiepVuBaoHiem);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(layDanhSachLoaiHinhNghiepVuBaoHiem))
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel layDanhSachQuyTacBaoHiem(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        LayDanhSachQuyTacBaoHiem layDanhSachQuyTacBaoHiem = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), LayDanhSachQuyTacBaoHiem.class);
        fieldValidator.validateFields(layDanhSachQuyTacBaoHiem);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(layDanhSachQuyTacBaoHiem))
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel layDanhSachTinhThanh(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        LayDanhSachTinhThanh layDanhSachTinhThanh = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), LayDanhSachTinhThanh.class);
        fieldValidator.validateFields(layDanhSachTinhThanh);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(layDanhSachTinhThanh))
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel layDanhSachQuanHuyen(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        LayDanhSachQuanHuyen layDanhSachQuanHuyen = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), LayDanhSachQuanHuyen.class);
        fieldValidator.validateFields(layDanhSachQuanHuyen);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(layDanhSachQuanHuyen))
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel tinhPhi(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        TinhPhi tinhPhi = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), TinhPhi.class);
        fieldValidator.validateFields(tinhPhi);

        PtiRequest request = PtiRequest.builder()
            .cot(tinhPhi.getCot())
            .data(objectMapper.writeValueAsString(tinhPhi.getData()))
            .encrypt(tinhPhi.getEncrypt())
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel layTyLePhiMacDinhVaNamSuDung(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        LayTyLePhiMacDinhVaNamSuDung layTyLePhiMacDinhVaNamSuDung = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), LayTyLePhiMacDinhVaNamSuDung.class);
        fieldValidator.validateFields(layTyLePhiMacDinhVaNamSuDung);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(layTyLePhiMacDinhVaNamSuDung))
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel nhapDon(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        NhapDon nhapDon = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), NhapDon.class);
        fieldValidator.validateFields(nhapDon);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(nhapDon.getData()))
            .dsTra(objectMapper.writeValueAsString(nhapDon.getDsTra()))
            .encrypt(nhapDon.getEncrypt())
            .build();
        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }
}
