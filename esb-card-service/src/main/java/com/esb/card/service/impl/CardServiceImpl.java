package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.*;
import com.esb.card.dto.availinfocard.AvailInfoCardREQDTO;
import com.esb.card.dto.debitcardinfo.DebitCardInfoREQDTO;
import com.esb.card.dto.listcardinfo.CardInfoREQDTO;
import com.esb.card.dto.updatelimitpayment.UpdateLimitPaymentREQDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.ICardService;
import com.esb.card.service.IEsbService;
import com.esb.card.utils.BuildMessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.esb.service.common.utils.code.ErrorMessageEng;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
public class CardServiceImpl implements ICardService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel getCardInfo(CardInfoREQDTO cardInfoRequest) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(cardInfoRequest.getAppId() + " MSGREQ: " + objectMapper.writeValueAsString(cardInfoRequest));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoListCardInfo();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = BuildMessageUtils.initDataCardInfoRequest(serviceInfo, cardInfoRequest, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);

            log.info(cardInfoRequest.getAppId() + " Request ListCardInfo: " + objectMapper.writeValueAsString(initCardRequest));

            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);
            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);

            log.info(cardInfoRequest.getAppId() + " Response ListCardInfo: " + response.getBody());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                responseModel.setData(BuildMessageUtils.convertCardToCardInfo(cardResponse, esbCardCoreUserInfo, cardInfoRequest));
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(cardInfoRequest.getAppId() + " Response ListCardInfo: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            log.info(cardInfoRequest.getAppId() + " Exception ListCardInfo: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    @Override
    public ResponseModel getDebitCardInfo(DebitCardInfoREQDTO debitCardInfoREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(debitCardInfoREQDTO.getAppId() + " MSGREQ: " + objectMapper.writeValueAsString(debitCardInfoREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoDebitCardInfo();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = BuildMessageUtils.initDataDebitCardInfoRequest(serviceInfo, debitCardInfoREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);

            log.info(debitCardInfoREQDTO.getAppId() + " Request DebitCardInfo: " + objectMapper.writeValueAsString(initCardRequest));

            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);
            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);

            log.info(debitCardInfoREQDTO.getAppId() + " Response DebitCardInfo: " + response.getBody());
            //để equals vs 0 là vì cardResponse trả về như vậy không phải trong ErrorMassage
            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                responseModel.setData(BuildMessageUtils.convertCardToDebitCardInfo(cardResponse, esbCardCoreUserInfo, debitCardInfoREQDTO));
            } else {
                lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
                lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(debitCardInfoREQDTO.getAppId() + " Response DebitCardInfo: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            log.info(debitCardInfoREQDTO.getAppId() + " Exception DebitCardInfo: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    @Override
    public ResponseModel getListAcc(DebitCardInfoREQDTO debitCardInfoREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(debitCardInfoREQDTO.getAppId() + " MSGREQ: " + objectMapper.writeValueAsString(debitCardInfoREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoListAcc();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = BuildMessageUtils.initDataListAccRequest(serviceInfo, debitCardInfoREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(debitCardInfoREQDTO.getAppId() + " Request GetListAcc: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(debitCardInfoREQDTO.getAppId() + " Response GetListAcc: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                responseModel.setData(BuildMessageUtils.convertListAccInfo(cardResponse, esbCardCoreUserInfo, debitCardInfoREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(debitCardInfoREQDTO.getAppId() + " Response GetListAcc: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            log.info(debitCardInfoREQDTO.getAppId() + " Exception getListAcc: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    @Override
    public ResponseModel getAvailInfoCard(AvailInfoCardREQDTO availInfoCardREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(availInfoCardREQDTO.getAppId() + " MSGREQ: " + objectMapper.writeValueAsString(availInfoCardREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoAvailInfoCard();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = BuildMessageUtils.initAvailInfoCardRequest(serviceInfo, availInfoCardREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(availInfoCardREQDTO.getAppId() + " Request AvailInfoCard " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(availInfoCardREQDTO.getAppId() + " Response AvailInfoCard: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                responseModel.setData(BuildMessageUtils.convertAvailInfoCard(cardResponse, esbCardCoreUserInfo, availInfoCardREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(availInfoCardREQDTO.getAppId() + " Response AvailInfoCard: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(availInfoCardREQDTO.getAppId() + " Exception AvailInfoCard: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    @Override
    public ResponseModel updateLimitPayment(UpdateLimitPaymentREQDTO updateLimitPaymentREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(updateLimitPaymentREQDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(updateLimitPaymentREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoUpdateLimitPayment();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = BuildMessageUtils.initUpdateLimitPaymentRequest(serviceInfo, updateLimitPaymentREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(updateLimitPaymentREQDTO.getMsgId() + " Request UpdateLimitPayment: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(updateLimitPaymentREQDTO.getMsgId() + " Response UpdateLimitPayment: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(BuildMessageUtils.convertUpdateLimitPayment(cardResponse, esbCardCoreUserInfo, updateLimitPaymentREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessageEng.OTHER_ERROR.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.OTHER_ERROR.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(updateLimitPaymentREQDTO.getMsgId() + " Response UpdateLimitPayment: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(updateLimitPaymentREQDTO.getMsgId() + " Exception UpdateLimitPayment: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private List<ServiceInfoDTO> initServiceInfoListCardInfo() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.PRODUCT_LIST_CARD_INFO, Constant.HAS_ROLE);
        return serviceInfo;
    }

    private List<ServiceInfoDTO> initServiceInfoDebitCardInfo() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.PRODUCT_DEBIT_CARD_INFO, Constant.HAS_ROLE);
        return serviceInfo;
    }

    private List<ServiceInfoDTO> initServiceInfoListAcc() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.PRODUCT_LIST_ACC, Constant.HAS_ROLE);
        return serviceInfo;
    }

    private List<ServiceInfoDTO> initServiceInfoAvailInfoCard() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.AVAIL_INFO_CARD, Constant.HAS_ROLE);
        return serviceInfo;
    }

    private List<ServiceInfoDTO> initServiceInfoUpdateLimitPayment() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.UPDATE_LIMIT_PAYMENT, Constant.HAS_ROLE);
        return serviceInfo;
    }
}
