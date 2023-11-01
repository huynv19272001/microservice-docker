package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.constants.UNIFConstant;
import com.esb.card.dto.CardREQDTO;
import com.esb.card.dto.CardRESDTO;
import com.esb.card.dto.ServiceInfoDTO;
import com.esb.card.dto.listcardinfo.CardInfoREQDTO;
import com.esb.card.dto.unif.customerinfo.UpdateCustomerInfoREQDTO;
import com.esb.card.dto.unif.customerinfo.UpdateCustomerInfoRESDTO;
import com.esb.card.dto.unif.updatecardstatus.UpdateCardStatusREQDTO;
import com.esb.card.dto.unif.updatecardstatus.UpdateCardStatusRESDTO;
import com.esb.card.dto.updatelinkacc.UpdateLinkAccREQDTO;
import com.esb.card.dto.updatelinkacc.UpdateLinkAccRESDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.ICardUNIFService;
import com.esb.card.service.IEsbService;
import com.esb.card.utils.BuildMessageUtils;
import com.esb.card.utils.RSAUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
public class CardUNIFServiceImpl implements ICardUNIFService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    private List<ServiceInfoDTO> getServiceInfo(String productCode) {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(UNIFConstant.SERVICE_ID, productCode, productCode);
        return serviceInfo;
    }

    @Override
    public ResponseModel updateCustomerInfo(UpdateCustomerInfoREQDTO requestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(requestDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(requestDTO));
            List<ServiceInfoDTO> serviceInfo = getServiceInfo(UNIFConstant.PRODUCT_UPDATE_CUSTOMER_INFO);
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(UNIFConstant.USER_KEY_RSA);

            CardREQDTO initCardRequest = initUpdateCustomerInfoRequest(serviceInfo, esbCardCoreUserInfo, requestDTO);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(requestDTO.getMsgId() + " Request updateCustomerInfo: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() + serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(requestDTO.getMsgId() + " Response updateCustomerInfo: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertUpdateCustomerInfoRESDTO(cardResponse, esbCardCoreUserInfo, requestDTO.getMsgId()));
            } else {
                lpbResCode.setErrorCode(ErrorMessageEng.OTHER_ERROR.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.OTHER_ERROR.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(requestDTO.getMsgId() + " Response updateCustomerInfo: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(requestDTO.getMsgId() + " Exception updateCustomerInfo: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    public static <T> String buildXMLRequest(T data) {
        try {
            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            ObjectMapper objectMapper = new XmlMapper(xmlModule);
            String xml = objectMapper.writeValueAsString(data);
            return xml;
        } catch (Exception e) {
            log.info("Exception buildXMLRequest: " + e);
            return null;
        }
    }

    public static CardREQDTO initUpdateCustomerInfoRequest(List<ServiceInfoDTO> serviceInfo, EsbCardCoreUserInfo esbCardCoreUserInfo, UpdateCustomerInfoREQDTO requestDTO) throws Exception {
        UpdateCustomerInfoREQDTO data = requestDTO;
        data.setMsgId(null);

        String msg = buildXMLRequest(data);
        return BuildMessageUtils.initCardReq(serviceInfo, esbCardCoreUserInfo, requestDTO.getMsgId(), msg);
    }

    public static UpdateCustomerInfoRESDTO convertUpdateCustomerInfoRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo, String msgId) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(msgId + " updateCustomerInfo: " + "RES " + xmlResponse);
        UpdateCustomerInfoRESDTO response = xmlMapper.readValue(xmlResponse, UpdateCustomerInfoRESDTO.class);

        return response;
    }

    @Override
    public ResponseModel updateCardStatus(UpdateCardStatusREQDTO requestDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(requestDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(requestDTO));
            List<ServiceInfoDTO> serviceInfo = getServiceInfo(UNIFConstant.PRODUCT_UPDATE_CARD_STATUS);
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(UNIFConstant.USER_KEY_RSA);

            CardREQDTO initCardRequest = initUpdateCardStatusRequest(serviceInfo, esbCardCoreUserInfo, requestDTO);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(requestDTO.getMsgId() + " Request updateCustomerInfo: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() + serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(requestDTO.getMsgId() + " Response updateCustomerInfo: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertUpdateCardStatusRESDTO(cardResponse, esbCardCoreUserInfo, requestDTO.getMsgId()));
            } else {
                lpbResCode.setErrorCode(ErrorMessageEng.OTHER_ERROR.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.OTHER_ERROR.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(requestDTO.getMsgId() + " Response updateCustomerInfo: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(requestDTO.getMsgId() + " Exception updateCustomerInfo: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    public static CardREQDTO initUpdateCardStatusRequest(List<ServiceInfoDTO> serviceInfo, EsbCardCoreUserInfo esbCardCoreUserInfo, UpdateCardStatusREQDTO requestDTO) throws Exception {
        UpdateCardStatusREQDTO data = requestDTO;
        data.setMsgId(null);

        String msg = buildXMLRequest(data);
        return BuildMessageUtils.initCardReq(serviceInfo, esbCardCoreUserInfo, requestDTO.getMsgId(), msg);
    }

    public static UpdateCardStatusRESDTO convertUpdateCardStatusRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo, String msgId) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(msgId + " updateCustomerInfo: " + "RES " + xmlResponse);
        UpdateCardStatusRESDTO response = xmlMapper.readValue(xmlResponse, UpdateCardStatusRESDTO.class);

        return response;
    }

    @Override
    public ResponseModel linkAccount(CardInfoREQDTO cardInfoRequest) {
        return null;
    }

    @Override
    public ResponseModel changeDefaultAccount(CardInfoREQDTO cardInfoRequest) {
        return null;
    }
}
