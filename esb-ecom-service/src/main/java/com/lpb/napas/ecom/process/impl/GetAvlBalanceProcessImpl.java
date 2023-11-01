package com.lpb.napas.ecom.process.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.napas.ecom.common.Constant;
import com.lpb.napas.ecom.dto.*;
import com.lpb.napas.ecom.model.EsbSystemEcomLog;
import com.lpb.napas.ecom.model.config.ServiceApiConfig;
import com.lpb.napas.ecom.process.IGetAvlBalanceProcess;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class GetAvlBalanceProcessImpl implements IGetAvlBalanceProcess {

    @Autowired
    ServiceApiConfig serviceApiConfig;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseModel excuteGetAvlBalance(GetAvlBalanceREQDTO getAvlBalanceREQDTO, String requestorTransId, String appId) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<GetAvlBalanceREQDTO> entity = new HttpEntity<>(getAvlBalanceREQDTO, headers);
            String response = restTemplate.exchange
                (serviceApiConfig.getGetAvlBalance(), HttpMethod.POST, entity, String.class).getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseModel dataResponseModel = objectMapper.readValue(response, ResponseModel.class);
            List<GetAvlBalanceRESDTO> getListAccount = objectMapper.convertValue(
                dataResponseModel.getData(),
                new TypeReference<List<GetAvlBalanceRESDTO>>() {
                }
            );
            if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                responseModel.setData(getListAccount);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            log.info(requestorTransId + "-" + appId + " Get GetAvlBalance: " + lpbResCode.getErrorDesc());
        } catch (HttpClientErrorException e) {
            log.error(requestorTransId + "-" + appId + " Exception Get GetAvlBalance: " + e.getResponseBodyAsString());
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        } catch (Exception e) {
            log.error(requestorTransId + "-" + appId + " Exception Get GetAvlBalance" + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public GetAvlBalanceREQDTO initGetAvlBalanceDTO(CardInfoDTO cardInfoDTO, EsbSystemEcomLog esbSystemEcomLog, VerifyPaymentRequest verifyPaymentRequest) {
        GetAvlBalanceDTO getAvlBalanceDTO = GetAvlBalanceDTO.builder()
            .accountNumber(cardInfoDTO.getAccountNumber())
            .build();
        GetAvlBalanceREQDTO getAvlBalanceREQDTO = new GetAvlBalanceREQDTO();
        FCubsHeaderDTO fCubsHeader = new FCubsHeaderDTO();
        fCubsHeader.setMsgId(esbSystemEcomLog.getAppId());
        getAvlBalanceREQDTO.setGetAvlBalance(getAvlBalanceDTO);
        getAvlBalanceREQDTO.setFCubsHeader(fCubsHeader);
        return getAvlBalanceREQDTO;
    }

    @Override
    public Boolean checkAvlBalance(GetAvlBalanceRESDTO getAvlBalanceRESDTO,
                                   DataVerifyPaymentRequest dataVerifyPaymentRequest) {
        Boolean result = false;
        BigInteger numBig = new BigInteger(getAvlBalanceRESDTO.getAcyAvlBal())
            .subtract(new BigInteger(dataVerifyPaymentRequest.getTransaction().getAmount()));

        if (numBig.compareTo(new BigInteger(Constant.MIN_BALANCE.toString())) >= 0) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
