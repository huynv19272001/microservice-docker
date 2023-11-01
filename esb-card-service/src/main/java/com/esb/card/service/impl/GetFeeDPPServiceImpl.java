package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.*;
import com.esb.card.dto.getfeedpp.GetFeeDPPREQDTO;
import com.esb.card.dto.getfeedpp.GetFeeDPPRESDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.GetFeeDPPService;
import com.esb.card.service.IEsbService;
import com.esb.card.utils.ESBUtils;
import com.esb.card.utils.RSAUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.code.ErrorMessageEng;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class GetFeeDPPServiceImpl implements GetFeeDPPService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel getFeeDPP(GetFeeDPPREQDTO getFeeDPPREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(getFeeDPPREQDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(getFeeDPPREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoGetFeeDPP();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = initServiceInfoGetFeeDPPRequest(serviceInfo, getFeeDPPREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(getFeeDPPREQDTO.getMsgId() + " Request GetFeeDPP: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(getFeeDPPREQDTO.getMsgId() + " Response GetFeeDPP: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertGetFeeDPPRESDTO(cardResponse, esbCardCoreUserInfo, getFeeDPPREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessageEng.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(getFeeDPPREQDTO.getMsgId() + " Response GetFeeDPP: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(getFeeDPPREQDTO.getMsgId() + " Exception GetFeeDPP: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private List<ServiceInfoDTO> initServiceInfoGetFeeDPP() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.GET_FEE_DPP, Constant.HAS_ROLE);
        return serviceInfo;
    }

    public static CardREQDTO initServiceInfoGetFeeDPPRequest(List<ServiceInfoDTO> serviceInfo, GetFeeDPPREQDTO getFeeDPPREQDTO,
                                                             EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgGetFeeDPP(getFeeDPPREQDTO);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, getFeeDPPREQDTO, msg);
    }

    public static String buildMsgGetFeeDPP(GetFeeDPPREQDTO getFeeDPPREQDTO) {
        try {
            GetFeeDPPREQDTO buildXml = getFeeDPPREQDTO.builder()
                .cardId(getFeeDPPREQDTO.getCardId())
                .operAmount(getFeeDPPREQDTO.getOperAmount())
                .build();
            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            ObjectMapper objectMapper = new XmlMapper(xmlModule);
            String xml = objectMapper.writeValueAsString(buildXml);
            return xml;
        } catch (Exception e) {
            log.info("Exception buildMsgGetFeeDPP: " + e);
            return null;
        }
    }

    public static GetFeeDPPRESDTO convertGetFeeDPPRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                                         GetFeeDPPREQDTO getFeeDPPREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(getFeeDPPREQDTO.getMsgId() + " GetFeeDPPRESDTO: " + "RES " + xmlResponse);
        GetFeeDPPRESDTO getFeeDPPRESDTO = xmlMapper.readValue(xmlResponse, GetFeeDPPRESDTO.class);
        getFeeDPPRESDTO.setRequestCode(cardResponse.getHeader().getResponseCode());
        return getFeeDPPRESDTO;
    }

    public static CardREQDTO initCardReq(List<ServiceInfoDTO> serviceInfo, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                         GetFeeDPPREQDTO getFeeDPPREQDTO, String messageBody) throws Exception {
        Date curDate = new Date();
        CardHeaderDTO cardHeader = new CardHeaderDTO();
        cardHeader.setRequestCode(getFeeDPPREQDTO.getChannel() + getFeeDPPREQDTO.getClientRequestTime());
        cardHeader.setRequestTranDate(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMdd));
        cardHeader.setRequestDateTime(getFeeDPPREQDTO.getClientRequestTime());
        cardHeader.setFunctionCode(serviceInfo.get(0).getUDF3());
        cardHeader.setChannelCode(getFeeDPPREQDTO.getChannel());
        cardHeader.setUserName(getFeeDPPREQDTO.getUser());
        cardHeader.setPassword(RSAUtils.genneSHA1(serviceInfo.get(0).getUDF2()));

        log.info(getFeeDPPREQDTO.getMsgId() + " - RequestCode: " + cardHeader.getRequestCode() + " - FunctionCode: " + cardHeader.getFunctionCode() + " - MessageBody: " + messageBody);
        CardBodyDTO cardBody = new CardBodyDTO();
        cardBody.setContentData(RSAUtils.encryptStr(messageBody, esbCardCoreUserInfo.getCardEsbPublicKey()));

        CardREQDTO cardRequest = new CardREQDTO();
        cardRequest.setHeader(cardHeader);
        cardRequest.setBody(cardBody);
        cardRequest.setSignature(RSAUtils.genneSHA1(messageBody));
        return cardRequest;
    }
}
