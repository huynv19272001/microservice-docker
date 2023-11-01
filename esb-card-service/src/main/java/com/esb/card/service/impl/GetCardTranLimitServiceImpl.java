package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.*;
import com.esb.card.dto.cardtranlimit.GetCardTranLimit;
import com.esb.card.dto.cardtranlimit.GetCardTranLimitREQDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.esb.card.service.GetCardTranLimitService;
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
public class GetCardTranLimitServiceImpl implements GetCardTranLimitService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IEsbService esbService;

    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ResponseModel getCardTranLimit(GetCardTranLimitREQDTO getCardTranLimitREQDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(getCardTranLimitREQDTO.getMsgId() + " MSGREQ: " + objectMapper.writeValueAsString(getCardTranLimitREQDTO));
            List<ServiceInfoDTO> serviceInfo = initServiceInfo();
            EsbCardCoreUserInfo esbCardCoreUserInfo = esbService.getCardCoreUser(serviceConfig.getUserCardCore());
            CardREQDTO initCardRequest = initRequest(serviceInfo, getCardTranLimitREQDTO, esbCardCoreUserInfo);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            log.info(getCardTranLimitREQDTO.getMsgId() + " Request GetCardTranLimit: " + objectMapper.writeValueAsString(initCardRequest));

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(initCardRequest), httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(serviceInfo.get(0).getUrlApi() +
                serviceInfo.get(0).getConnectorURL(), request, String.class);

            log.info(getCardTranLimitREQDTO.getMsgId() + " Response GetCardTranLimit: " + response.getBody());

            CardRESDTO cardResponse = objectMapper.readValue(response.getBody(), CardRESDTO.class);
            lpbResCode.setRefCode(cardResponse.getHeader().getHttpStatusCode());
            lpbResCode.setRefDesc(cardResponse.getHeader().getResponseDesc());

            if (cardResponse.getHeader().getHttpStatusCode().equals("0")) {
                lpbResCode.setErrorCode(ErrorMessageEng.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.SUCCESS.description);
                responseModel.setData(convertGetCardTranRESDTO(cardResponse, esbCardCoreUserInfo, getCardTranLimitREQDTO));
            } else {
                lpbResCode.setErrorCode(ErrorMessageEng.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessageEng.NODATA.description);
            }
            responseModel.setResCode(lpbResCode);
            log.info(getCardTranLimitREQDTO.getMsgId() + " Response GetCardTranLimit: " + objectMapper.writeValueAsString(responseModel));

            return responseModel;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(getCardTranLimitREQDTO.getMsgId() + " Exception GetCardTranLimit: " + e);
            lpbResCode.setErrorCode(ErrorMessageEng.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessageEng.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private List<ServiceInfoDTO> initServiceInfo() {
        List<ServiceInfoDTO> serviceInfo = esbService.getServiceInfo(Constant.SERVICE_ID, Constant.GET_CARD_TRAN_LIMIT, Constant.HAS_ROLE);
        return serviceInfo;
    }

    public static CardREQDTO initRequest(List<ServiceInfoDTO> serviceInfo, GetCardTranLimitREQDTO getCardTranLimitREQDTO,
                                                             EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsg(getCardTranLimitREQDTO);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, getCardTranLimitREQDTO, msg);
    }

    public static String buildMsg(GetCardTranLimitREQDTO getCardTranLimitREQDTO) {
        try {
            StringBuilder bu = new StringBuilder();
            bu.append("<GetCardTranLimitRequest>");
            if (!StringUtils.isNullOrBlank(getCardTranLimitREQDTO.getCardId())) {
                bu.append("<CardId>");
                bu.append(getCardTranLimitREQDTO.getCardId());
                bu.append("</CardId>");
            } else {
                bu.append("<CardId></CardId>");
            }
            if (!StringUtils.isNullOrBlank(getCardTranLimitREQDTO.getCif())) {
                bu.append("<Cif>");
                bu.append(getCardTranLimitREQDTO.getCif());
                bu.append("</Cif>");
            } else {
                bu.append("<Cif></Cif>");
            }
            bu.append("</GetCardTranLimitRequest>");
            return bu.toString();

        } catch (Exception e) {
            log.info("Exception buildMsg: " + e);
            return null;
        }
    }

    public static GetCardTranLimit convertGetCardTranRESDTO(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                                                  GetCardTranLimitREQDTO getCardTranLimitREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(getCardTranLimitREQDTO.getMsgId() + " GetCardTranRESDTO: " + "RES " + xmlResponse);
        GetCardTranLimit getCardTranLimit = xmlMapper.readValue(xmlResponse, GetCardTranLimit.class);
        return getCardTranLimit;
    }

    public static CardREQDTO initCardReq(List<ServiceInfoDTO> serviceInfo, EsbCardCoreUserInfo esbCardCoreUserInfo,
                                         GetCardTranLimitREQDTO getCardTranLimitREQDTO, String messageBody) throws Exception {
        Date curDate = new Date();
        CardHeaderDTO cardHeader = new CardHeaderDTO();
        cardHeader.setRequestCode(serviceInfo.get(0).getUDF4() + ESBUtils.formatDate(curDate, ESBUtils.YYYYMMddHHmmssSSSSSS));
        cardHeader.setRequestTranDate(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMdd));
        cardHeader.setRequestDateTime(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMddHHmmssSSSSSS));
        cardHeader.setFunctionCode(serviceInfo.get(0).getUDF3());
        cardHeader.setChannelCode(serviceInfo.get(0).getUDF4());
        cardHeader.setUserName(serviceInfo.get(0).getUDF1());
        cardHeader.setPassword(RSAUtils.genneSHA1(serviceInfo.get(0).getUDF2()));

        log.info(getCardTranLimitREQDTO.getMsgId() + " - RequestCode: " + cardHeader.getRequestCode() + " - FunctionCode: " + cardHeader.getFunctionCode() + " - MessageBody: " + messageBody);
        CardBodyDTO cardBody = new CardBodyDTO();
        cardBody.setContentData(RSAUtils.encryptStr(messageBody, esbCardCoreUserInfo.getCardEsbPublicKey()));

        CardREQDTO cardRequest = new CardREQDTO();
        cardRequest.setHeader(cardHeader);
        cardRequest.setBody(cardBody);
        cardRequest.setSignature(RSAUtils.genneSHA1(messageBody));
        return cardRequest;
    }
}
