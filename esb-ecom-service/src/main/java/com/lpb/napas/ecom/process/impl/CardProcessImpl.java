package com.lpb.napas.ecom.process.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.napas.ecom.dto.CardInfoDTO;
import com.lpb.napas.ecom.dto.CardInfoREQDTO;
import com.lpb.napas.ecom.dto.DebitCardInfoREQDTO;
import com.lpb.napas.ecom.dto.DebitCardInfoRESDTO;
import com.lpb.napas.ecom.model.config.ServiceApiConfig;
import com.lpb.napas.ecom.process.ICardProcess;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Log4j2
@Service
public class CardProcessImpl implements ICardProcess {
    @Autowired
    ServiceApiConfig serviceApiConfig;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseModel executeListCardInfo(CardInfoREQDTO cardInfoREQDTO, String requestorTransId) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<CardInfoREQDTO> entity = new HttpEntity<>(cardInfoREQDTO, headers);
            String responseGetListCard = restTemplate.exchange
                (serviceApiConfig.getListCardInfo(), HttpMethod.POST, entity, String.class).getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseModel dataResponse = objectMapper.readValue(responseGetListCard, ResponseModel.class);

            List<CardInfoDTO> dataResponseModel = objectMapper.convertValue(
                dataResponse.getData(),
                new TypeReference<List<CardInfoDTO>>() {
                }
            );

            if (dataResponse.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                responseModel.setData(dataResponseModel);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            log.info(requestorTransId + "-" + cardInfoREQDTO.getAppId() + " Get CardInfo: " + lpbResCode.getErrorDesc());
        } catch (HttpClientErrorException e) {
            log.error(requestorTransId + "-" + cardInfoREQDTO.getAppId() + " Exception Get CardInfo: " + e.getResponseBodyAsString());
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        } catch (Exception e) {
            log.error(requestorTransId + "-" + cardInfoREQDTO.getAppId() + " Exception Get CardInfo" + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public ResponseModel executeDebitCardInfo(DebitCardInfoREQDTO debitCardInfoREQDTO, String requestorTransId) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<DebitCardInfoREQDTO> entity = new HttpEntity<>(debitCardInfoREQDTO, headers);
            String responseGetListCard = restTemplate.exchange
                (serviceApiConfig.getDebitCardInfo(), HttpMethod.POST, entity, String.class).getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseModel dataResponse = objectMapper.readValue(responseGetListCard, ResponseModel.class);

            DebitCardInfoRESDTO dataResponseModel = objectMapper.convertValue(
                dataResponse.getData(),
                new TypeReference<DebitCardInfoRESDTO>() {
                }
            );
            if (dataResponse.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                responseModel.setData(dataResponseModel);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            log.info(requestorTransId + "-" + debitCardInfoREQDTO.getAppId() + " Get DebitCardInfo: " + lpbResCode.getErrorDesc());
        } catch (HttpClientErrorException e) {
            log.error(requestorTransId + "-" + debitCardInfoREQDTO.getAppId() + " Exception Get DebitCardInfo: " + e.getResponseBodyAsString());
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        } catch (Exception e) {
            log.error(requestorTransId + "-" + debitCardInfoREQDTO.getAppId() + " Exception Get DebitCardInfo" + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }
}




