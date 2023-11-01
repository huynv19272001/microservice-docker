package com.lpd.esb.service.mobifone.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpd.esb.service.mobifone.configuration.RestTemplateConfig;
import com.lpd.esb.service.mobifone.model.Request;
import com.lpd.esb.service.mobifone.model.mobifone.BillInfoShortResponse;
import com.lpd.esb.service.mobifone.model.mobifone.MobifoneRequestBody;
import com.lpd.esb.service.mobifone.service.BillInfoShortService;
import com.lpd.esb.service.mobifone.service.LogInService;
import com.lpd.esb.service.mobifone.util.MobifoneUtility;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class BillInfoShortServiceImpl implements BillInfoShortService {
    @Autowired
    RestTemplateConfig restTemplate;
    @Autowired
    LogInService logInService;
    @Autowired
    MobifoneUtility mobifoneUtility;

    @SneakyThrows
    @Override
    public ResponseModel getInfoShort(Request request) {
        String msgId = request.getHeader().getMsgId();
        LpbResCode lpbResCode;
        ResponseModel responseModel;

        List<ServiceInfo> serviceInfos = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(),
            request.getHeader().getServiceId(), request.getHeader().getProductCode(), HAS_ROLE);

        ObjectMapper objectMapper = new ObjectMapper();

        //request
        log.info("msgId: " + msgId + " | LPB Request: " + objectMapper.writeValueAsString(request));
        ArrayList<MobifoneRequestBody> mobiBody = mobifoneUtility.buildMobiReqBody(request, serviceInfos);
        //61
        MobifoneRequestBody mobiReqBody = MobifoneRequestBody.builder()
            .fieldId("61")
            .value(request.getBody().getData().getPhoneNumber())
            .build();
        mobiBody.add(mobiReqBody);

        responseModel = logInService.logIn(request);
        if (responseModel.getResCode().getErrorCode().equals(EsbErrorCode.FAIL.label)) {
            return responseModel;
        }

        mobifoneUtility.sendMobiRequest(request, serviceInfos, mobiBody, logInService);

        String mobiResCode = mobifoneUtility.getMobiResCode();
        String mobiResMsg = mobifoneUtility.getMobiResMsg();

        if (mobiResCode.equals("00")) {
            lpbResCode = LpbResCode.builder().errorCode(EsbErrorCode.SUCCESS.label).errorDesc("Service Success").build();

            String[] mobiResponseData = mobiResMsg.split("\\|");
            BillInfoShortResponse billInfoShortResponse = BillInfoShortResponse.builder()
                .phoneNumber(mobiResponseData[0])
                .custCode(mobiResponseData[1])
                .debtStaCycle(mobiResponseData[2])
                .usageCharge(mobiResponseData[3])
                .payment(mobiResponseData[4])
                .lDebtRemain(mobiResponseData[5])
                .debtMonth(mobiResponseData[6])
                .centerCode(mobiResponseData[7])
                .redStatus(mobiResponseData[8])
                .build();

            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(billInfoShortResponse)
                .build();
        } else {
            lpbResCode = LpbResCode.builder()
                .errorCode(EsbErrorCode.FAIL.label)
                .errorDesc("Failure")
                .refCode(mobiResCode)
                .refDesc(mobiResMsg)
                .build();

            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .build();
        }
        log.info("msgId: " + msgId + " | LPB Response: " + objectMapper.writeValueAsString(responseModel));
        return responseModel;
    }
}
