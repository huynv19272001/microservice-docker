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
import com.lpb.service.bidv.model.response.BIDVDataResponseCT002;
import com.lpb.service.bidv.model.response.BIDVResponse;
import com.lpb.service.bidv.process.CT002Process;
import com.lpb.service.bidv.repositories.EsbBIDVTransactionRepository;
import com.lpb.service.bidv.service.EsbBIDVTransactionService;
import com.lpb.service.bidv.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
public class CT002ProcessImpl implements CT002Process {
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
    public ResponseModel<List<BIDVDataResponseCT002>> requestCT002(LPBRequest<LPBDataRequestCT002> lpbRequestCT002) throws Exception {
        //validate đầu vào
//        validate(lpbRequestCT002);
        //call service info
        List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo = esbBIDVTransactionService.listServiceInfo
            (constant.getHAS_ROLE(), constant.getSERVICE_ID(), constant.getPRODUCT_CODE_EXPORT_MESSAGE_CT002());
        String urlApi = serviceInfo.get(0).getURL_API() + serviceInfo.get(0).getCONNECTOR_URL();

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        BIDVRequest bidvRequest = initRequestCT002(lpbRequestCT002, serviceInfo);
        HttpEntity<BIDVRequest> entity = new HttpEntity<>(bidvRequest, headers);
        String responseCT002 = restTemplate.getRestTemplatePartner().exchange
            (urlApi, HttpMethod.POST, entity, String.class).getBody();

        log.info(lpbRequestCT002.getMsgId() + " Response call Partner: " + responseCT002);

        BIDVResponse<BIDVDataResponseCT002> asResponseCT002 = objectMapper.
            readValue(responseCT002, new TypeReference<BIDVResponse<BIDVDataResponseCT002>>() {
            });

        if (!asResponseCT002.getStatus().equals("00")) {
            lpbResCode.setErrorCode(asResponseCT002.getStatus());
            lpbResCode.setErrorDesc(asResponseCT002.getMessage());
            responseModel.setResCode(lpbResCode);
        } else {
            try {
                log.info("CT002 export data size: {}", asResponseCT002.getData().size());
            } catch (Exception e) {
                log.error("error: {}", ExceptionUtils.getStackTrace(e));
            }
//            log.info(lpbRequestCT002.getMsgId() + " CHECK VERIFY");
            if (!utils.verify(asResponseCT002, serviceInfo, "export")) {
                lpbResCode.setErrorCode(MessageContent.VERIFY_SIGNATURE.label);
                lpbResCode.setErrorDesc(MessageContent.VERIFY_SIGNATURE.description);
                responseModel.setResCode(lpbResCode);
            } else {
                lpbResCode.setErrorCode(asResponseCT002.getStatus());
                lpbResCode.setErrorDesc(asResponseCT002.getMessage());
                responseModel.setResCode(lpbResCode);
                responseModel.setData(asResponseCT002.getData());
            }
        }

        EsbBIDVTransaction esbBIDVTransaction = EsbBIDVTransaction.builder()
            .appId(esbBIDVTransactionService.getAppMsgID())
            .msgId(lpbRequestCT002.getMsgId())
            .requestEsb(objectMapper.writeValueAsString(lpbRequestCT002))
            .responseEsb(objectMapper.writeValueAsString(responseModel))
            .requestPartner(objectMapper.writeValueAsString(bidvRequest))
            .responsePartner(objectMapper.writeValueAsString(responseCT002))
            .errorCode(responseModel.getResCode().getErrorCode())
            .errorDesc(responseModel.getResCode().getErrorDesc())
            .requestDate(bidvRequest.getRequestDate())
            .createDt(new Date())
            .build();
        esbBIDVTransactionService.save(esbBIDVTransaction);

        log.info(lpbRequestCT002.getMsgId() + " Response: " + objectMapper.writeValueAsString(responseModel));

        return responseModel;
    }

    private void validate(LPBRequest<LPBDataRequestCT002> lpbDataRequestCT002) {
        ValidateCommon.validateNullObject(lpbDataRequestCT002.getMsgId(), "msgId");
        lpbDataRequestCT002.getData().forEach(elem -> {
            try {
                //validate check null
                ValidateCommon.validateNullObject(elem.getBankID(), "bankID");

                //validate check length
                ValidateCommon.validateLengthObject(elem.getBankID(), elem, "bankID");

                //validate check currentdate
                ValidateCommon.validateCurrentDate(elem.getExportDate(), "exportDate", "dd/MM/yyyy HH:mm:ss");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
    }

    private BIDVRequest initRequestCT002(LPBRequest<LPBDataRequestCT002> lpbRequestCT002,
                                         List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo) throws Exception {
        List<BIDVDataRequestCT002> listDataRequestCT002 = convertDataRequest(lpbRequestCT002.getData());
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(listDataRequestCT002);

        //tạm thời mật khẩu để trống
        BIDVRequest<BIDVDataRequestCT002> requestCT002 = BIDVRequest.<BIDVDataRequestCT002>builder()
            .requestDate(DateUtils.getCurrentLocalDateTimeStamp())
            .accessToken(utils.getAccessToken(serviceInfo))
            .data(listDataRequestCT002)
            .appCode(serviceInfo.get(0).getUDF4())
            .signature(utils.getSignature(data, serviceInfo))
            .build();
        log.info(lpbRequestCT002.getMsgId() + " Request call Partner: " + objectMapper.writeValueAsString(requestCT002));
        return requestCT002;
    }

    private List<BIDVDataRequestCT002> convertDataRequest(List<LPBDataRequestCT002> data) {
        List<BIDVDataRequestCT002> listDataRequestCT002 = new ArrayList<>();
        data.forEach(elem -> {
            BIDVDataRequestCT002 dataRequestCT002 = BIDVDataRequestCT002.builder()
                .bankID(elem.getBankID())
                .exportDate(elem.getExportDate())
                .build();

            listDataRequestCT002.add(dataRequestCT002);
        });
        return listDataRequestCT002;
    }

}
