package com.lpb.napas.ecom.utils;

import com.lpb.napas.ecom.dto.SMSDTO;
import lombok.extern.log4j.Log4j2;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.lpb.napas.ecom.common.Constant;

@Log4j2
public class SoapSendSMS {
    private static void createSoapEnvelopeFCUBSInternal(SOAPMessage soapMessage, SMSDTO smsDTO) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String namespaceFCub = "fcub";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(namespaceFCub, Constant.NAME_SPACE_FCUB_EXTERNAL_SERVICE);

        //SOAP Header
        SOAPHeader soapHeader = envelope.getHeader();

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement EXTERNALSERVICE_REQ = soapBody.addChildElement("EXTERNALSERVICE_REQ", namespaceFCub);
        SOAPElement F_CUBS_HEADER = EXTERNALSERVICE_REQ.addChildElement("FCUBS_HEADER");
        if (smsDTO.getFCubsHeaderDTO().getSource() != null
            && !smsDTO.getFCubsHeaderDTO().getSource().trim().isEmpty()) {
            SOAPElement SOURCE = F_CUBS_HEADER.addChildElement("SOURCE");
            SOURCE.addTextNode(smsDTO.getFCubsHeaderDTO().getSource());
        }
        if (smsDTO.getFCubsHeaderDTO().getUbsComp() != null
            && !smsDTO.getFCubsHeaderDTO().getUbsComp().trim().isEmpty()) {
            SOAPElement UBSCOMP = F_CUBS_HEADER.addChildElement("UBSCOMP");
            UBSCOMP.addTextNode(smsDTO.getFCubsHeaderDTO().getUbsComp());
        }
        if (smsDTO.getFCubsHeaderDTO().getReferenceNo() != null
            && !smsDTO.getFCubsHeaderDTO().getReferenceNo().trim().isEmpty()) {
            SOAPElement REFERENCE_NO = F_CUBS_HEADER.addChildElement("REFERENCE_NO");
            REFERENCE_NO.addTextNode(smsDTO.getFCubsHeaderDTO().getReferenceNo());
        }
        if (smsDTO.getFCubsHeaderDTO().getMsgId() != null
            && !smsDTO.getFCubsHeaderDTO().getMsgId().trim().isEmpty()) {
            SOAPElement MSG_ID = F_CUBS_HEADER.addChildElement("MSGID");
            MSG_ID.addTextNode(smsDTO.getFCubsHeaderDTO().getMsgId());
        }
        if (smsDTO.getFCubsHeaderDTO().getCorrelId() != null
            && !smsDTO.getFCubsHeaderDTO().getCorrelId().trim().isEmpty()) {
            SOAPElement CORRELID = F_CUBS_HEADER.addChildElement("CORRELID");
            CORRELID.addTextNode(smsDTO.getFCubsHeaderDTO().getCorrelId());
        }
        if (smsDTO.getFCubsHeaderDTO().getUserId() != null
            && !smsDTO.getFCubsHeaderDTO().getUserId().trim().isEmpty()) {
            SOAPElement USERID = F_CUBS_HEADER.addChildElement("USERID");
            USERID.addTextNode(smsDTO.getFCubsHeaderDTO().getUserId());
        }
        if (smsDTO.getFCubsHeaderDTO().getBranch() != null
            && !smsDTO.getFCubsHeaderDTO().getBranch().trim().isEmpty()) {
            SOAPElement BRANCH = F_CUBS_HEADER.addChildElement("BRANCH");
            BRANCH.addTextNode(smsDTO.getFCubsHeaderDTO().getBranch());
        }
        if (smsDTO.getFCubsHeaderDTO().getModuleId() != null
            && !smsDTO.getFCubsHeaderDTO().getModuleId().trim().isEmpty()) {
            SOAPElement MODULEID = F_CUBS_HEADER.addChildElement("MODULEID");
            MODULEID.addTextNode(smsDTO.getFCubsHeaderDTO().getModuleId());
        }
        if (smsDTO.getFCubsHeaderDTO().getService() != null
            && !smsDTO.getFCubsHeaderDTO().getService().trim().isEmpty()) {
            SOAPElement SERVICE = F_CUBS_HEADER.addChildElement("SERVICE");
            SERVICE.addTextNode(smsDTO.getFCubsHeaderDTO().getService());
        }
        if (smsDTO.getFCubsHeaderDTO().getOperation() != null
            && !smsDTO.getFCubsHeaderDTO().getOperation().trim().isEmpty()) {
            SOAPElement OPERATION = F_CUBS_HEADER.addChildElement("OPERATION");
            OPERATION.addTextNode(smsDTO.getFCubsHeaderDTO().getOperation());
        }
        if (smsDTO.getFCubsHeaderDTO().getSourceOperation() != null
            && !smsDTO.getFCubsHeaderDTO().getSourceOperation().trim().isEmpty()) {
            SOAPElement SOURCE_OPERATION = F_CUBS_HEADER.addChildElement("SOURCE_OPERATION");
            SOURCE_OPERATION.addTextNode(smsDTO.getFCubsHeaderDTO().getSourceOperation());
        }
        if (smsDTO.getFCubsHeaderDTO().getSourceUserId() != null
            && !smsDTO.getFCubsHeaderDTO().getSourceUserId().trim().isEmpty()) {
            SOAPElement SOURCE_USERID = F_CUBS_HEADER.addChildElement("SOURCE_USERID");
            SOURCE_USERID.addTextNode(smsDTO.getFCubsHeaderDTO().getSourceUserId());
        }
        if (smsDTO.getFCubsHeaderDTO().getDestination() != null
            && !smsDTO.getFCubsHeaderDTO().getDestination().trim().isEmpty()) {
            SOAPElement DESTINATION = F_CUBS_HEADER.addChildElement("DESTINATION");
            DESTINATION.addTextNode(smsDTO.getFCubsHeaderDTO().getDestination());
        }
        if (smsDTO.getFCubsHeaderDTO().getMultitripId() != null
            && !smsDTO.getFCubsHeaderDTO().getMultitripId().trim().isEmpty()) {
            SOAPElement MULTITRIPID = F_CUBS_HEADER.addChildElement("MULTITRIPID");
            MULTITRIPID.addTextNode(smsDTO.getFCubsHeaderDTO().getMultitripId());
        }
        if (smsDTO.getFCubsHeaderDTO().getFunctionId() != null
            && !smsDTO.getFCubsHeaderDTO().getFunctionId().trim().isEmpty()) {
            SOAPElement FUNCTIONID = F_CUBS_HEADER.addChildElement("FUNCTIONID");
            FUNCTIONID.addTextNode(smsDTO.getFCubsHeaderDTO().getFunctionId());
        }
        if (smsDTO.getFCubsHeaderDTO().getAction() != null
            && !smsDTO.getFCubsHeaderDTO().getAction().trim().isEmpty()) {
            SOAPElement ACTION = F_CUBS_HEADER.addChildElement("ACTION");
            ACTION.addTextNode(smsDTO.getFCubsHeaderDTO().getAction());
        }
        if (smsDTO.getFCubsHeaderDTO().getPassword() != null
            && !smsDTO.getFCubsHeaderDTO().getPassword().trim().isEmpty()) {
            SOAPElement PASSWORD = F_CUBS_HEADER.addChildElement("PASSWORD");
            PASSWORD.addTextNode(smsDTO.getFCubsHeaderDTO().getPassword());
        }

        SOAPElement F_CUBS_BODY = EXTERNALSERVICE_REQ.addChildElement("FCUBS_BODY");
        SOAPElement SERVICE_IO = F_CUBS_BODY.addChildElement("SERVICE_IO");

        if (smsDTO.getFCubsSMSBodyDTO().getServiceId() != null
            && !smsDTO.getFCubsSMSBodyDTO().getServiceId().trim().isEmpty()) {
            SOAPElement SERVICE_ID = SERVICE_IO.addChildElement("SERVICE_ID");
            SERVICE_ID.addTextNode(smsDTO.getFCubsSMSBodyDTO().getServiceId());
        }
        if (smsDTO.getFCubsSMSBodyDTO().getProductCode() != null
            && !smsDTO.getFCubsSMSBodyDTO().getProductCode().trim().isEmpty()) {
            SOAPElement PRODUCT_CODE = SERVICE_IO.addChildElement("PRODUCT_CODE");
            PRODUCT_CODE.addTextNode(smsDTO.getFCubsSMSBodyDTO().getProductCode());
        }
        SOAPElement SEND_INFO_IO = F_CUBS_BODY.addChildElement("SEND_INFO_IO");
        if (smsDTO.getFCubsSMSBodyDTO().getMobileNumber() != null
            && !smsDTO.getFCubsSMSBodyDTO().getMobileNumber().trim().isEmpty()) {
            SOAPElement MOBILE_NUMBER = SEND_INFO_IO.addChildElement("MOBILE_NUMBER");
            MOBILE_NUMBER.addTextNode(smsDTO.getFCubsSMSBodyDTO().getMobileNumber());
        }
        if (smsDTO.getFCubsSMSBodyDTO().getContent() != null
            && !smsDTO.getFCubsSMSBodyDTO().getContent().trim().isEmpty()) {
            SOAPElement CONTENT = SEND_INFO_IO.addChildElement("CONTENT");
            CONTENT.addTextNode(smsDTO.getFCubsSMSBodyDTO().getContent());
        }
        if (smsDTO.getFCubsSMSBodyDTO().getChannel() != null
            && !smsDTO.getFCubsSMSBodyDTO().getChannel().trim().isEmpty()) {
            SOAPElement CHANNEL = SEND_INFO_IO.addChildElement("CHANNEL");
            CHANNEL.addTextNode(smsDTO.getFCubsSMSBodyDTO().getChannel());
        }
        if (smsDTO.getFCubsSMSBodyDTO().getCateGory() != null
            && !smsDTO.getFCubsSMSBodyDTO().getCateGory().trim().isEmpty()) {
            SOAPElement CATEGORY = SEND_INFO_IO.addChildElement("CATEGORY");
            CATEGORY.addTextNode(smsDTO.getFCubsSMSBodyDTO().getCateGory());
        }
        if (smsDTO.getFCubsSMSBodyDTO().getBranchCode() != null
            && !smsDTO.getFCubsSMSBodyDTO().getBranchCode().trim().isEmpty()) {
            SOAPElement BRANCH_CODE = SEND_INFO_IO.addChildElement("BRANCH_CODE");
            BRANCH_CODE.addTextNode(smsDTO.getFCubsSMSBodyDTO().getBranchCode());
        }
    }

    public static SOAPMessage callSoapWebServiceFCUBSInternal(String soapEndpointUrl, String soapAction, SMSDTO smsDTO) throws IOException, SOAPException {
        ByteArrayOutputStream baos = null;
        SOAPConnectionFactory soapConnectionFactory;
        SOAPConnection soapConnection = null;
        SOAPMessage soapResponse;
        try {
            // Create SOAP Connection
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            soapResponse = soapConnection.call(createSOAPRequestFCUBSInternal(soapAction, smsDTO), soapEndpointUrl);

            // Print the SOAP Response
            baos = new ByteArrayOutputStream();
            soapResponse.writeTo(baos);
            log.info(smsDTO.getRequestorTransId() + "-" + smsDTO.getAppId() + ": " + baos);
        } catch (Exception e) {
            log.error(smsDTO.getRequestorTransId() + "-" + smsDTO.getAppId() + ": " + e);
            soapResponse = null;
        } finally {
            if (soapConnection != null) {
                soapConnection.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
        return soapResponse;
    }

    public static SOAPMessage createSOAPRequestFCUBSInternal(String soapAction, SMSDTO smsDTO) throws Exception {
        ByteArrayOutputStream baos = null;
        MessageFactory messageFactory;
        SOAPMessage soapMessage;
        try {
            messageFactory = MessageFactory.newInstance();
            soapMessage = messageFactory.createMessage();

            createSoapEnvelopeFCUBSInternal(soapMessage, smsDTO);

            MimeHeaders headers = soapMessage.getMimeHeaders();

            soapMessage.saveChanges();

            /* Print the request message, just for debugging purposes */
            baos = new ByteArrayOutputStream();
            soapMessage.writeTo(baos);
            log.info(smsDTO.getRequestorTransId() + "-" + smsDTO.getAppId() + ": " + baos);
        } catch (Exception ex) {
            log.error(smsDTO.getRequestorTransId() + "-" + smsDTO.getAppId() + ": " + ex);
            soapMessage = null;
        } finally {
            if (baos != null) {
                baos.close();
            }
        }
        return soapMessage;
    }

}
