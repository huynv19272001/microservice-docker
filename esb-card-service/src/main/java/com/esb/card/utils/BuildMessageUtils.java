package com.esb.card.utils;

import com.esb.card.dto.*;
import com.esb.card.dto.availinfocard.AvailInfoCardREQDTO;
import com.esb.card.dto.availinfocard.AvailInfoCardRESDTO;
import com.esb.card.dto.debitcardinfo.DebitCardInfoREQDTO;
import com.esb.card.dto.debitcardinfo.DebitCardInfoRESDTO;
import com.esb.card.dto.listaccount.ListAccInfoRESDTO;
import com.esb.card.dto.listaccount.ListAccountInfoRESDTO;
import com.esb.card.dto.listcardinfo.CardInfoDTO;
import com.esb.card.dto.listcardinfo.CardInfoREQDTO;
import com.esb.card.dto.listcardinfo.CardInfoRESDTO;
import com.esb.card.dto.telcoRequest.EsbTelCoBodyDTO;
import com.esb.card.dto.telcoRequest.EsbTelCoHeaderDTO;
import com.esb.card.dto.telcoRequest.EsbTelCoReqDTO;
import com.esb.card.dto.telcoRequest.EsbTelCoRequestDTO;
import com.esb.card.dto.updatelimitpayment.UpdateLimitPaymentREQDTO;
import com.esb.card.dto.updatelimitpayment.UpdateLimitPaymentRESDTO;
import com.esb.card.model.EsbCardCoreUserInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.utils.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tudv1 on 2022-04-28
 */
@Component
@Log4j2
public class BuildMessageUtils {

    @Autowired
    LogicUtils logicUtils;

    public String buildTelcoMsgXml(EsbTelCoReqDTO esbTelCoRequestDTO, String phoneNo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<UpdateTelCodeRequest>");
//        stringBuilder.append("<CardId>%s</CardId>");
        stringBuilder.append("<PhoneNo>%s</PhoneNo>");
        stringBuilder.append("<TelCode>%s</TelCode>");
        stringBuilder.append("</UpdateTelCodeRequest>");
        return String.format(stringBuilder.toString(), phoneNo, esbTelCoRequestDTO.getTelCode());
    }

    public EsbTelCoRequestDTO buildTelcoRequest(ServiceInfo serviceInfo, EsbCardCoreUserInfo esbCardCoreUserInfo, String messageBody) throws Exception {
        Date curDate = new Date();
        EsbTelCoHeaderDTO cardHeader = new EsbTelCoHeaderDTO();
        cardHeader.setRequestCode(serviceInfo.getUdf4() + ESBUtils.formatDate(curDate, ESBUtils.YYYYMMddHHmmssSSSSSS));
        cardHeader.setRequestTranDate(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMdd));
        cardHeader.setRequestDateTime(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMddHHmmssSSSSSS));
        cardHeader.setFuncionCode(serviceInfo.getUdf3());
        cardHeader.setChannelCode(serviceInfo.getUdf4());
        cardHeader.setUserName(serviceInfo.getUdf1());
        cardHeader.setPassWord(RSAUtils.genneSHA1(serviceInfo.getUdf2()));

        log.info(cardHeader.getRequestCode() + "_" + cardHeader.getFuncionCode() + "_" + messageBody);
        EsbTelCoBodyDTO cardBody = new EsbTelCoBodyDTO();
        cardBody.setContentData(RSAUtils.encryptStr(messageBody, esbCardCoreUserInfo.getCardEsbPublicKey()));

        EsbTelCoRequestDTO cardRequest = new EsbTelCoRequestDTO();
        cardRequest.setHeader(cardHeader);
        cardRequest.setBody(cardBody);
        cardRequest.setSignature(RSAUtils.genneSHA1(messageBody));
        return cardRequest;
    }

    public static String buildMsgCardInfo(CardInfoREQDTO cardInfoRequest) {
        StringBuilder bu = new StringBuilder();
        bu.append("<CardInfoRequest>");
        if (!StringUtils.isNullOrBlank(cardInfoRequest.getClientCode())) {
            bu.append("<ClientCode>");
            bu.append(cardInfoRequest.getClientCode());
            bu.append("</ClientCode>");
        } else {
            bu.append("<ClientCode></ClientCode>");
        }
        if (!StringUtils.isNullOrBlank(cardInfoRequest.getCardID())) {
            bu.append("<CardID>");
            bu.append(cardInfoRequest.getCardID());
            bu.append("</CardID>");
        } else {
            bu.append("<CardID></CardID>");
        }
        if (!StringUtils.isNullOrBlank(cardInfoRequest.getCardNumber())) {
            bu.append("<CardNumber>");
            bu.append(cardInfoRequest.getCardNumber());
            bu.append("</CardNumber>");
        } else {
            bu.append("<CardNumber></CardNumber>");
        }
        if (!StringUtils.isNullOrBlank(cardInfoRequest.getCmnd())) {
            bu.append("<CMND>");
            bu.append(cardInfoRequest.getCmnd());
            bu.append("</CMND>");
        } else {
            bu.append("<CMND></CMND>");
        }
        if (!StringUtils.isNullOrBlank(cardInfoRequest.getInputType())) {
            bu.append("<InputType>");
            bu.append(cardInfoRequest.getInputType());
            bu.append("</InputType>");
        } else {
            bu.append("<InputType></InputType>");
        }
        if (!StringUtils.isNullOrBlank(cardInfoRequest.getPassport())) {
            bu.append("<PASSPORT>");
            bu.append(cardInfoRequest.getPassport());
            bu.append("</PASSPORT>");
        } else {
            bu.append("<PASSPORT></PASSPORT>");
        }
        bu.append("</CardInfoRequest>");
        return bu.toString();
    }

    public static String buildMsgDebitCardInfo(DebitCardInfoREQDTO debitCardInfoREQDTO) {
        StringBuilder bu = new StringBuilder();
        bu.append("<DebitCardInfoRequest>");
        bu.append("<CardNumber>%s</CardNumber>");
        bu.append("<CardID>%s</CardID>");
        bu.append("<InputType>%s</InputType>");
        bu.append("</DebitCardInfoRequest>");
        return String.format(bu.toString(), debitCardInfoREQDTO.getCardNumber(), debitCardInfoREQDTO.getCardID(), debitCardInfoREQDTO.getInputType());
    }

    public static String buildMsgListAcc(DebitCardInfoREQDTO debitCardInfoREQDTO) {
        StringBuilder bu = new StringBuilder();
        bu.append("<GetListAccountRequest>");
        bu.append("<CardId>%s</CardId>");
        bu.append("</GetListAccountRequest>");
        return String.format(bu.toString(), debitCardInfoREQDTO.getCardID());
    }

    public static String buildAvailInfoCard(AvailInfoCardREQDTO availInfoCardREQDTO) {
        StringBuilder bu = new StringBuilder();
        bu.append("<AvailInfoCardRequest>");
        if (!StringUtils.isNullOrBlank(availInfoCardREQDTO.getCardId())) {
            bu.append("<CardId>");
            bu.append(availInfoCardREQDTO.getCardId());
            bu.append("</CardId>");
        } else {
            bu.append("<CardId></CardId>");
        }
        if (!StringUtils.isNullOrBlank(availInfoCardREQDTO.getCardNumber())) {
            bu.append("<CardNumber>");
            bu.append(availInfoCardREQDTO.getCardNumber());
            bu.append("</CardNumber>");
        } else {
            bu.append("<CardNumber></CardNumber>");
        }
        if (!StringUtils.isNullOrBlank(availInfoCardREQDTO.getInputType())) {
            bu.append("<InputType>");
            bu.append(availInfoCardREQDTO.getInputType());
            bu.append("</InputType>");
        } else {
            bu.append("<InputType></InputType>");
        }
        bu.append("</AvailInfoCardRequest>");
        return bu.toString();
    }

    public static String buildMsgUpdateLimitPayment(UpdateLimitPaymentREQDTO updateLimitPaymentREQDTO) {
        try {
            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            ObjectMapper objectMapper = new XmlMapper(xmlModule);
            String xml = objectMapper.writeValueAsString(updateLimitPaymentREQDTO);
            return xml;
        } catch (Exception e) {
            log.info("Exception buildMsgUpdateLimitPayment: " + e);
            return null;
        }
    }

    public static CardREQDTO initAvailInfoCardRequest(List<ServiceInfoDTO> serviceInfo, AvailInfoCardREQDTO availInfoCardREQDTO, EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildAvailInfoCard(availInfoCardREQDTO);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, availInfoCardREQDTO.getAppId(), msg);
    }

    public static CardREQDTO initDataCardInfoRequest(List<ServiceInfoDTO> serviceInfo, CardInfoREQDTO cardInfoRequest, EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgCardInfo(cardInfoRequest);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, cardInfoRequest.getAppId(), msg);
    }

    public static CardREQDTO initDataDebitCardInfoRequest(List<ServiceInfoDTO> serviceInfo, DebitCardInfoREQDTO debitCardInfoREQDTO, EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgDebitCardInfo(debitCardInfoREQDTO);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, debitCardInfoREQDTO.getAppId(), msg);
    }

    public static CardREQDTO initDataListAccRequest(List<ServiceInfoDTO> serviceInfo, DebitCardInfoREQDTO debitCardInfoREQDTO, EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgListAcc(debitCardInfoREQDTO);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, debitCardInfoREQDTO.getAppId(), msg);
    }

    public static CardREQDTO initUpdateLimitPaymentRequest(List<ServiceInfoDTO> serviceInfo, UpdateLimitPaymentREQDTO updateLimitPaymentREQDTO, EsbCardCoreUserInfo esbCardCoreUserInfo) throws Exception {
        String msg = buildMsgUpdateLimitPayment(updateLimitPaymentREQDTO);
        return initCardReq(serviceInfo, esbCardCoreUserInfo, updateLimitPaymentREQDTO.getMsgId(), msg);
    }

    public static CardREQDTO initCardReq(List<ServiceInfoDTO> serviceInfo, EsbCardCoreUserInfo esbCardCoreUserInfo, String appId, String messageBody) throws Exception {
        Date curDate = new Date();
        CardHeaderDTO cardHeader = new CardHeaderDTO();
        cardHeader.setRequestCode(serviceInfo.get(0).getUDF4() + ESBUtils.formatDate(curDate, ESBUtils.YYYYMMddHHmmssSSSSSS));
        cardHeader.setRequestTranDate(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMdd));
        cardHeader.setRequestDateTime(ESBUtils.formatDate(curDate, ESBUtils.YYYYMMddHHmmssSSSSSS));
        cardHeader.setFunctionCode(serviceInfo.get(0).getUDF3());
        cardHeader.setChannelCode(serviceInfo.get(0).getUDF4());
        cardHeader.setUserName(serviceInfo.get(0).getUDF1());
        cardHeader.setPassword(RSAUtils.genneSHA1(serviceInfo.get(0).getUDF2()));

        log.info(appId + " _RequestCode: " + cardHeader.getRequestCode() + " _FunctionCode: " + cardHeader.getFunctionCode() + " _MessageBody: " + messageBody);
        CardBodyDTO cardBody = new CardBodyDTO();
        log.info(appId + " _RSA: " + esbCardCoreUserInfo.getCardEsbPublicKey());
        cardBody.setContentData(RSAUtils.encryptStr(messageBody, esbCardCoreUserInfo.getCardEsbPublicKey()));

        CardREQDTO cardRequest = new CardREQDTO();
        cardRequest.setHeader(cardHeader);
        cardRequest.setBody(cardBody);
        cardRequest.setSignature(RSAUtils.genneSHA1(messageBody));
        return cardRequest;
    }

    public static ArrayList<CardInfoDTO> convertCardToCardInfo(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo, CardInfoREQDTO cardInfoREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());
        log.info(cardInfoREQDTO.getAppId() + " ListCardInfo: " + "RES " + xmlResponse);
        CardInfoRESDTO cardInfoResponse = xmlMapper.readValue(xmlResponse, CardInfoRESDTO.class);

        return cardInfoResponse.getCards().getCardInfo();
    }

    public static DebitCardInfoRESDTO convertCardToDebitCardInfo(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo, DebitCardInfoREQDTO debitCardInfoREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());
        log.info(debitCardInfoREQDTO.getAppId() + " DebitCardInfo: " + "RES " + xmlResponse);
        DebitCardInfoRESDTO debitCardInfoRESDTO = xmlMapper.readValue(xmlResponse, DebitCardInfoRESDTO.class);

        return debitCardInfoRESDTO;
    }

    public static ListAccountInfoRESDTO convertListAccInfo(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo, DebitCardInfoREQDTO debitCardInfoREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(debitCardInfoREQDTO.getAppId() + " ListAccInfo: " + "RES " + xmlResponse);

        ListAccInfoRESDTO listAccInfoRESDTO = xmlMapper.readValue(xmlResponse, ListAccInfoRESDTO.class);
        ListAccountInfoRESDTO accountInfoRESDTO = ListAccountInfoRESDTO.builder().CardId(listAccInfoRESDTO.getCardId()).EmbossedName(listAccInfoRESDTO.getEmbossedName()).CardType(listAccInfoRESDTO.getCardType()).AccountNumber(listAccInfoRESDTO.getListAccount().get(0).getAccountNumber()).build();

        return accountInfoRESDTO;
    }

    public static AvailInfoCardRESDTO convertAvailInfoCard(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo, AvailInfoCardREQDTO AvailInfoCardREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(AvailInfoCardREQDTO.getAppId() + " AvailInfoCard: " + "RES " + xmlResponse);
        AvailInfoCardRESDTO availInfoCardRESDTO = xmlMapper.readValue(xmlResponse, AvailInfoCardRESDTO.class);

        return availInfoCardRESDTO;
    }

    public static UpdateLimitPaymentRESDTO convertUpdateLimitPayment(CardRESDTO cardResponse, EsbCardCoreUserInfo esbCardCoreUserInfo, UpdateLimitPaymentREQDTO updateLimitPaymentREQDTO) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);

        xmlMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        String xmlResponse = RSAUtils.decryptStr(cardResponse.getBody().getContentData(), esbCardCoreUserInfo.getEsbCardPrivateKey());

        log.info(updateLimitPaymentREQDTO.getMsgId() + " UpdateLimitPayment: " + "RES " + xmlResponse);
        UpdateLimitPaymentRESDTO updateLimitPaymentRESDTO = xmlMapper.readValue(xmlResponse, UpdateLimitPaymentRESDTO.class);

        return updateLimitPaymentRESDTO;
    }

}
