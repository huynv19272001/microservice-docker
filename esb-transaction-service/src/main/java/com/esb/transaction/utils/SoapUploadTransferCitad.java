/**
 * @author Trung.Nguyen
 * @date 15-Sep-2022
 * @description: be cloned from SoapUploadTransferJrn
 * */
package com.esb.transaction.utils;

import com.esb.transaction.constants.Constant;
import com.esb.transaction.dto.InternalServiceREQDTO;
import lombok.extern.log4j.Log4j2;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Log4j2
public class SoapUploadTransferCitad {

    private static void createSoapEnvelopeFCUBSInternal(SOAPMessage soapMessage, InternalServiceREQDTO internalServiceREQ) throws SOAPException {
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
        SOURCE.addTextNode(internalServiceREQ.getFCubsHeader().getSource());
        SOAPElement PASSWORD = F_CUBS_HEADER.addChildElement("PASSWORD");
        PASSWORD.addTextNode(internalServiceREQ.getFCubsHeader().getPassword());
        SOAPElement UBS_COMP = F_CUBS_HEADER.addChildElement("UBSCOMP");
        UBS_COMP.addTextNode(internalServiceREQ.getFCubsHeader().getUbsComp());
        SOAPElement MSG_ID = F_CUBS_HEADER.addChildElement("MSGID");
        MSG_ID.addTextNode(internalServiceREQ.getFCubsHeader().getMsgId());
        SOAPElement CORREL_ID = F_CUBS_HEADER.addChildElement("CORRELID");
        CORREL_ID.addTextNode(internalServiceREQ.getFCubsHeader().getMsgId());
        SOAPElement USERID = F_CUBS_HEADER.addChildElement("USERID");
        USERID.addTextNode(internalServiceREQ.getFCubsHeader().getUserId());
        SOAPElement BRANCH = F_CUBS_HEADER.addChildElement("BRANCH");
        BRANCH.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getBranchCode());
        SOAPElement MODULE_ID = F_CUBS_HEADER.addChildElement("MODULEID");
        MODULE_ID.addTextNode(internalServiceREQ.getFCubsHeader().getModuleId());
        SOAPElement SERVICE = F_CUBS_HEADER.addChildElement("SERVICE");
        SERVICE.addTextNode(internalServiceREQ.getFCubsHeader().getService());
        SOAPElement OPERATION = F_CUBS_HEADER.addChildElement("OPERATION");
        OPERATION.addTextNode(internalServiceREQ.getFCubsHeader().getOperation());

        SOAPElement F_CUBS_BODY = INTERNAL_SERVICE_REQ.addChildElement("FCUBS_BODY");

        SOAPElement Upload_Transfer_IO = F_CUBS_BODY.addChildElement("Upload_Transfer_IO");
        SOAPElement BRANCH_CODE = Upload_Transfer_IO.addChildElement("BRANCH_CODE");
        BRANCH_CODE.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getBranchCode());
        SOAPElement DESCRIPTION = Upload_Transfer_IO.addChildElement("DESCRIPTION");
        DESCRIPTION.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getDescription());
        SOAPElement SOURCE_CODE = Upload_Transfer_IO.addChildElement("SOURCE_CODE");
        SOURCE_CODE.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getSourceCode());
        SOAPElement TXN_CODE = Upload_Transfer_IO.addChildElement("TXN_CODE");
        TXN_CODE.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getTxnCode());
        SOAPElement VALUE_DT = Upload_Transfer_IO.addChildElement("VALUE_DT");
        VALUE_DT.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getValueDt());
        SOAPElement MAKER_ID = Upload_Transfer_IO.addChildElement("MAKER_ID");
        MAKER_ID.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getMakerId());
        SOAPElement CHECKER_ID = Upload_Transfer_IO.addChildElement("CHECKER_ID");
        CHECKER_ID.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getCheckerId());
        SOAPElement ENTRY_LIST = Upload_Transfer_IO.addChildElement("ENTRY_LIST");
        for (int i = 0; i < internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().size(); i++) {
            SOAPElement ENTRY = ENTRY_LIST.addChildElement("ENTRY");
            SOAPElement ACCOUNT_NUMBER = ENTRY.addChildElement("ACCOUNT_NUMBER");
            ACCOUNT_NUMBER.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getAccountNumber());
            SOAPElement ACCOUNT_BRANCH = ENTRY.addChildElement("ACCOUNT_BRANCH");
            ACCOUNT_BRANCH.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getAccountBranch());
            SOAPElement ACCOUNT_CCY = ENTRY.addChildElement("ACCOUNT_CCY");
            ACCOUNT_CCY.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getAccountCCY());
            SOAPElement ACCOUNT_TYPE = ENTRY.addChildElement("ACCOUNT_TYPE");
            ACCOUNT_TYPE.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getAccountType());
            SOAPElement DRCR_INDICATOR = ENTRY.addChildElement("DRCR_INDICATOR");
            DRCR_INDICATOR.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getDrcrIndicator());
            SOAPElement LCY_AMOUNT = ENTRY.addChildElement("LCY_AMOUNT");
            LCY_AMOUNT.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getLcyAmount());
            /**
             * @author Trung.Nguyen
             * @date 15-Sep-2022
             * @description: append these tags to the Citad transfer request
             * */
            SOAPElement SENDER_INST_ACCOUNT = ENTRY.addChildElement("SENDER_INST_ACCOUNT");
            SENDER_INST_ACCOUNT.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getSenderInstAccount());
            SOAPElement RECEIVER_NAME = ENTRY.addChildElement("RECEIVER_NAME");
            RECEIVER_NAME.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getReceiverName());
            SOAPElement RECEIVER_ADDRESS = ENTRY.addChildElement("RECEIVER_ADDRESS");
            RECEIVER_ADDRESS.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getReceiverAddress());
            SOAPElement RECEIVER_ACCOUNT = ENTRY.addChildElement("RECEIVER_ACCOUNT");
            RECEIVER_ACCOUNT.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getReceiverAccount());
            SOAPElement RECEIVER_CODE = ENTRY.addChildElement("RECEIVER_CODE");
            RECEIVER_CODE.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getListEntry().get(i).getReceiverCode());
            //
            SOAPElement DESCRIPTIONENTRY = ENTRY.addChildElement("DESCRIPTION");
            DESCRIPTIONENTRY.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getDescription());
        }
        SOAPElement TRN_REF_NO = Upload_Transfer_IO.addChildElement("TRN_REF_NO");
        if (internalServiceREQ.getFCubsBody().getUploadTransferIO().getTrnRefNo() != null) {
            TRN_REF_NO.addTextNode(internalServiceREQ.getFCubsBody().getUploadTransferIO().getTrnRefNo());
        }
    }

    public static SOAPMessage createSOAPRequestFCUBSInternal(String soapAction, InternalServiceREQDTO internalServiceREQ) throws Exception {
        ByteArrayOutputStream baos = null;
        MessageFactory messageFactory;
        SOAPMessage soapMessage;
        try {
            messageFactory = MessageFactory.newInstance();
            soapMessage = messageFactory.createMessage();

            createSoapEnvelopeFCUBSInternal(soapMessage, internalServiceREQ);

            MimeHeaders headers = soapMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", soapAction);

            soapMessage.saveChanges();

            /* Print the request message, just for debugging purposes */
            baos = new ByteArrayOutputStream();
            soapMessage.writeTo(baos);
            log.info(internalServiceREQ.getFCubsHeader().getAppId() + ": " + baos);

        } catch (Exception e) {
            log.info(internalServiceREQ.getFCubsHeader().getAppId() + ": " + e);
            soapMessage = null;
        } finally {
            if (baos != null) {
                baos.close();
            }
        }
        return soapMessage;
    }

    public static SOAPMessage callSoapWebServiceFCUBSInternal(String soapEndpointUrl, String soapAction, InternalServiceREQDTO internalServiceREQ) throws SOAPException, IOException {
        ByteArrayOutputStream baos = null;
        SOAPConnectionFactory soapConnectionFactory;
        SOAPConnection soapConnection = null;
        SOAPMessage soapResponse;
        try {
            // Create SOAP Connection
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            soapResponse = soapConnection.call(createSOAPRequestFCUBSInternal(soapAction, internalServiceREQ), soapEndpointUrl);

            // Print the SOAP Response
            baos = new ByteArrayOutputStream();
            soapResponse.writeTo(baos);
            log.info(internalServiceREQ.getFCubsHeader().getAppId() + ": " + baos);

        } catch (Exception e) {
            log.info(internalServiceREQ.getFCubsHeader().getAppId() + ": " + e);
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

}
