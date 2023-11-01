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
import com.lpb.service.bidv.model.request.BIDVDataRequestCT005;
import com.lpb.service.bidv.model.request.BIDVRequest;
import com.lpb.service.bidv.model.request.LPBDataRequestCT005;
import com.lpb.service.bidv.model.request.LPBRequest;
import com.lpb.service.bidv.model.response.BIDVDataResponseCT005;
import com.lpb.service.bidv.model.response.BIDVResponse;
import com.lpb.service.bidv.process.CT005Process;
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
public class CT005ProcessImpl implements CT005Process {
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
    public ResponseModel<List<BIDVDataResponseCT005>> requestCT005(LPBRequest<LPBDataRequestCT005> lpbRequestCT005) throws Exception {
        //validate đầu vào
//        validate(lpbRequestCT005);
        //call service info
        List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo = esbBIDVTransactionService.listServiceInfo
            (constant.getHAS_ROLE(), constant.getSERVICE_ID(), constant.getPRODUCT_CODE_UPDATE_EXPORTED_MESSAGE_CT005());
        String urlApi = serviceInfo.get(0).getURL_API() + serviceInfo.get(0).getCONNECTOR_URL();

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        BIDVRequest bidvRequest = initRequestCT005(lpbRequestCT005, serviceInfo);
        HttpEntity<BIDVRequest> entity = new HttpEntity<>(bidvRequest, headers);
        String responseCT005 = restTemplate.getRestTemplatePartner().exchange
            (urlApi, HttpMethod.POST, entity, String.class).getBody();

        log.info(lpbRequestCT005.getMsgId() + " Response call Partner: " + responseCT005);

        BIDVResponse<BIDVDataResponseCT005> asResponseCT005 = objectMapper.
            readValue(responseCT005, new TypeReference<BIDVResponse<BIDVDataResponseCT005>>() {
            });

        if (!asResponseCT005.getStatus().equals("00")) {
            lpbResCode.setErrorCode(asResponseCT005.getStatus());
            lpbResCode.setErrorDesc(asResponseCT005.getMessage());
            responseModel.setResCode(lpbResCode);
        } else {
            if (!utils.verify(asResponseCT005, serviceInfo)) {
                lpbResCode.setErrorCode(MessageContent.VERIFY_SIGNATURE.label);
                lpbResCode.setErrorDesc(MessageContent.VERIFY_SIGNATURE.description);
                responseModel.setResCode(lpbResCode);
            } else {
                lpbResCode.setErrorCode(asResponseCT005.getStatus());
                lpbResCode.setErrorDesc(asResponseCT005.getMessage());
                responseModel.setResCode(lpbResCode);
                responseModel.setData(asResponseCT005.getData());
            }
        }

        EsbBIDVTransaction esbBIDVTransaction = EsbBIDVTransaction.builder()
            .appId(esbBIDVTransactionService.getAppMsgID())
            .msgId(lpbRequestCT005.getMsgId())
            .requestEsb(objectMapper.writeValueAsString(lpbRequestCT005))
            .responseEsb(objectMapper.writeValueAsString(responseModel))
            .requestPartner(objectMapper.writeValueAsString(bidvRequest))
            .responsePartner(objectMapper.writeValueAsString(responseCT005))
            .errorCode(responseModel.getResCode().getErrorCode())
            .errorDesc(responseModel.getResCode().getErrorDesc())
            .requestDate(bidvRequest.getRequestDate())
            .createDt(new Date())
            .build();
        esbBIDVTransactionService.save(esbBIDVTransaction);

        log.info(lpbRequestCT005.getMsgId() + " Response: " + objectMapper.writeValueAsString(responseModel));

        return responseModel;
    }

    private void validate(LPBRequest<LPBDataRequestCT005> lpbDataRequestCT005) {
        ValidateCommon.validateNullObject(lpbDataRequestCT005.getMsgId(), "msgId");
        lpbDataRequestCT005.getData().forEach(elem -> {
            try {
                //validate check null
                ValidateCommon.validateNullObject(elem.getMtId(), "mtId");

                //validate check length
                ValidateCommon.validateLengthObject(elem.getMtId(), elem, "mtId");

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
    }

    private BIDVRequest initRequestCT005(LPBRequest<LPBDataRequestCT005> lpbRequestCT005,
                                         List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo) throws Exception {
        List<BIDVDataRequestCT005> listDataRequestCT005 = convertDataRequest(lpbRequestCT005.getData());
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(listDataRequestCT005);

        //tạm thời mật khẩu để trống
        BIDVRequest<BIDVDataRequestCT005> requestCT005 = BIDVRequest.<BIDVDataRequestCT005>builder()
            .requestDate(DateUtils.getCurrentLocalDateTimeStamp())
            .accessToken(utils.getAccessToken(serviceInfo))
            .data(listDataRequestCT005)
            .appCode(serviceInfo.get(0).getUDF4())
            .signature(utils.getSignature(data, serviceInfo))
            .build();
        log.info(lpbRequestCT005.getMsgId() + " Request call Partner: " + objectMapper.writeValueAsString(requestCT005));
        return requestCT005;
    }

    private List<BIDVDataRequestCT005> convertDataRequest(List<LPBDataRequestCT005> data) {
        List<BIDVDataRequestCT005> listDataRequestCT005 = new ArrayList<>();
        data.forEach(elem -> {
            BIDVDataRequestCT005 dataRequestCT005 = BIDVDataRequestCT005.builder()
                .mtId(elem.getMtId())
                .build();
            listDataRequestCT005.add(dataRequestCT005);
        });
        return listDataRequestCT005;
    }
}
