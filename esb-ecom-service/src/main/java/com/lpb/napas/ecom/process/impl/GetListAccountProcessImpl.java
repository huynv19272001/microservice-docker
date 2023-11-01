package com.lpb.napas.ecom.process.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.napas.ecom.dto.*;
import com.lpb.napas.ecom.model.EsbSystemEcomLog;
import com.lpb.napas.ecom.model.config.ServiceApiConfig;
import com.lpb.napas.ecom.process.IGetListAccountProcess;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class GetListAccountProcessImpl implements IGetListAccountProcess {

    @Autowired
    ServiceApiConfig serviceApiConfig;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseModel excuteGetListAccount(GetAccountListDTO getAccountListDTO, String requestorTransId, String appId) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<GetAccountListDTO> entity = new HttpEntity<>(getAccountListDTO, headers);
            String response = restTemplate.exchange
                (serviceApiConfig.getGetListAccount(), HttpMethod.POST, entity, String.class).getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseModel dataResponseModel = objectMapper.readValue(response, ResponseModel.class);
            List<GetAccountListRESDTO> getListAccount = objectMapper.convertValue(
                dataResponseModel.getData(),
                new TypeReference<List<GetAccountListRESDTO>>() {
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
            log.info(requestorTransId + "-" + appId + " Get ListAccount: " + lpbResCode.getErrorDesc());
        } catch (HttpClientErrorException e) {
            log.error(requestorTransId + "-" + appId + " Exception Get ListAccount: " + e.getResponseBodyAsString());
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        } catch (Exception e) {
            log.error(requestorTransId + "-" + appId + " Exception Get ListAccount" + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    public GetAccountListREQDTO initGetAccountListDTO(CardInfoDTO cardInfoDTO,
                                                      EsbSystemEcomLog esbSystemEcomLog) {
        GetAccountListREQDTO getAccountListREQDTO = new GetAccountListREQDTO();
        FCubsHeaderDTO fCubsHeader = new FCubsHeaderDTO();
        GetAccountListDTO getAccountList = new GetAccountListDTO();
        fCubsHeader.setMsgId(esbSystemEcomLog.getAppId());
        getAccountList.setAccountNumber(cardInfoDTO.getAccountNumber());
        getAccountListREQDTO.setGetAccountList(getAccountList);
        getAccountListREQDTO.setFCubsHeader(fCubsHeader);
        return getAccountListREQDTO;
    }
}

