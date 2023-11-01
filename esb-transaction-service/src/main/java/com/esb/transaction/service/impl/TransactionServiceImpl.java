package com.esb.transaction.service.impl;

import com.esb.transaction.constants.Constant;
import com.esb.transaction.dto.*;
import com.esb.transaction.model.EsbServiceProcess;
import com.esb.transaction.model.EsbSystemEcomLog;
import com.esb.transaction.respository.EsbTransactionRepository;
import com.esb.transaction.respository.ITransactionDAO;
import com.esb.transaction.service.IEsbServiceProcessService;
import com.esb.transaction.service.IEsbSystemEcomLogService;
import com.esb.transaction.service.ITransactionService;
import com.esb.transaction.utils.SoapUploadTransferCitad;
import com.esb.transaction.utils.SoapUploadTransferJrn;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.CommonException;
import com.lpb.esb.service.common.utils.code.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    private EsbTransactionRepository esbTransactionRepository;

    @Autowired
    private ITransactionDAO iTransactionDAO;

    @Autowired
    private IEsbServiceProcessService esbServiceProcessService;

    @Autowired
    private IEsbSystemEcomLogService iEsbSystemEcomLogService;

    @Override
    public ResponseModel loadTransactionPost(String appMsgId) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            List<TransactionPostInfoDTO> data = esbTransactionRepository.loadTransactionPost(appMsgId);
            if (data != null && !data.isEmpty()) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
            log.info(appMsgId + " LoadTransactionPost " + lpbResCode.getErrorDesc());
            responseModel.setResCode(lpbResCode);
            responseModel.setData(data);
            return responseModel;
        } catch (Exception e) {
            log.error(appMsgId + " Exception LoadTransactionPost " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    @Override
    public ResponseModel getResponseModelUploadTransferJRN(Document doc) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        XPath xPath = XPathFactory.newInstance().newXPath();
        String BATCH_NUMBER = "";
        String MSGSTAT = "";
        try {
            NodeList nodeList = (NodeList) xPath.compile("//BATCH_NUMBER")
                .evaluate(doc, XPathConstants.NODESET);
            if (nodeList.getLength() > 0
                && nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                BATCH_NUMBER = nodeList.item(0).getTextContent();
            }
            if (!BATCH_NUMBER.isEmpty())
                responseModel.setData(BATCH_NUMBER);

            nodeList = (NodeList) xPath.compile("//MSGSTAT")
                .evaluate(doc, XPathConstants.NODESET);
            if (nodeList.getLength() > 0
                && nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                MSGSTAT = nodeList.item(0).getTextContent();
            }
            nodeList = (NodeList) xPath.compile("//WARNING").evaluate(doc,
                XPathConstants.NODESET);
            if (nodeList.getLength() > 0
                && nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    if (nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                        Element el = (Element) nodeList.item(i);
                        lpbResCode.setRefCode(el.getElementsByTagName("WCODE").item(0).getTextContent());
                        lpbResCode.setRefDesc(el.getElementsByTagName("WDESC").item(0).getTextContent());
                    }
                }
            }
            if (nodeList == null || nodeList.getLength() <= 0) {
                nodeList = (NodeList) xPath.compile("//ERROR").evaluate(
                    doc, XPathConstants.NODESET);
                if (nodeList.getLength() > 0) {
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        if (nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                            Element el = (Element) nodeList.item(0);
                            if (el.getNodeName().contains("ERROR")) {
                                lpbResCode.setRefCode(el.getElementsByTagName("ECODE").item(0).getTextContent());
                                lpbResCode.setRefDesc(el.getElementsByTagName("EDESC").item(0).getTextContent());
                            }
                        }
                    }
                }
            }
            if (MSGSTAT.equals("SUCCESS")) {
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
            } else if (lpbResCode.getRefCode().equals(Constant.AVAILABLE_AMOUNT)) {
                lpbResCode.setErrorCode(ErrorMessage.AVAILABLE_AMOUNT.label);
                lpbResCode.setErrorDesc(ErrorMessage.AVAILABLE_AMOUNT.description);
            } else if (lpbResCode.getRefCode().equals(Constant.DUPLICATE_MESSAGE)) {
                lpbResCode.setErrorCode(ErrorMessage.DUPLICATE_MESSAGE.label);
                lpbResCode.setErrorDesc(ErrorMessage.DUPLICATE_MESSAGE.description);
            } else if (lpbResCode.getRefCode().equals(Constant.INVALID_ACCOUNT_NUMBER)) {
                lpbResCode.setErrorCode(ErrorMessage.INVALID_ACCOUNT_NUMBER.label);
                lpbResCode.setErrorDesc(ErrorMessage.INVALID_ACCOUNT_NUMBER.description);
            } else if (lpbResCode.getRefCode().equals(Constant.ACCOUNT_GL_NOT_BRANCH)) {
                lpbResCode.setErrorCode(ErrorMessage.INVALID_ACCOUNT_NUMBER.label);
                lpbResCode.setErrorDesc(ErrorMessage.INVALID_ACCOUNT_NUMBER.description);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }
            responseModel.setResCode(lpbResCode);
            return responseModel;
        } catch (Exception e) {
            log.error("Exception Response UploadTransferJRN: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    @Override
    public ResponseModel initTransaction(String userID, String trnBranch, String trnDesc,
                                         String appMsgId, String xmltypeCustomerInfo,
                                         String xmlTypePartnerInfo, String xmlTypeService,
                                         String xmlTypeTrnPost) {

        return iTransactionDAO.initTransaction(userID, trnBranch, trnDesc, appMsgId, xmltypeCustomerInfo,
            xmlTypePartnerInfo, xmlTypeService, xmlTypeTrnPost);
    }

    @Override
    public ResponseModel updateTransaction(UpdateTransactionDTO transactionDTO) {
        return iTransactionDAO.updateTransaction(transactionDTO);
    }


    @Override
    public ResponseModel uploadTransferJRN(UploadTransferJRNDTO uploadTransferIO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        UpdateTransactionDTO updateTransactionDTO = new UpdateTransactionDTO();
        updateTransactionDTO.setAppMsgId(uploadTransferIO.getFCubsHeader().getAppId());
        updateTransactionDTO.setValueDt(new Date());
        updateTransactionDTO.setMessageId(uploadTransferIO.getFCubsHeader().getMsgId());
        try {
            List<EsbServiceProcess> serviceInfo = esbServiceProcessService.
                getServiceInfo(Constant.SERVICE_ID, Constant.PRODUCT_REQUEST_TXN);
            String urlAPI = serviceInfo.get(0).getUrlApi() + serviceInfo.get(0).getConnectorUrl();
            SOAPMessage soapResponse = SoapUploadTransferJrn.callSoapWebServiceFCUBSInternal(urlAPI, "", initDataUploadTransfer(uploadTransferIO));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapResponse.writeTo(out);
            String strMsg = new String(out.toByteArray());

            DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(strMsg)));
            ResponseModel<String> responseModelData = getResponseModelUploadTransferJRN(doc);

            updateTransactionDTO.setCoreRefNo(responseModelData.getData());
            if (responseModelData.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                updateTransactionDTO.setErrorDesc("SUCCESS");
                updateTransactionDTO.setErrorCode("ESB-000");
                updateTransaction(updateTransactionDTO);
            } else {
                updateTransactionDTO.setErrorDesc(responseModelData.getResCode().getErrorDesc());
                updateTransactionDTO.setErrorCode(responseModelData.getResCode().getErrorCode());
                updateTransaction(updateTransactionDTO);
            }
            return responseModelData;

        } catch (NullPointerException e) {
            saveEcomSystemLog(uploadTransferIO, "ESB-099", "Exception NullPointerException UploadTransferJrn");
            updateTransactionDTO.setErrorDesc("Exception NullPointerException UploadTransferJrn");
            updateTransactionDTO.setErrorCode("ESB-099");
            updateTransaction(updateTransactionDTO);

            log.error(uploadTransferIO.getFCubsHeader().getAppId() + " Exception NullPointerException UploadTransferJRN: " + e);
            lpbResCode.setErrorCode(ErrorMessage.INPUT_ERROR.label);
            lpbResCode.setErrorDesc(ErrorMessage.INPUT_ERROR.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        } catch (Exception e) {
            saveEcomSystemLog(uploadTransferIO, "ESB-099", "Exception UploadTransferJrn");
            updateTransactionDTO.setErrorDesc("Exception UploadTransferJrn");
            updateTransactionDTO.setErrorCode("ESB-099");
            updateTransaction(updateTransactionDTO);

            log.error(uploadTransferIO.getFCubsHeader().getAppId() + " Exception UploadTransferJRN: " + e);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    /**
     * @author Trung.Nguyen
     * @date 15-Sep-2022
     * */
    @Override
    public ResponseModel uploadTransferCitad(UploadTransferJRNDTO uploadTransferIO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            List<EsbServiceProcess> serviceInfo = esbServiceProcessService.
                getServiceInfo(Constant.SERVICE_ID, Constant.PRODUCT_REQUEST_TXN);
            String urlAPI = serviceInfo.get(0).getUrlApi() + serviceInfo.get(0).getConnectorUrl();
            SOAPMessage soapResponse = SoapUploadTransferCitad.callSoapWebServiceFCUBSInternal(urlAPI, "", initDataUploadTransfer(uploadTransferIO));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapResponse.writeTo(out);
            String strMsg = new String(out.toByteArray());

            DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(strMsg)));
            ResponseModel<String> responseModelData = getResponseModelUploadTransferJRN(doc);

            UpdateTransactionDTO updateTransactionDTO = new UpdateTransactionDTO();
            updateTransactionDTO.setAppMsgId(uploadTransferIO.getFCubsHeader().getAppId());
            updateTransactionDTO.setCoreRefNo(responseModelData.getData());
            updateTransactionDTO.setValueDt(new Date());
            updateTransactionDTO.setMessageId(uploadTransferIO.getFCubsHeader().getMsgId());

            if (responseModelData.getResCode().getErrorCode().equals(ErrorMessage.SUCCESS.label)) {
                updateTransactionDTO.setErrorDesc("SUCCESS");
                updateTransactionDTO.setErrorCode("ESB-000");
                updateTransaction(updateTransactionDTO);
            } else {
                updateTransactionDTO.setErrorDesc(responseModelData.getResCode().getErrorDesc());
                updateTransactionDTO.setErrorCode(responseModelData.getResCode().getErrorCode());
                updateTransaction(updateTransactionDTO);
            }
            return responseModelData;

        } catch (NullPointerException e) {
            log.error(uploadTransferIO.getFCubsHeader().getAppId() + " Exception uploadTransferCitad: " + e);
            lpbResCode.setErrorCode(ErrorMessage.INPUT_ERROR.label);
            lpbResCode.setErrorDesc(ErrorMessage.INPUT_ERROR.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        } catch (Exception e) {
            log.error(uploadTransferIO.getFCubsHeader().getAppId() + " Exception uploadTransferCitad: " + e);
            lpbResCode.setErrorCode(ErrorMessage.TIMEOUT.label);
            lpbResCode.setErrorDesc(ErrorMessage.TIMEOUT.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

    private InternalServiceREQDTO initDataUploadTransfer(UploadTransferJRNDTO uploadTransferIO) {
        FCubsBodyDTO fCubsBody = FCubsBodyDTO.builder()
            .uploadTransferIO(uploadTransferIO.getUploadTransferIO())
            .build();

        InternalServiceREQDTO internalServiceREQ = InternalServiceREQDTO.builder()
            .fCubsHeader(uploadTransferIO.getFCubsHeader())
            .fCubsBody(fCubsBody)
            .build();
        return internalServiceREQ;
    }

    //lưu log nếu uploadtransfer fail
    private void saveEcomSystemLog(UploadTransferJRNDTO uploadTransferIO, String errorCode, String errorDesc) {
        EsbSystemEcomLog esbSystemEcomLogNew = new EsbSystemEcomLog();
        esbSystemEcomLogNew.setMsgId(iEsbSystemEcomLogService.getAppMsgID());
        esbSystemEcomLogNew.setAppId(uploadTransferIO.getFCubsHeader().getAppId());
        esbSystemEcomLogNew.setRequestorCode("NAPAS");
        esbSystemEcomLogNew.setMethodAction("Purchase");

        esbSystemEcomLogNew.setErrorCode(errorCode);
        esbSystemEcomLogNew.setErrorDesc(errorDesc);
        esbSystemEcomLogNew.setStatus(ErrorMessage.FAIL.label);

        iEsbSystemEcomLogService.save(esbSystemEcomLogNew);
    }
}
