package com.lpb.service.bidv.process.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.constant.MessageContent;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.DateUtils;
import com.lpb.esb.service.common.utils.validate.ValidateCommon;
import com.lpb.service.bidv.common.CommonConfig;
import com.lpb.service.bidv.common.Constant;
import com.lpb.service.bidv.model.entities.EsbBIDVTransaction;
import com.lpb.service.bidv.model.request.BIDVDataRequestCT001;
import com.lpb.service.bidv.model.request.LPBDataRequestCT001;
import com.lpb.service.bidv.model.request.LPBRequest;
import com.lpb.service.bidv.model.request.BIDVRequest;
import com.lpb.service.bidv.model.response.BIDVDataResponseCT001;
import com.lpb.service.bidv.model.response.BIDVResponse;
import com.lpb.service.bidv.process.CT001Process;
import com.lpb.service.bidv.repositories.EsbBIDVTransactionRepository;
import com.lpb.service.bidv.service.EsbBIDVTransactionService;
import com.lpb.service.bidv.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class CT001ProcessImpl implements CT001Process {
    @Autowired
    Constant constant;
    @Autowired
    CommonConfig restTemplate;

//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    EsbBIDVTransactionService esbBIDVTransactionService;
    @Autowired
    Utils utils;

    @Override
    public ResponseModel<List<BIDVDataResponseCT001>> requestCT001(LPBRequest<LPBDataRequestCT001> lpbRequestCT001) throws Exception {
        //validate đầu vào
//        validate(lpbRequestCT001);
        //call service info
        List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo = esbBIDVTransactionService.listServiceInfo
            (constant.getHAS_ROLE(), constant.getSERVICE_ID(), constant.getPRODUCT_CODE_IMPORT_MESSAGE_CT001());
        String urlApi = serviceInfo.get(0).getURL_API() + serviceInfo.get(0).getCONNECTOR_URL();

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        BIDVRequest bidvRequest = initRequestCT001(lpbRequestCT001, serviceInfo);
        HttpEntity<BIDVRequest> entity = new HttpEntity<>(bidvRequest, headers);
        String responseCT001 = restTemplate.getRestTemplatePartner().exchange
            (urlApi, HttpMethod.POST, entity, String.class).getBody();

        log.info(lpbRequestCT001.getMsgId() + " Response call Partner: " + responseCT001);

        BIDVResponse<BIDVDataResponseCT001> asResponseCT001 = objectMapper.
            readValue(responseCT001, new TypeReference<BIDVResponse<BIDVDataResponseCT001>>() {
            });

        if (!asResponseCT001.getStatus().equals("00")) {
            lpbResCode.setErrorCode(asResponseCT001.getStatus());
            lpbResCode.setErrorDesc(asResponseCT001.getMessage());
            responseModel.setResCode(lpbResCode);
        } else {
            if (!utils.verify(asResponseCT001, serviceInfo)) {
                lpbResCode.setErrorCode(MessageContent.VERIFY_SIGNATURE.label);
                lpbResCode.setErrorDesc(MessageContent.VERIFY_SIGNATURE.description);
                responseModel.setResCode(lpbResCode);
            } else {
                lpbResCode.setErrorCode(asResponseCT001.getStatus());
                lpbResCode.setErrorDesc(asResponseCT001.getMessage());
                responseModel.setResCode(lpbResCode);
                responseModel.setData(asResponseCT001.getData());
            }
        }

        EsbBIDVTransaction esbBIDVTransaction = EsbBIDVTransaction.builder()
            .appId(esbBIDVTransactionService.getAppMsgID())
            .msgId(lpbRequestCT001.getMsgId())
            .requestEsb(objectMapper.writeValueAsString(lpbRequestCT001))
            .responseEsb(objectMapper.writeValueAsString(responseModel))
            .requestPartner(objectMapper.writeValueAsString(bidvRequest))
            .responsePartner(objectMapper.writeValueAsString(responseCT001))
            .errorCode(responseModel.getResCode().getErrorCode())
            .errorDesc(responseModel.getResCode().getErrorDesc())
            .requestDate(bidvRequest.getRequestDate())
            .createDt(new Date())
            .build();
        esbBIDVTransactionService.save(esbBIDVTransaction);

        log.info(lpbRequestCT001.getMsgId() + " Response: " + objectMapper.writeValueAsString(responseModel));

        return responseModel;
    }


    private BIDVRequest initRequestCT001(LPBRequest<LPBDataRequestCT001> lpbRequestCT001,
                                         List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo) throws Exception {
        List<BIDVDataRequestCT001> listDataRequestCT001 = convertDataRequest(lpbRequestCT001.getData());
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(listDataRequestCT001);

        //tạm thời mật khẩu để trống
        BIDVRequest<BIDVDataRequestCT001> requestCT001 = BIDVRequest.<BIDVDataRequestCT001>builder()
            .requestDate(DateUtils.getCurrentLocalDateTimeStamp())
            .accessToken(utils.getAccessToken(serviceInfo))
            .data(listDataRequestCT001)
            .appCode(serviceInfo.get(0).getUDF4())
            .signature(utils.getSignature(data, serviceInfo))
            .build();

        log.info(lpbRequestCT001.getMsgId() + " Request call Partner: " + objectMapper.writeValueAsString(requestCT001));
        return requestCT001;
    }

    private List<BIDVDataRequestCT001> convertDataRequest(List<LPBDataRequestCT001> data) {
        List<BIDVDataRequestCT001> listDataRequestCT001 = new ArrayList<>();
        data.forEach(elem -> {
            BIDVDataRequestCT001 dataRequestCT001 = BIDVDataRequestCT001.builder()
                .f1(elem.getSenderBankId())
                .f2(elem.getReceiveBankId())
                .f3(elem.getReqRefNo())
                .f4(elem.getOperationCode())
                .f5(elem.getValueDate())
                .f6(elem.getTxnCcy())
                .f7(elem.getTxnAmount())
                .f8(elem.getSendersAccount())
                .f9(elem.getSendersName())
                .f10(elem.getOrderingInstitution())
                .f11(elem.getBensBankCode())
                .f12(elem.getBensAccount())
                .f13(elem.getBensName())
                .f14(elem.getTrnDesc())
                .f15(elem.getCharge())
                .f16(elem.getSenderToReceiverInformation())
                .f17(elem.getCurrencyInstructedAmount())
                .msgType(elem.getMsgType())
                .status(elem.getStatus())
                .build();
            listDataRequestCT001.add(dataRequestCT001);
        });
        return listDataRequestCT001;
    }

    private void validate(LPBRequest<LPBDataRequestCT001> lpbRequestCT001) {
        ValidateCommon.validateNullObject(lpbRequestCT001.getMsgId(), "msgId");
        lpbRequestCT001.getData().forEach(elem -> {
            try {
                //validate check null
                ValidateCommon.validateNullObject(elem.getSenderBankId(), "senderBankId");
                ValidateCommon.validateNullObject(elem.getReceiveBankId(), "receiveBankId");
                ValidateCommon.validateNullObject(elem.getReqRefNo(), "reqRefNo");
                ValidateCommon.validateNullObject(elem.getOperationCode(), "operationCode");
                ValidateCommon.validateNullObject(elem.getValueDate(), "valueDate");
                ValidateCommon.validateNullObject(elem.getTxnCcy(), "txnCcy");
                ValidateCommon.validateNullObject(elem.getTxnAmount(), "txnAmount");
                ValidateCommon.validateNullObject(elem.getSendersName(), "sendersName");
                ValidateCommon.validateNullObject(elem.getOrderingInstitution(), "orderingInstitution");
                ValidateCommon.validateNullObject(elem.getBensBankCode(), "bensBankCode");
                ValidateCommon.validateNullObject(elem.getBensName(), "bensName");
                ValidateCommon.validateNullObject(elem.getTrnDesc(), "trnDesc");
                ValidateCommon.validateNullObject(elem.getCharge(), "charge");
                ValidateCommon.validateNullObject(elem.getMsgType(), "msgType");
                ValidateCommon.validateNullObject(elem.getStatus(), "status");

                //validate check length
                ValidateCommon.validateLengthObject(elem.getSenderBankId(), elem, "senderBankId");
                ValidateCommon.validateLengthObject(elem.getReceiveBankId(), elem, "receiveBankId");
                ValidateCommon.validateLengthObject(elem.getReqRefNo(), elem, "reqRefNo");
                ValidateCommon.validateLengthObject(elem.getOperationCode(), elem, "operationCode");
                ValidateCommon.validateLengthObject(elem.getValueDate(), elem, "valueDate");
                ValidateCommon.validateLengthObject(elem.getTxnCcy(), elem, "txnCcy");
                ValidateCommon.validateLengthObject(elem.getTxnAmount(), elem, "txnAmount");
                ValidateCommon.validateLengthObject(elem.getSendersAccount(), elem, "sendersAccount");
                ValidateCommon.validateLengthObject(elem.getSendersName(), elem, "sendersName");
                ValidateCommon.validateLengthObject(elem.getOrderingInstitution(), elem, "orderingInstitution");
                ValidateCommon.validateLengthObject(elem.getBensBankCode(), elem, "bensBankCode");
                ValidateCommon.validateLengthObject(elem.getBensAccount(), elem, "bensAccount");
                ValidateCommon.validateLengthObject(elem.getBensName(), elem, "bensName");
                ValidateCommon.validateLengthObject(elem.getTrnDesc(), elem, "trnDesc");
                ValidateCommon.validateLengthObject(elem.getCharge(), elem, "charge");
                ValidateCommon.validateLengthObject(elem.getSenderToReceiverInformation(), elem, "senderToReceiverInformation");
                ValidateCommon.validateLengthObject(elem.getCurrencyInstructedAmount(), elem, "currencyInstructedAmount");
                ValidateCommon.validateLengthObject(elem.getMsgType(), elem, "msgType");
                ValidateCommon.validateLengthObject(elem.getStatus(), elem, "status");

                //validate time f5 Value Date
                ValidateCommon.validateSmallCurrentDate(elem.getValueDate(), "valueDate", "yyMMdd");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
    }
}
