package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.*;
import com.esb.card.dto.getlisttrans.GetListTransREQDTO;
import com.esb.card.dto.getlisttrans.GetListTransRESDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.GetListTransService;
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
public class GetListTransServiceImpl implements GetListTransService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel getListTrans(GetListTransREQDTO getListTransREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(getListTransREQDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(getListTransREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfoGetListTrans();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = initServiceInfoGetListTransRequest(serviceInfo, getListTransREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(getListTransREQDTO.getMsgId() + " Request GetListTrans: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(getListTransREQDTO.getMsgId() + " Response GetListTrans: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertGetListTransRESDTO(cardResponse, esbCardCoreUserInfo, getListTransREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessageEng.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(getListTransREQDTO.getMsgId() + " Response GetListTrans: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(getListTransREQDTO.getMsgId() + " Exception GetListTrans: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private List<ServiceInfoDTO> initServiceInfoGetListTrans() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.LIST_TRANS, Constant.HAS_ROLE);
        return serviceInfo;
    }

    public static CardREQDTO initServiceInfoGetListTransRequest(List<ServiceInfoDTO> serviceInfo, GetListTransREQDTO getListTransREQDTO,
                                                                EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgGetListTrans(getListTransREQDTO);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, getListTransREQDTO, msg);
    }

    public static String buildMsgGetListTrans(GetListTransREQDTO getListTransREQDTO) {
        try {
            StringBuilder bu = new StringBuilder();
            bu.append("<GetListTransRequest>");
            if (!StringUtils.isNullOrBlank(getListTransREQDTO.getTypeInput())) {
                bu.append("<TypeInput>");
                bu.append(getListTransREQDTO.getTypeInput());
                bu.append("</TypeInput>");
            } else {
                bu.append("<TypeInput></TypeInput>");
            }
            if (!StringUtils.isNullOrBlank(getListTransREQDTO.getCardId())) {
                bu.append("<CardId>");
                bu.append(getListTransREQDTO.getCardId());
                bu.append("</CardId>");
            } else {
                bu.append("<CardId></CardId>");
            }
            if (!StringUtils.isNullOrBlank(getListTransREQDTO.getCif())) {
                bu.append("<Cif>");
                bu.append(getListTransREQDTO.getCif());
                bu.append("</Cif>");
            } else {
                bu.append("<Cif></Cif>");
            }
            if (!StringUtils.isNullOrBlank(getListTransREQDTO.getFromDate())) {
                bu.append("<FromDate>");
                bu.append(getListTransREQDTO.getFromDate());
                bu.append("</FromDate>");
            } else {
                bu.append("<FromDate></FromDate>");
            }
            if (!StringUtils.isNullOrBlank(getListTransREQDTO.getToDate())) {
                bu.append("<ToDate>");
                bu.append(getListTransREQDTO.getToDate());
                bu.append("</ToDate>");
            } else {
                bu.append("<ToDate></ToDate>");
            }
            if (!StringUtils.isNullOrBlank(getListTransREQDTO.getMinAmount())) {
                bu.append("<MinAmount>");
                bu.append(getListTransREQDTO.getMinAmount());
                bu.append("</MinAmount>");
            } else {
                bu.append("<MinAmount></MinAmount>");
            }
            bu.append("</GetListTransRequest>");

            return bu.toString();
        } catch (Exception e) {
            log.info("Exception buildMsgGetListTrans: " + e);
            return null;
        }
    }

    public static GetListTransRESDTO convertGetListTransRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                                               GetListTransREQDTO getListTransREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(getListTransREQDTO.getMsgId() + " GetListTransRESDTO: " + "RES " + xmlResponse);
        GetListTransRESDTO getListTransRESDTO = xmlMapper.readValue(xmlResponse, GetListTransRESDTO.class);
        getListTransRESDTO.setRequestCode(cardResponse.getHeader().getResponseCode());
        getListTransRESDTO.setCif(getListTransRESDTO.getTransactionRESDTO().get(0).getCif());
        getListTransRESDTO.setCardId(getListTransRESDTO.getTransactionRESDTO().get(0).getCardId());
        return getListTransRESDTO;
    }

    public static CardREQDTO initCardReq(List<ServiceInfoDTO> serviceInfo, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                         GetListTransREQDTO getListTransREQDTO, String messageBody) throws Exception {
        Date curDate = new Date();
        CardHeaderDTO cardHeader = new CardHeaderDTO();
        cardHeader.setRequestCode(getListTransREQDTO.getChannel() + getListTransREQDTO.getClientRequestTime());
        cardHeader.setRequestTranDate(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMdd));
        cardHeader.setRequestDateTime(getListTransREQDTO.getClientRequestTime());
        cardHeader.setFunctionCode(serviceInfo.get(0).getUDF3());
        cardHeader.setChannelCode(getListTransREQDTO.getChannel());
        cardHeader.setUserName(getListTransREQDTO.getUser());
        cardHeader.setPassword(RSAUtils.genneSHA1(serviceInfo.get(0).getUDF2()));

        log.info(getListTransREQDTO.getMsgId() + " - RequestCode: " + cardHeader.getRequestCode() + " - FunctionCode: " + cardHeader.getFunctionCode() + " - MessageBody: " + messageBody);
        CardBodyDTO cardBody = new CardBodyDTO();
        cardBody.setContentData(RSAUtils.encryptStr(messageBody, esbCardCoreUserInfo.getCardEsbPublicKey()));

        CardREQDTO cardRequest = new CardREQDTO();
        cardRequest.setHeader(cardHeader);
        cardRequest.setBody(cardBody);
        cardRequest.setSignature(RSAUtils.genneSHA1(messageBody));
        return cardRequest;
    }
}

