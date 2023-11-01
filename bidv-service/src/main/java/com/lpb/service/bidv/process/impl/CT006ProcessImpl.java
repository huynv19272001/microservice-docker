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
import com.lpb.service.bidv.model.request.BIDVDataRequestCT006;
import com.lpb.service.bidv.model.request.BIDVRequest;
import com.lpb.service.bidv.model.request.LPBDataRequestCT006;
import com.lpb.service.bidv.model.request.LPBRequest;
import com.lpb.service.bidv.model.response.BIDVDataResponseCT006;
import com.lpb.service.bidv.model.response.BIDVResponse;
import com.lpb.service.bidv.process.CT006Process;
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
public class CT006ProcessImpl implements CT006Process {
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
    public ResponseModel<List<BIDVDataResponseCT006>> requestCT006(LPBRequest<LPBDataRequestCT006> lpbRequestCT006) throws Exception {
        //validate đầu vào
//        validate(lpbRequestCT006);
        //call service info
        List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo = esbBIDVTransactionService.listServiceInfo
            (constant.getHAS_ROLE(), constant.getSERVICE_ID(), constant.getPRODUCT_CODE_SEQUENCE_EXPORT_MESSAGE_CT006());
        String urlApi = serviceInfo.get(0).getURL_API() + serviceInfo.get(0).getCONNECTOR_URL();

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        BIDVRequest bidvRequest = initRequestCT006(lpbRequestCT006, serviceInfo);
        HttpEntity<BIDVRequest> entity = new HttpEntity<>(bidvRequest, headers);
        String responseCT006 = restTemplate.getRestTemplatePartner().exchange
            (urlApi, HttpMethod.POST, entity, String.class).getBody();

        log.info(lpbRequestCT006.getMsgId() + " Response call Partner: " + responseCT006);

        BIDVResponse<BIDVDataResponseCT006> asResponseCT006 = objectMapper.
            readValue(responseCT006, new TypeReference<BIDVResponse<BIDVDataResponseCT006>>() {
            });

        if (!asResponseCT006.getStatus().equals("00")) {
            lpbResCode.setErrorCode(asResponseCT006.getStatus());
            lpbResCode.setErrorDesc(asResponseCT006.getMessage());
            responseModel.setResCode(lpbResCode);
        } else {
            if (!utils.verify(asResponseCT006, serviceInfo)) {
                lpbResCode.setErrorCode(MessageContent.VERIFY_SIGNATURE.label);
                lpbResCode.setErrorDesc(MessageContent.VERIFY_SIGNATURE.description);
                responseModel.setResCode(lpbResCode);
            } else {
                lpbResCode.setErrorCode(asResponseCT006.getStatus());
                lpbResCode.setErrorDesc(asResponseCT006.getMessage());
                responseModel.setResCode(lpbResCode);
                responseModel.setData(asResponseCT006.getData());
            }
        }

        EsbBIDVTransaction esbBIDVTransaction = EsbBIDVTransaction.builder()
            .appId(esbBIDVTransactionService.getAppMsgID())
            .msgId(lpbRequestCT006.getMsgId())
            .requestEsb(objectMapper.writeValueAsString(lpbRequestCT006))
            .responseEsb(objectMapper.writeValueAsString(responseModel))
            .requestPartner(objectMapper.writeValueAsString(bidvRequest))
            .responsePartner(objectMapper.writeValueAsString(responseCT006))
            .errorCode(responseModel.getResCode().getErrorCode())
            .errorDesc(responseModel.getResCode().getErrorDesc())
            .requestDate(bidvRequest.getRequestDate())
            .createDt(new Date())
            .build();
        esbBIDVTransactionService.save(esbBIDVTransaction);

        log.info(lpbRequestCT006.getMsgId() + " Response: " + objectMapper.writeValueAsString(responseModel));
        return responseModel;
    }

    private void validate(LPBRequest<LPBDataRequestCT006> lpbDataRequestCT006) {
        ValidateCommon.validateNullObject(lpbDataRequestCT006.getMsgId(), "msgId");
        lpbDataRequestCT006.getData().forEach(elem -> {
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

    private BIDVRequest initRequestCT006(LPBRequest<LPBDataRequestCT006> lpbRequestCT006,
                                         List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo) throws Exception {
        List<BIDVDataRequestCT006> listDataRequestCT006 = convertDataRequest(lpbRequestCT006.getData());
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(listDataRequestCT006);

        //tạm thời mật khẩu để trống
        BIDVRequest<BIDVDataRequestCT006> requestCT006 = BIDVRequest.<BIDVDataRequestCT006>builder()
            .requestDate(DateUtils.getCurrentLocalDateTimeStamp())
            .accessToken(utils.getAccessToken(serviceInfo))
            .data(listDataRequestCT006)
            .appCode(serviceInfo.get(0).getUDF4())
            .signature(utils.getSignature(data, serviceInfo))
            .build();
        log.info(lpbRequestCT006.getMsgId() + " Request call Partner: " + objectMapper.writeValueAsString(requestCT006));
        return requestCT006;
    }

    private List<BIDVDataRequestCT006> convertDataRequest(List<LPBDataRequestCT006> data) {
        List<BIDVDataRequestCT006> listDataRequestCT006 = new ArrayList<>();
        data.forEach(elem -> {
            BIDVDataRequestCT006 dataRequestCT006 = BIDVDataRequestCT006.builder()
                .mtId(elem.getMtId())
                .build();
            listDataRequestCT006.add(dataRequestCT006);
        });
        return listDataRequestCT006;
    }
}
