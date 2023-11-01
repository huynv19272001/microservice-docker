package com.lpb.esb.service.pti.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.pti.exception.validation.FieldValidator;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;
import com.lpb.esb.service.pti.model.pti.PtiRequest;
import com.lpb.esb.service.pti.model.pti.car.*;
import com.lpb.esb.service.pti.model.pti.car.capdon.CapDon;
import com.lpb.esb.service.pti.service.CarService;
import com.lpb.esb.service.pti.util.PtiUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CarServiceImpl implements CarService {
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
    public ResponseModel hieuXe(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        HieuXe hieuXe = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), HieuXe.class);
        fieldValidator.validateFields(hieuXe);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(hieuXe))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel loaiXe(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        LoaiXe loaiXe = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), LoaiXe.class);
        fieldValidator.validateFields(loaiXe);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(loaiXe))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel chiTietLoaiXe(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        ChiTietLoaiXe chiTietLoaiXe = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), ChiTietLoaiXe.class);
        fieldValidator.validateFields(chiTietLoaiXe);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(chiTietLoaiXe))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel tinhPhi(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        com.lpb.esb.service.pti.model.pti.car.tinhphi.TinhPhi tinhPhi = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), com.lpb.esb.service.pti.model.pti.car.tinhphi.TinhPhi.class);
        fieldValidator.validateFields(tinhPhi);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(tinhPhi.getData()))
            .dsDk(objectMapper.writeValueAsString(tinhPhi.getDsDk()))
            .cot(tinhPhi.getCot())
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel chiaKyPhi(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        ChiaKyPhi chiaKyPhi = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), ChiaKyPhi.class);
        fieldValidator.validateFields(chiaKyPhi);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(chiaKyPhi))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel duyetDon(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        DuyetDon duyetDon = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), DuyetDon.class);
        fieldValidator.validateFields(duyetDon);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(duyetDon))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel inDon(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        InDon inDon = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), InDon.class);
        fieldValidator.validateFields(inDon);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(inDon))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel layMa(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        LayMa layMa = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), LayMa.class);
        fieldValidator.validateFields(layMa);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(layMa))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel capDon(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        CapDon capDon = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), CapDon.class);
        fieldValidator.validateFields(capDon);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(capDon.getData()))
            .dsDk(objectMapper.writeValueAsString(capDon.getDsDk()))
            .dsTra(objectMapper.writeValueAsString(capDon.getDsTra()))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }
}
