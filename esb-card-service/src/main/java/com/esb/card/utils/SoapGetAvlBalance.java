package com.esb.card.utils;

import com.esb.card.constants.Constant;
import com.esb.card.dto.getavlbalance.GetAvlBalanceREQDTO;
import lombok.extern.log4j.Log4j2;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Log4j2
public class SoapGetAvlBalance {
    private static void createSoapEnvelopeFCUBSInternal(SOAPMessage soapMessage, GetAvlBalanceREQDTO getAvlBalanceDTO) throws SOAPException {
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
        SOURCE.addTextNode(getAvlBalanceDTO.getFCubsHeader().getSource());
        SOAPElement PASSWORD = F_CUBS_HEADER.addChildElement("PASSWORD");
        PASSWORD.addTextNode(getAvlBalanceDTO.getFCubsHeader().getPassword());
        SOAPElement UBS_COMP = F_CUBS_HEADER.addChildElement("UBSCOMP");
        UBS_COMP.addTextNode(getAvlBalanceDTO.getFCubsHeader().getUbsComp());
        SOAPElement MSG_ID = F_CUBS_HEADER.addChildElement("MSGID");
        MSG_ID.addTextNode(getAvlBalanceDTO.getFCubsHeader().getMsgId());
        SOAPElement USERID = F_CUBS_HEADER.addChildElement("USERID");
        USERID.addTextNode(getAvlBalanceDTO.getFCubsHeader().getUserId());
        SOAPElement SERVICE = F_CUBS_HEADER.addChildElement("SERVICE");
        SERVICE.addTextNode(getAvlBalanceDTO.getFCubsHeader().getService());
        SOAPElement OPERATION = F_CUBS_HEADER.addChildElement("OPERATION");
        OPERATION.addTextNode(getAvlBalanceDTO.getFCubsHeader().getOperation());
        SOAPElement FUNCTIONID = F_CUBS_HEADER.addChildElement("FUNCTIONID");
        FUNCTIONID.addTextNode(getAvlBalanceDTO.getFCubsHeader().getFunctionId());
        SOAPElement ACTION = F_CUBS_HEADER.addChildElement("ACTION");
        ACTION.addTextNode(getAvlBalanceDTO.getFCubsHeader().getAction());
        SOAPElement F_CUBS_BODY = INTERNAL_SERVICE_REQ.addChildElement("FCUBS_BODY");
        SOAPElement Get_Account_List_IO = F_CUBS_BODY.addChildElement("Get_Account_List_IO");
        SOAPElement BRANCH_CODE = Get_Account_List_IO.addChildElement("ACCOUNT_NUMBER");
        BRANCH_CODE.addTextNode(getAvlBalanceDTO.getGetAvlBalance().getAccountNumber());
    }

    public static SOAPMessage callSoapWebServiceFCUBSInternal(String soapEndpointUrl, String soapAction, GetAvlBalanceREQDTO getAvlBalanceDTO) throws SOAPException, IOException {
        ByteArrayOutputStream baos = null;
        SOAPConnectionFactory soapConnectionFactory;
        SOAPConnection soapConnection = null;
        SOAPMessage soapResponse;
        try {
            // Create SOAP Connection
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            soapResponse = soapConnection.call(createSOAPRequestFCUBSInternal(soapAction, getAvlBalanceDTO), soapEndpointUrl);

            // Print the SOAP Response
            baos = new ByteArrayOutputStream();
            soapResponse.writeTo(baos);
            log.info(getAvlBalanceDTO.getFCubsHeader().getMsgId() + " GetAvlBalance: " + baos);

        } catch (Exception e) {
            soapResponse = null;
            log.info(getAvlBalanceDTO.getFCubsHeader().getMsgId() + " Exception Call Soap GetAvlBalance " + e);
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

    public static SOAPMessage createSOAPRequestFCUBSInternal(String soapAction, GetAvlBalanceREQDTO getAvlBalanceDTO) throws Exception {
        ByteArrayOutputStream baos = null;
        MessageFactory messageFactory;
        SOAPMessage soapMessage;
        try {
            messageFactory = MessageFactory.newInstance();
            soapMessage = messageFactory.createMessage();

            createSoapEnvelopeFCUBSInternal(soapMessage, getAvlBalanceDTO);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", soapAction);

            soapMessage.saveChanges();

            /* Print the request message, just for debugging purposes */
            baos = new ByteArrayOutputStream();
            soapMessage.writeTo(baos);
            log.info(getAvlBalanceDTO.getFCubsHeader().getMsgId() + " GetAvlBalance: " + baos);
        } catch (IOException ex) {
            soapMessage = null;
            log.info(getAvlBalanceDTO.getFCubsHeader().getMsgId() + "Exception Call Soap GetAvlBalance " + ex);
        } finally {
            if (baos != null) {
                baos.close();
            }
        }
        return soapMessage;
    }

}

