package com.esb.card.utils;

import com.esb.card.constants.Constant;
import com.esb.card.dto.getaccountlist.GetAccountListREQDTO;
import lombok.extern.log4j.Log4j2;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Log4j2
public class SoapGetAccountList {
    private static void createSoapEnvelopeFCUBSInternal(SOAPMessage soapMessage, GetAccountListREQDTO getAccountListDTO) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String namespaceSoapEnv = "soapenv";
        String namespaceFCub = "fcub";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(namespaceSoapEnv, Constant.NAME_SPACE_SOAPEVN);
        envelope.addNamespaceDeclaration(namespaceFCub, Constant.NAME_SPACE_FCUB_INTERNAL_SERVICE);

        //SOAP Header
        SOAPHeader soapHeader = envelope.getHeader();

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement INTERNAL_SERVICE_REQ = soapBody.addChildElement("INTERNALSERVICE_REQ", namespaceFCub);
        SOAPElement F_CUBS_HEADER = INTERNAL_SERVICE_REQ.addChildElement("FCUBS_HEADER");
        SOAPElement SOURCE = F_CUBS_HEADER.addChildElement("SOURCE");
        SOURCE.addTextNode(getAccountListDTO.getFCubsHeader().getSource());
        SOAPElement PASSWORD = F_CUBS_HEADER.addChildElement("PASSWORD");
        PASSWORD.addTextNode(getAccountListDTO.getFCubsHeader().getPassword());
        SOAPElement UBS_COMP = F_CUBS_HEADER.addChildElement("UBSCOMP");
        UBS_COMP.addTextNode(getAccountListDTO.getFCubsHeader().getUbsComp());
        SOAPElement MSG_ID = F_CUBS_HEADER.addChildElement("MSGID");
        MSG_ID.addTextNode(getAccountListDTO.getFCubsHeader().getMsgId());
        SOAPElement USERID = F_CUBS_HEADER.addChildElement("USERID");
        USERID.addTextNode(getAccountListDTO.getFCubsHeader().getUserId());
        SOAPElement SERVICE = F_CUBS_HEADER.addChildElement("SERVICE");
        SERVICE.addTextNode(getAccountListDTO.getFCubsHeader().getService());
        SOAPElement OPERATION = F_CUBS_HEADER.addChildElement("OPERATION");
        OPERATION.addTextNode(getAccountListDTO.getFCubsHeader().getOperation());
        SOAPElement FUNCTIONID = F_CUBS_HEADER.addChildElement("FUNCTIONID");
        FUNCTIONID.addTextNode(getAccountListDTO.getFCubsHeader().getFunctionId());
        SOAPElement ACTION = F_CUBS_HEADER.addChildElement("ACTION");
        ACTION.addTextNode(getAccountListDTO.getFCubsHeader().getAction());
        SOAPElement F_CUBS_BODY = INTERNAL_SERVICE_REQ.addChildElement("FCUBS_BODY");
        SOAPElement Get_Account_List_IO = F_CUBS_BODY.addChildElement("Get_Account_List_IO");
        SOAPElement BRANCH_CODE = Get_Account_List_IO.addChildElement("ACCOUNT_NUMBER");
        BRANCH_CODE.addTextNode(getAccountListDTO.getGetAccountList().getAccountNumber());
        SOAPElement RECORD_PER_PAGE = Get_Account_List_IO.addChildElement("RECORD_PER_PAGE");
        RECORD_PER_PAGE.addTextNode(getAccountListDTO.getGetAccountList().getRecordPerPage());
        SOAPElement PAGE_NUMBER = Get_Account_List_IO.addChildElement("PAGE_NUMBER");
        PAGE_NUMBER.addTextNode(getAccountListDTO.getGetAccountList().getPageNumber());
    }

    public static SOAPMessage callSoapWebServiceFCUBSInternal(String soapEndpointUrl, String soapAction, GetAccountListREQDTO getAccountListDTO) throws SOAPException, IOException {
        ByteArrayOutputStream baos = null;
        SOAPConnectionFactory soapConnectionFactory;
        SOAPConnection soapConnection = null;
        SOAPMessage soapResponse;
        try {
            // Create SOAP Connection
            log.info("URL:" + soapEndpointUrl);
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            soapResponse = soapConnection.call(createSOAPRequestFCUBSInternal(soapAction, getAccountListDTO), soapEndpointUrl);

            // Print the SOAP Response
            baos = new ByteArrayOutputStream();
            soapResponse.writeTo(baos);
            log.info(getAccountListDTO.getFCubsHeader().getMsgId() + " GetAccountList: " + baos);
        } catch (Exception e) {
            soapResponse = null;
            log.info(getAccountListDTO.getFCubsHeader().getMsgId() + " Exception Call Soap GetAccountList " + e);
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

    public static SOAPMessage createSOAPRequestFCUBSInternal(String soapAction, GetAccountListREQDTO getAccountListDTO) throws Exception {
        ByteArrayOutputStream baos = null;
        MessageFactory messageFactory;
        SOAPMessage soapMessage;
        try {
            messageFactory = MessageFactory.newInstance();
            soapMessage = messageFactory.createMessage();

            createSoapEnvelopeFCUBSInternal(soapMessage, getAccountListDTO);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", soapAction);

            soapMessage.saveChanges();

            /* Print the request message, just for debugging purposes */
            baos = new ByteArrayOutputStream();
            soapMessage.writeTo(baos);
            log.info(getAccountListDTO.getFCubsHeader().getMsgId() + " GetAccountList: " + baos);
        } catch (IOException ex) {
            soapMessage = null;
            log.info(getAccountListDTO.getFCubsHeader().getMsgId() + " Exception Call Soap GetAccountList " + ex);
        } finally {
            if (baos != null) {
                baos.close();
            }
        }
        return soapMessage;
    }

}

