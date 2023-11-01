package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.*;
import com.esb.card.dto.canceldpp.CancelDppREQDTO;
import com.esb.card.dto.canceldpp.CancelDppRESDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.CancelDppService;
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

import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class CancelDppServiceImpl implements CancelDppService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel cancelDpp(CancelDppREQDTO cancelDppREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(cancelDppREQDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(cancelDppREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoCancelDpp();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = initServiceInfoCancelDppRequest(serviceInfo, cancelDppREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(cancelDppREQDTO.getMsgId() + " Request CancelDpp: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(cancelDppREQDTO.getMsgId() + " Response CancelDpp: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertCancelDppRESDTO(cardResponse, esbCardCoreUserInfo, cancelDppREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(cancelDppREQDTO.getMsgId() + " Response CancelDpp: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            log.info(cancelDppREQDTO.getMsgId() + " Exception CancelDpp: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private List<ServiceInfoDTO> initServiceInfoCancelDpp() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.CANCEL_DPP, Constant.HAS_ROLE);
        return serviceInfo;
    }

    public static CardREQDTO initServiceInfoCancelDppRequest(List<ServiceInfoDTO> serviceInfo, CancelDppREQDTO cancelDppREQDTO,
                                                             EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgCancelDpp(cancelDppREQDTO);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, cancelDppREQDTO, msg);
    }

    public static String buildMsgCancelDpp(CancelDppREQDTO cancelDppREQDTO) {
        StringBuilder bu = new StringBuilder();
        bu.append("<CancelDppRequest>");
        if (!StringUtils.isNullOrBlank(cancelDppREQDTO.getCardId())) {
            bu.append("<CardId>");
            bu.append(cancelDppREQDTO.getCardId());
            bu.append("</CardId>");
        } else {
            bu.append("<CardId></CardId>");
        }
        if (!StringUtils.isNullOrBlank(cancelDppREQDTO.getCif())) {
            bu.append("<Cif>");
            bu.append(cancelDppREQDTO.getCif());
            bu.append("</Cif>");
        } else {
            bu.append("<Cif></Cif>");
        }
        if (!StringUtils.isNullOrBlank(cancelDppREQDTO.getOperId())) {
            bu.append("<OperId>");
            bu.append(cancelDppREQDTO.getOperId());
            bu.append("</OperId>");
        } else {
            bu.append("<OperId></OperId>");
        }
        bu.append("</CancelDppRequest>");
        return bu.toString();
    }

    public static CancelDppRESDTO convertCancelDppRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                                         CancelDppREQDTO cancelDppREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(cancelDppREQDTO.getMsgId() + " CancelDppRESDTO: " + "RES " + xmlResponse);
        CancelDppRESDTO cancelDppRESDTO = xmlMapper.readValue(xmlResponse, CancelDppRESDTO.class);
        cancelDppRESDTO.setRequestCode(cardResponse.getHeader().getResponseCode());
        return cancelDppRESDTO;
    }

    public static CardREQDTO initCardReq(List<ServiceInfoDTO> serviceInfo, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                         CancelDppREQDTO cancelDppREQDTO, String messageBody) throws Exception {
        Date curDate = new Date();
        CardHeaderDTO cardHeader = new CardHeaderDTO();
        cardHeader.setRequestCode(cancelDppREQDTO.getChannel() + cancelDppREQDTO.getClientRequestTime());
        cardHeader.setRequestTranDate(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMdd));
        cardHeader.setRequestDateTime(cancelDppREQDTO.getClientRequestTime());
        cardHeader.setFunctionCode(serviceInfo.get(0).getUDF3());
        cardHeader.setChannelCode(cancelDppREQDTO.getChannel());
        cardHeader.setUserName(cancelDppREQDTO.getUser());
        cardHeader.setPassword(RSAUtils.genneSHA1(serviceInfo.get(0).getUDF2()));

        log.info(cancelDppREQDTO.getMsgId() + " - RequestCode: " + cardHeader.getRequestCode() + " - FunctionCode: " + cardHeader.getFunctionCode() + " - MessageBody: " + messageBody);
        CardBodyDTO cardBody = new CardBodyDTO();
        cardBody.setContentData(RSAUtils.encryptStr(messageBody, esbCardCoreUserInfo.getCardEsbPublicKey()));

        CardREQDTO cardRequest = new CardREQDTO();
        cardRequest.setHeader(cardHeader);
        cardRequest.setBody(cardBody);
        cardRequest.setSignature(RSAUtils.genneSHA1(messageBody));
        return cardRequest;
    }
}
