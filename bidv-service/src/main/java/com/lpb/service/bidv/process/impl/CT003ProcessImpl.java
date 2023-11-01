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
import com.lpb.service.bidv.model.request.*;
import com.lpb.service.bidv.model.response.BIDVDataResponseCT003;
import com.lpb.service.bidv.model.response.BIDVResponse;
import com.lpb.service.bidv.process.CT003Process;
import com.lpb.service.bidv.repositories.EsbBIDVTransactionRepository;
import com.lpb.service.bidv.service.EsbBIDVTransactionService;
import com.lpb.service.bidv.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class CT003ProcessImpl implements CT003Process {
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
    public ResponseModel<List<BIDVDataResponseCT003>> requestCT003(LPBRequest<LPBDataRequestCT003> lpbRequestCT003) throws Exception {
        //validate đầu vào
//        validate(lpbRequestCT003);
        //call service info
        List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo = esbBIDVTransactionService.listServiceInfo
            (constant.getHAS_ROLE(), constant.getSERVICE_ID(), constant.getPRODUCT_CODE_RESULTS_MESSAGE_CT003());
        String urlApi = serviceInfo.get(0).getURL_API() + serviceInfo.get(0).getCONNECTOR_URL();

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        BIDVRequest bidvRequest = initRequestCT003(lpbRequestCT003, serviceInfo);
        HttpEntity<BIDVRequest> entity = new HttpEntity<>(bidvRequest, headers);
        String responseCT003 = restTemplate.getRestTemplatePartner().exchange
            (urlApi, HttpMethod.POST, entity, String.class).getBody();

        log.info(lpbRequestCT003.getMsgId() + " Response call Partner: " + responseCT003);

        BIDVResponse<BIDVDataResponseCT003> asResponseCT003 = objectMapper.
            readValue(responseCT003, new TypeReference<BIDVResponse<BIDVDataResponseCT003>>() {
            });

        if (!asResponseCT003.getStatus().equals("00")) {
            lpbResCode.setErrorCode(asResponseCT003.getStatus());
            lpbResCode.setErrorDesc(asResponseCT003.getMessage());
            responseModel.setResCode(lpbResCode);
        } else {
            if (!utils.verify(asResponseCT003, serviceInfo)) {
                lpbResCode.setErrorCode(MessageContent.VERIFY_SIGNATURE.label);
                lpbResCode.setErrorDesc(MessageContent.VERIFY_SIGNATURE.description);
                responseModel.setResCode(lpbResCode);
            } else {
                lpbResCode.setErrorCode(asResponseCT003.getStatus());
                lpbResCode.setErrorDesc(asResponseCT003.getMessage());
                responseModel.setResCode(lpbResCode);
                responseModel.setData(asResponseCT003.getData());
            }
        }

        EsbBIDVTransaction esbBIDVTransaction = EsbBIDVTransaction.builder()
            .appId(esbBIDVTransactionService.getAppMsgID())
            .msgId(lpbRequestCT003.getMsgId())
            .requestEsb(objectMapper.writeValueAsString(lpbRequestCT003))
            .responseEsb(objectMapper.writeValueAsString(responseModel))
            .requestPartner(objectMapper.writeValueAsString(bidvRequest))
            .responsePartner(objectMapper.writeValueAsString(responseCT003))
            .errorCode(responseModel.getResCode().getErrorCode())
            .errorDesc(responseModel.getResCode().getErrorDesc())
            .requestDate(bidvRequest.getRequestDate())
            .createDt(new Date())
            .build();
        esbBIDVTransactionService.save(esbBIDVTransaction);

        log.info(lpbRequestCT003.getMsgId() + " Response: " + objectMapper.writeValueAsString(responseModel));
        return responseModel;
    }

    private void validate(LPBRequest<LPBDataRequestCT003> lpbDataRequestCT003) {
        ValidateCommon.validateNullObject(lpbDataRequestCT003.getMsgId(), "msgId");
        lpbDataRequestCT003.getData().forEach(elem -> {
            try {
                //validate check null
                ValidateCommon.validateNullObject(elem.getHash(), "hash");

                //validate check length
                ValidateCommon.validateLengthObject(elem.getHash(), elem, "hash");

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
    }

    private BIDVRequest initRequestCT003(LPBRequest<LPBDataRequestCT003> lpbRequestCT003,
                                         List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo) throws Exception {
        List<BIDVDataRequestCT003> listDataRequestCT003 = convertDataRequest(lpbRequestCT003.getData());
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(listDataRequestCT003);

        //tạm thời mật khẩu để trống
        BIDVRequest<BIDVDataRequestCT003> requestCT003 = BIDVRequest.<BIDVDataRequestCT003>builder()
            .requestDate(DateUtils.getCurrentLocalDateTimeStamp())
            .accessToken(utils.getAccessToken(serviceInfo))
            .data(listDataRequestCT003)
            .appCode(serviceInfo.get(0).getUDF4())
            .signature(utils.getSignature(data, serviceInfo))
            .build();
        log.info(lpbRequestCT003.getMsgId() + " Request call Partner: " + objectMapper.writeValueAsString(requestCT003));
        return requestCT003;
    }

    private List<BIDVDataRequestCT003> convertDataRequest(List<LPBDataRequestCT003> data) {
        List<BIDVDataRequestCT003> listDataRequestCT003 = new ArrayList<>();
        data.forEach(elem -> {
            BIDVDataRequestCT003 dataRequestCT003 = BIDVDataRequestCT003.builder()
                .hash(elem.getHash())
                .build();
            listDataRequestCT003.add(dataRequestCT003);
        });
        return listDataRequestCT003;
    }
}
