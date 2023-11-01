package com.lpd.esb.service.mobifone.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpd.esb.service.mobifone.configuration.RestTemplateConfig;
import com.lpd.esb.service.mobifone.model.Request;
import com.lpd.esb.service.mobifone.model.mobifone.BillPayCancelResponse;
import com.lpd.esb.service.mobifone.model.mobifone.BillPayPostpaidResponse;
import com.lpd.esb.service.mobifone.model.mobifone.BillPayPrepaidResponse;
import com.lpd.esb.service.mobifone.model.mobifone.MobifoneRequestBody;
import com.lpd.esb.service.mobifone.service.BillPayService;
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
public class BillPayServiceImpl implements BillPayService {
    @Autowired
    RestTemplateConfig restTemplate;
    @Autowired
    LogInService logInService;
    @Autowired
    MobifoneUtility mobifoneUtility;

    @SneakyThrows
    @Override
    public ResponseModel prepaid(Request request) {
        String msgId = request.getHeader().getMsgId();
        LpbResCode lpbResCode;
        ResponseModel responseModel;

        List<ServiceInfo> serviceInfos = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(),
            request.getHeader().getServiceId(), request.getHeader().getProductCode(), HAS_ROLE);

        ObjectMapper objectMapper = new ObjectMapper();

        log.info("msgId: " + msgId + " | LPB Request: " + objectMapper.writeValueAsString(request));
        //request
        ArrayList<MobifoneRequestBody> mobiBody = mobifoneUtility.buildMobiReqBody(request, serviceInfos);
        mobiBody = mobifoneUtility.buildMobiReqBodyBillPay(request, mobiBody);

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
            BillPayPrepaidResponse billPayPrepaidResponse = BillPayPrepaidResponse.builder()
                .phoneNumber(mobiResponseData[0])
                .amount(mobiResponseData[1])
                .result(mobiResponseData[2])
                .build();

            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(billPayPrepaidResponse)
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

    @SneakyThrows
    @Override
    public ResponseModel postpaid(Request request) {
        String msgId = request.getHeader().getMsgId();
        LpbResCode lpbResCode;
        ResponseModel responseModel;

        List<ServiceInfo> serviceInfos = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(),
            request.getHeader().getServiceId(), request.getHeader().getProductCode(), HAS_ROLE);

        ObjectMapper objectMapper = new ObjectMapper();

        log.info("msgId: " + msgId + " | LPB Request: " + objectMapper.writeValueAsString(request));
        //request
        ArrayList<MobifoneRequestBody> mobiBody = mobifoneUtility.buildMobiReqBody(request, serviceInfos);
        mobiBody = mobifoneUtility.buildMobiReqBodyBillPay(request, mobiBody);

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
            BillPayPostpaidResponse billPayPostpaidResponse = BillPayPostpaidResponse.builder()
                .phoneNumber(mobiResponseData[0])
                .custCode(mobiResponseData[1])
                .settlementAmount(mobiResponseData[2])
                .debtStaCycle(mobiResponseData[3])
                .usageCharge(mobiResponseData[4])
                .payment(mobiResponseData[5])
                .paymentId(mobiResponseData[6])
                .paymentStartDate(mobiResponseData[7])
                .paymentEndDate(mobiResponseData[8])
                .centerCode(mobiResponseData[9])
                .billCycleID(mobiResponseData[10])
                .build();

            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(billPayPostpaidResponse)
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

    @SneakyThrows
    @Override
    public ResponseModel cancel(Request request) {
        String msgId = request.getHeader().getMsgId();
        LpbResCode lpbResCode;
        ResponseModel responseModel;

        List<ServiceInfo> serviceInfos = ConnectInfoService.getServiceInfo(restTemplate.getRestTemplateLB(),
            request.getHeader().getServiceId(), request.getHeader().getProductCode(), HAS_ROLE);

        ObjectMapper objectMapper = new ObjectMapper();

        log.info("msgId: " + msgId + " | LPB Request: " + objectMapper.writeValueAsString(request));
        //request
        ArrayList<MobifoneRequestBody> mobiBody = mobifoneUtility.buildMobiReqBody(request, serviceInfos);
        //12
        MobifoneRequestBody mobiReqBody = MobifoneRequestBody.builder()
            .fieldId("12")
            .value("970449")
            .build();
        mobiBody.add(mobiReqBody);
        //61
        mobiReqBody = MobifoneRequestBody.builder()
            .fieldId("61")
            .value(request.getBody().getData().getPhoneNumber() + "|" + request.getBody().getData().getCustCode())
            .build();
        mobiBody.add(mobiReqBody);
        //73
        mobiReqBody = MobifoneRequestBody.builder()
            .fieldId("73")
            .value(request.getBody().getData().getSettlementDate())
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
            BillPayCancelResponse billPayCancelResponse = BillPayCancelResponse.builder()
                .phoneNumber(mobiResponseData[0])
                .abortAmount(mobiResponseData[1])
                .debtRemain(mobiResponseData[2])
                .centerCode(mobiResponseData[3])
                .build();

            responseModel = ResponseModel.builder()
                .resCode(lpbResCode)
                .data(billPayCancelResponse)
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
