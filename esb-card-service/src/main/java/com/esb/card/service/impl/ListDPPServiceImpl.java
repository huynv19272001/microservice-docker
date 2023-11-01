package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.*;
import com.esb.card.dto.getlistdpp.GetListDPPREQDTO;
import com.esb.card.dto.getlistdpp.GetListDPPRESDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.IEsbService;
import com.esb.card.service.ListDPPService;
import com.esb.card.utils.ESBUtils;
import com.esb.card.utils.RSAUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.StringUtils;
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
public class ListDPPServiceImpl implements ListDPPService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel getListDPP(GetListDPPREQDTO getListDPPREQ) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(getListDPPREQ.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(getListDPPREQ));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoGetListDPP();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = initServiceInfoGetListDPPRequest(serviceInfo, getListDPPREQ, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(getListDPPREQ.getMsgId() + " Request GetListDPP: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(getListDPPREQ.getMsgId() + " Response GetListDPP: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertGetListDPPRESDTO(cardResponse, esbCardCoreUserInfo, getListDPPREQ));
            } else {
                lpbResCode.setErrorCode(ErrorMessageEng.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(getListDPPREQ.getMsgId() + " Response GetListDPP: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(getListDPPREQ.getMsgId() + " Exception GetListDPP: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private List<ServiceInfoDTO> initServiceInfoGetListDPP() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.LIST_DPP, Constant.HAS_ROLE);
        return serviceInfo;
    }

    public static CardREQDTO initServiceInfoGetListDPPRequest(List<ServiceInfoDTO> serviceInfo, GetListDPPREQDTO getListDPPREQ,
                                                              EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgGetListDPP(getListDPPREQ);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, getListDPPREQ, msg);
    }

    public static String buildMsgGetListDPP(GetListDPPREQDTO getListDPPREQ) {
        try {
            StringBuilder bu = new StringBuilder();
            bu.append("<GetListDppRequest>");
            if (!StringUtils.isNullOrBlank(getListDPPREQ.getTypeInput())) {
                bu.append("<TypeInput>");
                bu.append(getListDPPREQ.getTypeInput());
                bu.append("</TypeInput>");
            } else {
                bu.append("<TypeInput></TypeInput>");
            }
            if (!StringUtils.isNullOrBlank(getListDPPREQ.getCardId())) {
                bu.append("<CardId>");
                bu.append(getListDPPREQ.getCardId());
                bu.append("</CardId>");
            } else {
                bu.append("<CardId></CardId>");
            }
            if (!StringUtils.isNullOrBlank(getListDPPREQ.getCif())) {
                bu.append("<Cif>");
                bu.append(getListDPPREQ.getCif());
                bu.append("</Cif>");
            } else {
                bu.append("<Cif></Cif>");
            }
            bu.append("</GetListDppRequest>");
            return bu.toString();
        } catch (Exception e) {
            log.info("Exception buildMsgGetListDPP: " + e);
            return null;
        }
    }

    public static GetListDPPRESDTO convertGetListDPPRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                                           GetListDPPREQDTO getListDPPREQ) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(getListDPPREQ.getMsgId() + " GetListDPPRESDTO: " + "RES " + xmlResponse);
        GetListDPPRESDTO getListDPPRESDTO = xmlMapper.readValue(xmlResponse, GetListDPPRESDTO.class);
        getListDPPRESDTO.setRequestCode(cardResponse.getHeader().getResponseCode());
        getListDPPRESDTO.setCif(getListDPPREQ.getCif());
        getListDPPRESDTO.setCardId(getListDPPREQ.getCardId());

        return getListDPPRESDTO;
    }

    public static CardREQDTO initCardReq(List<ServiceInfoDTO> serviceInfo, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                         GetListDPPREQDTO getListDPPREQDTO, String messageBody) throws Exception {
        Date curDate = new Date();
        CardHeaderDTO cardHeader = new CardHeaderDTO();
        cardHeader.setRequestCode(getListDPPREQDTO.getChannel() + getListDPPREQDTO.getClientRequestTime());
        cardHeader.setRequestTranDate(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMdd));
        cardHeader.setRequestDateTime(getListDPPREQDTO.getClientRequestTime());
        cardHeader.setFunctionCode(serviceInfo.get(0).getUDF3());
        cardHeader.setChannelCode(getListDPPREQDTO.getChannel());
        cardHeader.setUserName(getListDPPREQDTO.getUser());
        cardHeader.setPassword(RSAUtils.genneSHA1(serviceInfo.get(0).getUDF2()));

        log.info(getListDPPREQDTO.getMsgId() + " - RequestCode: " + cardHeader.getRequestCode() + " - FunctionCode: " + cardHeader.getFunctionCode() + " - MessageBody: " + messageBody);
        CardBodyDTO cardBody = new CardBodyDTO();
        cardBody.setContentData(RSAUtils.encryptStr(messageBody, esbCardCoreUserInfo.getCardEsbPublicKey()));

        CardREQDTO cardRequest = new CardREQDTO();
        cardRequest.setHeader(cardHeader);
        cardRequest.setBody(cardBody);
        cardRequest.setSignature(RSAUtils.genneSHA1(messageBody));
        return cardRequest;
    }
}

