package com.lpb.esb.service.pti.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.pti.exception.validation.FieldValidator;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;
import com.lpb.esb.service.pti.model.pti.KhoiDong;
import com.lpb.esb.service.pti.model.pti.PtiRequest;
import com.lpb.esb.service.pti.model.pti.creditbaoan.ChiTiet;
import com.lpb.esb.service.pti.model.pti.creditbaoan.ChiaKyPhi;
import com.lpb.esb.service.pti.model.pti.creditbaoan.TinhPhi;
import com.lpb.esb.service.pti.model.pti.creditbaoan.catkyphi.CatKyPhi;
import com.lpb.esb.service.pti.model.pti.creditbaoan.hinhthucthamgia.HinhThucThamGia;
import com.lpb.esb.service.pti.model.pti.creditbaoan.nhapdon.NhapDon;
import com.lpb.esb.service.pti.model.pti.creditbaoan.tinhduno.TinhDuNo;
import com.lpb.esb.service.pti.service.CreditBaoAnService;
import com.lpb.esb.service.pti.util.PtiUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CreditBaoAnServiceImpl implements CreditBaoAnService {
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
    public ResponseModel tinhPhi(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        TinhPhi tinhPhi = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), TinhPhi.class);
        fieldValidator.validateFields(tinhPhi);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(tinhPhi))
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
    public ResponseModel hinhThucThamGia(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        HinhThucThamGia hinhThucThamGia = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), HinhThucThamGia.class);
        fieldValidator.validateFields(hinhThucThamGia);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(hinhThucThamGia.getData()))
            .encrypt(hinhThucThamGia.getEncrypt())
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel catKyPhi(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        CatKyPhi catKyPhi = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), CatKyPhi.class);
        fieldValidator.validateFields(catKyPhi);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(catKyPhi.getData()))
            .dsTra(objectMapper.writeValueAsString(catKyPhi.getDsTra()))
            .cot(catKyPhi.getCot())
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel chiTiet(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        ChiTiet chiTiet = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), ChiTiet.class);
        fieldValidator.validateFields(chiTiet);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(chiTiet))
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }

    @SneakyThrows
    @Override
    public ResponseModel tinhDuNo(EsbRequestDTO data) {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("msgId: " + data.getHeader().getMsgId() + " | LPB Req: " + objectMapper.writeValueAsString(data));

        TinhDuNo tinhDuNo = objectMapper.readValue(objectMapper.writeValueAsString(data.getBody().getData()), TinhDuNo.class);
        fieldValidator.validateFields(tinhDuNo);

        PtiRequest request = PtiRequest.builder()
            .data(objectMapper.writeValueAsString(tinhDuNo.getData()))
            .cot(tinhDuNo.getCot())
            .build();

        ResponseModel responseModel = ptiUtils.sendPtiRequest(data, request);
        return responseModel;
    }
}
