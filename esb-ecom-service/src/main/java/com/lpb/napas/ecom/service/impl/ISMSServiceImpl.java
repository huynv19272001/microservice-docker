package com.lpb.napas.ecom.service.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import com.lpb.napas.ecom.dto.FCubsHeaderDTO;
import com.lpb.napas.ecom.dto.FCubsSMSBodyDTO;
import com.lpb.napas.ecom.dto.SMSDTO;
import com.lpb.napas.ecom.model.config.SmsConfig;
import com.lpb.napas.ecom.service.ISMSService;
import com.lpb.napas.ecom.utils.DateUtils;
import com.lpb.napas.ecom.utils.SoapSendSMS;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

@Service
@Log4j2
public class ISMSServiceImpl implements ISMSService {
    @Autowired
    SmsConfig smsConfig;

    @Override
    public ResponseModel getResponseModelSendSMS(Document doc) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        XPath xPath = XPathFactory.newInstance().newXPath();
        String MSGSTAT = "";
        try {
            NodeList nodeList = (NodeList) xPath.compile("//MSGSTAT")
                .evaluate(doc, XPathConstants.NODESET);
            if (nodeList.getLength() > 0
                && nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                MSGSTAT = nodeList.item(0).getTextContent();
            }
            if (MSGSTAT.equals("SUCCESS")) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
        } catch (Exception e) {
            log.info("Exception SMSService: " + e.getMessage());
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    private SMSDTO initDataSendSms(String buildContent, String phoneNumber,
                                   String requestorTransId, String appId) {
        String msgId = DateUtils.getddMMyyHHmmssSS();
        FCubsSMSBodyDTO fCubsBody = FCubsSMSBodyDTO.builder()
            .content(buildContent)
            .mobileNumber(phoneNumber)
            .serviceId(smsConfig.getBodyServiceId())
            .channel(smsConfig.getBodySendInfoChannel())
            .cateGory(smsConfig.getBodySendInfoCategory())
            .branchCode(smsConfig.getBodySendInfoBranchCode())
            .build();
        FCubsHeaderDTO fCubsHeader = FCubsHeaderDTO.builder()
            .msgId(msgId)
            .userId(smsConfig.getHeaderUserId())
            .service(smsConfig.getHeaderService())
            .operation(smsConfig.getHeaderOperation())
            .password(smsConfig.getHeaderPassword())
            .build();

        SMSDTO smsDTO = SMSDTO.builder()
            .fCubsHeaderDTO(fCubsHeader)
            .fCubsSMSBodyDTO(fCubsBody)
            .appId(appId)
            .requestorTransId(requestorTransId)
            .build();

        return smsDTO;
    }

    @Override
    public ResponseModel executeSendSms(String buildContent, String phoneNumber,
                                        String requestorTransId, String appId) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            SOAPMessage soapResponse = SoapSendSMS.callSoapWebServiceFCUBSInternal(smsConfig.getApiUrl(), "",
                initDataSendSms(buildContent, phoneNumber,
                    requestorTransId, appId));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapResponse.writeTo(out);
            String strMsg = new String(out.toByteArray());

            DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(strMsg)));
            ResponseModel dataResponseModel = getResponseModelSendSMS(doc);
            if (dataResponseModel.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
        } catch (Exception e) {
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }
}
