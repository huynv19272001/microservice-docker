package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.CardREQDTO;
import com.esb.card.dto.CardRESDTO;
import com.esb.card.dto.ServiceInfoDTO;
import com.esb.card.dto.creditcardinfo.CreditCardInfoREQDTO;
import com.esb.card.dto.creditcardinfo.CreditCardInfoRESDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.CreditCardInfoService;
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
import com.lpb.esb.service.common.utils.StringUtils;
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
public class CreditCardInfoServiceImpl implements CreditCardInfoService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel getCreditCard(CreditCardInfoREQDTO creditCardInfoREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(creditCardInfoREQDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(creditCardInfoREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoCreditCard();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = initServiceInfoCreditCardRequest(serviceInfo, creditCardInfoREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(creditCardInfoREQDTO.getMsgId() + " Request GetCreditCard: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(creditCardInfoREQDTO.getMsgId() + " Response GetCreditCard: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertGetInfoDPPRESDTO(cardResponse, esbCardCoreUserInfo, creditCardInfoREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(creditCardInfoREQDTO.getMsgId() + " Response GetCreditCard: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            log.info(creditCardInfoREQDTO.getMsgId() + " Exception GetCreditCard: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private List<ServiceInfoDTO> initServiceInfoCreditCard() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.CREDIT_CARD_INFO, Constant.HAS_ROLE);
        return serviceInfo;
    }

    public static CardREQDTO initServiceInfoCreditCardRequest(List<ServiceInfoDTO> serviceInfo, CreditCardInfoREQDTO creditCardInfoREQDTO,
                                                              EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgCreditCard(creditCardInfoREQDTO);
        return BuildMessageUtils.initCardReq(serviceInfo, esbCardCoreUserInfo, creditCardInfoREQDTO.getMsgId(), msg);
    }

    public static String buildMsgCreditCard(CreditCardInfoREQDTO creditCardInfoREQDTO) {
        StringBuilder bu = new StringBuilder();
        bu.append("<CreditCardInfoRequest>");
        if (!StringUtils.isNullOrBlank(creditCardInfoREQDTO.getCardNumber())) {
            bu.append("<CardNumber>");
            bu.append(creditCardInfoREQDTO.getCardNumber());
            bu.append("</CardNumber>");
        } else {
            bu.append("<CardNumber></CardNumber>");
        }
        if (!StringUtils.isNullOrBlank(creditCardInfoREQDTO.getInputType())) {
            bu.append("<InputType>");
            bu.append(creditCardInfoREQDTO.getInputType());
            bu.append("</InputType>");
        } else {
            bu.append("<InputType></InputType>");
        }
        if (!StringUtils.isNullOrBlank(creditCardInfoREQDTO.getCardId())) {
            bu.append("<CardId>");
            bu.append(creditCardInfoREQDTO.getCardId());
            bu.append("</CardId>");
        } else {
            bu.append("<CardId></CardId>");
        }
        bu.append("</CreditCardInfoRequest>");
        return bu.toString();
    }

    public static CreditCardInfoRESDTO convertGetInfoDPPRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                                               CreditCardInfoREQDTO creditCardInfoREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(creditCardInfoREQDTO.getMsgId() + " CreditCardInfoRESDTO: " + "RES " + xmlResponse);
        CreditCardInfoRESDTO creditCardInfoRESDTO = xmlMapper.readValue(xmlResponse, CreditCardInfoRESDTO.class);

        return creditCardInfoRESDTO;
    }
}
