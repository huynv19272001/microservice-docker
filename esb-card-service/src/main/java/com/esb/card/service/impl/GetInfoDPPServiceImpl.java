package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.*;
import com.esb.card.dto.getinfodpp.GetInfoDPPREQDTO;
import com.esb.card.dto.getinfodpp.GetInfoDPPRESDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.GetInfoDPPService;
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
public class GetInfoDPPServiceImpl implements GetInfoDPPService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel getInfoDPP(GetInfoDPPREQDTO getInfoDPPREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(getInfoDPPREQDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(getInfoDPPREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoGetInfoDPP();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = initServiceInfoGetInfoDPPRequest(serviceInfo, getInfoDPPREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(getInfoDPPREQDTO.getMsgId() + " Request GetInfoDPP: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(getInfoDPPREQDTO.getMsgId() + " Response GetInfoDPP: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertGetInfoDPPRESDTO(cardResponse, esbCardCoreUserInfo, getInfoDPPREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessageEng.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(getInfoDPPREQDTO.getMsgId() + " Response GetInfoDPP: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(getInfoDPPREQDTO.getMsgId() + " Exception GetInfoDPP: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private List<ServiceInfoDTO> initServiceInfoGetInfoDPP() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.GET_INFO_DPP, Constant.HAS_ROLE);
        return serviceInfo;
    }

    public static CardREQDTO initServiceInfoGetInfoDPPRequest(List<ServiceInfoDTO> serviceInfo, GetInfoDPPREQDTO getInfoDPPREQDTO,
                                                              EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgGetInfoDPPR(getInfoDPPREQDTO);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, getInfoDPPREQDTO, msg);
    }

    public static String buildMsgGetInfoDPPR(GetInfoDPPREQDTO getInfoDPPREQDTO) {
        try {
            GetInfoDPPREQDTO buildXml = GetInfoDPPREQDTO.builder()
                .cardId(getInfoDPPREQDTO.getCardId())
                .cif(getInfoDPPREQDTO.getCif())
                .operId(getInfoDPPREQDTO.getOperId())
                .build();
            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            ObjectMapper objectMapper = new XmlMapper(xmlModule);
            String xml = objectMapper.writeValueAsString(buildXml);
            return xml;
        } catch (Exception e) {
            log.info("Exception buildMsgGetInfoDPPR: " + e);
            return null;
        }
    }

    public static GetInfoDPPRESDTO convertGetInfoDPPRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                                           GetInfoDPPREQDTO getInfoDPPREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(getInfoDPPREQDTO.getMsgId() + " GetInfoDPPRESDTO: " + "RES " + xmlResponse);
        GetInfoDPPRESDTO getInfoDPPRESDTO = xmlMapper.readValue(xmlResponse, GetInfoDPPRESDTO.class);
        getInfoDPPRESDTO.setRequestCode(cardResponse.getHeader().getResponseCode());

        return getInfoDPPRESDTO;
    }

    public static CardREQDTO initCardReq(List<ServiceInfoDTO> serviceInfo, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                         GetInfoDPPREQDTO getInfoDPPREQDTO, String messageBody) throws Exception {
        Date curDate = new Date();
        CardHeaderDTO cardHeader = new CardHeaderDTO();
        cardHeader.setRequestCode(getInfoDPPREQDTO.getChannel() + getInfoDPPREQDTO.getClientRequestTime());
        cardHeader.setRequestTranDate(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMdd));
        cardHeader.setRequestDateTime(getInfoDPPREQDTO.getClientRequestTime());
        cardHeader.setFunctionCode(serviceInfo.get(0).getUDF3());
        cardHeader.setChannelCode(getInfoDPPREQDTO.getChannel());
        cardHeader.setUserName(getInfoDPPREQDTO.getUser());
        cardHeader.setPassword(RSAUtils.genneSHA1(serviceInfo.get(0).getUDF2()));

        log.info(getInfoDPPREQDTO.getMsgId() + " - RequestCode: " + cardHeader.getRequestCode() + " - FunctionCode: " + cardHeader.getFunctionCode() + " - MessageBody: " + messageBody);
        CardBodyDTO cardBody = new CardBodyDTO();
        cardBody.setContentData(RSAUtils.encryptStr(messageBody, esbCardCoreUserInfo.getCardEsbPublicKey()));

        CardREQDTO cardRequest = new CardREQDTO();
        cardRequest.setHeader(cardHeader);
        cardRequest.setBody(cardBody);
        cardRequest.setSignature(RSAUtils.genneSHA1(messageBody));
        return cardRequest;
    }
}
