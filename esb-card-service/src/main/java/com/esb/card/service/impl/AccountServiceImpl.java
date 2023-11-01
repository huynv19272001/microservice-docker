package com.esb.card.service.impl;

import com.esb.card.configuration.ServiceConfig;
import com.esb.card.constants.Constant;
import com.esb.card.dto.getaccountlist.GetAccountListREQDTO;
import com.esb.card.dto.getaccountlist.GetAccountListRESDTO;
import com.esb.card.dto.getavlbalance.GetAvlBalanceREQDTO;
import com.esb.card.dto.getavlbalance.GetAvlBalanceRESDTO;
import com.esb.card.model.EsbServiceProcess;
import com.esb.card.service.IAccountService;
import com.esb.card.service.IEsbService;
import com.esb.card.utils.SoapGetAccountList;
import com.esb.card.utils.SoapGetAvlBalance;
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
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private ServiceConfig serverConfigProperties;

    @Autowired
    private IEsbService iEsbService;

    @Override
    public ResponseModel getDataAccountList(Document doc) {
        ResponseModel<Object> executeModel = ResponseModel.builder().build();
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
            if (!MSGSTAT.equals("SUCCESS")) {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }

            nodeList = (NodeList) xPath.compile("//ACCOUNT_LIST").evaluate(doc,
                XPathConstants.NODESET);
            if (nodeList.getLength() > 0
                && nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                List<GetAccountListRESDTO> listAccount = new ArrayList<>();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element el = (Element) nodeList.item(i);
                        if (el.getNodeName().contains("ACCOUNT")) {
                            GetAccountListRESDTO getAccountListRES = new GetAccountListRESDTO();
                            getAccountListRES.setBranchCode(el.getElementsByTagName("BRANCH_CODE").item(0).getTextContent());
                            getAccountListRES.setAccountNumber(el.getElementsByTagName("ACCOUNT_NUMER").item(0).getTextContent());
                            getAccountListRES.setAccountName(el.getElementsByTagName("ACCOUNT_NAME").item(0).getTextContent());
                            getAccountListRES.setCustomerNumber(el.getElementsByTagName("CUSTOMER_NUMBER").item(0).getTextContent());
                            getAccountListRES.setAccountCcy(el.getElementsByTagName("ACCOUNT_CCY").item(0).getTextContent());
                            getAccountListRES.setAccountClass(el.getElementsByTagName("ACCOUNT_CLASS").item(0).getTextContent());
                            getAccountListRES.setAcStatNoDr(el.getElementsByTagName("AC_STAT_NO_DR").item(0).getTextContent());
                            getAccountListRES.setAcStatNoCr(el.getElementsByTagName("AC_STAT_NO_CR").item(0).getTextContent());
                            getAccountListRES.setAcStatDormant(el.getElementsByTagName("AC_STAT_DORMANT").item(0).getTextContent());
                            getAccountListRES.setAcOpenDate(el.getElementsByTagName("AC_OPEN_DATE").item(0).getTextContent());
                            getAccountListRES.setAltAcNo(el.getElementsByTagName("ALT_AC_NO").item(0).getTextContent());
                            getAccountListRES.setAtmFacility(el.getElementsByTagName("ATM_FACILITY").item(0).getTextContent());
                            getAccountListRES.setPassbookFacility(el.getElementsByTagName("PASSBOOK_FACILITY").item(0).getTextContent());
                            getAccountListRES.setAcStatFrozen(el.getElementsByTagName("AC_STAT_FROZEN").item(0).getTextContent());
                            getAccountListRES.setTodLimit(el.getElementsByTagName("TOD_LIMIT").item(0).getTextContent());
                            getAccountListRES.setDrGl(el.getElementsByTagName("DR_GL").item(0).getTextContent());
                            getAccountListRES.setCrGl(el.getElementsByTagName("CR_GL").item(0).getTextContent());
                            getAccountListRES.setRecordStat(el.getElementsByTagName("RECORD_STAT").item(0).getTextContent());
                            getAccountListRES.setAuthStat(el.getElementsByTagName("AUTH_STAT").item(0).getTextContent());
                            getAccountListRES.setModNo(el.getElementsByTagName("MOD_NO").item(0).getTextContent());
                            getAccountListRES.setMakerId(el.getElementsByTagName("CHECKER_ID").item(0).getTextContent());
                            getAccountListRES.setMinBalance(el.getElementsByTagName("MIN_BALANCE").item(0).getTextContent());

                            listAccount.add(getAccountListRES);
                        }
                    }
                }
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                executeModel.setData(listAccount);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
        } catch (Exception e) {
            log.info("Exception GetAccountList: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        executeModel.setResCode(lpbResCode);
        return executeModel;
    }

    @Override
    public ResponseModel getDataAvlBalance(Document doc) {
        ResponseModel<Object> responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();

        List<GetAvlBalanceRESDTO> listAccount = new ArrayList<>();
        XPath xPath = XPathFactory.newInstance().newXPath();
        String MSGSTAT = "";
        try {
            NodeList nodeList = (NodeList) xPath.compile("//MSGSTAT")
                .evaluate(doc, XPathConstants.NODESET);

            if (nodeList.getLength() > 0
                && nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                MSGSTAT = nodeList.item(0).getTextContent();
            }
            if (!MSGSTAT.equals("SUCCESS")) {
                lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
                lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            }

            nodeList = (NodeList) xPath.compile("//ACCOUNT_LIST").evaluate(doc,
                XPathConstants.NODESET);
            if (nodeList.getLength() > 0
                && nodeList.item(0).getNodeType() == Node.ELEMENT_NODE) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        Element el = (Element) nodeList.item(i);
                        if (el.getNodeName().contains("ACCOUNT")) {
                            GetAvlBalanceRESDTO getAvlBalanceRES = new GetAvlBalanceRESDTO();
                            getAvlBalanceRES.setBranchCode(el.getElementsByTagName("BRANCH_CODE").item(0).getTextContent());
                            getAvlBalanceRES.setAccountNumber(el.getElementsByTagName("ACCOUNT_NUMER").item(0).getTextContent());
                            getAvlBalanceRES.setAccountName(el.getElementsByTagName("ACCOUNT_NAME").item(0).getTextContent());
                            getAvlBalanceRES.setCustomerNumber(el.getElementsByTagName("CUSTOMER_NUMBER").item(0).getTextContent());
                            getAvlBalanceRES.setAccountCcy(el.getElementsByTagName("ACCOUNT_CCY").item(0).getTextContent());
                            getAvlBalanceRES.setAccountClass(el.getElementsByTagName("ACCOUNT_CLASS").item(0).getTextContent());
                            getAvlBalanceRES.setAcStatNoDr(el.getElementsByTagName("AC_STAT_NO_DR").item(0).getTextContent());
                            getAvlBalanceRES.setAcStatNoCr(el.getElementsByTagName("AC_STAT_NO_CR").item(0).getTextContent());
                            getAvlBalanceRES.setMinBalance(el.getElementsByTagName("MIN_BALANCE").item(0).getTextContent());
                            getAvlBalanceRES.setAcyAvlBal(el.getElementsByTagName("ACY_AVL_BAL").item(0).getTextContent());
                            getAvlBalanceRES.setAccountType(el.getElementsByTagName("ACCOUNT_TYPE").item(0).getTextContent());
                            getAvlBalanceRES.setDrAccrued(el.getElementsByTagName("DR_ACCRUED").item(0).getTextContent());
                            getAvlBalanceRES.setCrAccrued(el.getElementsByTagName("CR_ACCRUED").item(0).getTextContent());
                            listAccount.add(getAvlBalanceRES);
                        }
                    }
                }
                lpbResCode.setErrorCode(ErrorMessage.SUCCESS.label);
                lpbResCode.setErrorDesc(ErrorMessage.SUCCESS.description);
                responseModel.setData(listAccount);
            } else {
                lpbResCode.setErrorCode(ErrorMessage.NODATA.label);
                lpbResCode.setErrorDesc(ErrorMessage.NODATA.description);
            }
        } catch (Exception e) {
            log.info("Exception GetAvlBalance: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
        }
        responseModel.setResCode(lpbResCode);
        return responseModel;
    }

    @Override
    public ResponseModel getAccountList(GetAccountListREQDTO getAccountListDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            getAccountListDTO.getFCubsHeader().setSource(serverConfigProperties.getHeaderSource());
            getAccountListDTO.getFCubsHeader().setPassword(serverConfigProperties.getHeaderPassword());
            getAccountListDTO.getFCubsHeader().setUbsComp(serverConfigProperties.getHeaderUbsComp());
            getAccountListDTO.getFCubsHeader().setUserId(serverConfigProperties.getHeaderUserId());
            getAccountListDTO.getFCubsHeader().setService(serverConfigProperties.getHeaderService());
            getAccountListDTO.getFCubsHeader().setOperation(serverConfigProperties.getHeaderOperationGetAccountList());
            getAccountListDTO.getFCubsHeader().setFunctionId(serverConfigProperties.getHeaderFuntionId());
            getAccountListDTO.getFCubsHeader().setAction(serverConfigProperties.getHeaderAction());

            getAccountListDTO.getGetAccountList().setRecordPerPage(serverConfigProperties.getBodyRecordPerPage());
            getAccountListDTO.getGetAccountList().setPageNumber(serverConfigProperties.getBodyPageNumber());

            List<EsbServiceProcess> serviceInfo = iEsbService.getServiceInfo(Constant.SERVICE_ID, Constant.PRODUCT_REQUEST_TXN);
            String urlAPI = serviceInfo.get(0).getUrlApi() + serviceInfo.get(0).getConnectorUrl();
            SOAPMessage soapResponse = SoapGetAccountList.callSoapWebServiceFCUBSInternal(urlAPI, "", getAccountListDTO);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapResponse.writeTo(out);
            String strMsg = new String(out.toByteArray());

            DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(strMsg)));
            responseModel = getDataAccountList(doc);
            log.info(getAccountListDTO.getFCubsHeader().getMsgId() + " GetAccountList: " + responseModel.getResCode().getErrorDesc());
            return responseModel;
        } catch (NullPointerException e) {
            log.info(getAccountListDTO.getFCubsHeader().getMsgId() + " GetAccountList: " + e);
            lpbResCode.setErrorCode(ErrorMessage.INPUT_ERROR.label);
            lpbResCode.setErrorDesc(ErrorMessage.INPUT_ERROR.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        } catch (Exception e) {
            log.info(getAccountListDTO.getFCubsHeader().getMsgId() + " GetAccountList: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }


    @Override
    public ResponseModel getAvlBalance(GetAvlBalanceREQDTO getAvlBalanceDTO) {
        ResponseModel responseModel = ResponseModel.builder().build();
        LpbResCode lpbResCode = LpbResCode.builder().build();
        try {
            getAvlBalanceDTO.getFCubsHeader().setSource(serverConfigProperties.getHeaderSource());
            getAvlBalanceDTO.getFCubsHeader().setPassword(serverConfigProperties.getHeaderPassword());
            getAvlBalanceDTO.getFCubsHeader().setUbsComp(serverConfigProperties.getHeaderUbsComp());
            getAvlBalanceDTO.getFCubsHeader().setUserId(serverConfigProperties.getHeaderUserId());
            getAvlBalanceDTO.getFCubsHeader().setService(serverConfigProperties.getHeaderService());
            getAvlBalanceDTO.getFCubsHeader().setOperation(serverConfigProperties.getHeaderOperationGetAvlBalance());
            getAvlBalanceDTO.getFCubsHeader().setFunctionId(serverConfigProperties.getHeaderFuntionId());
            getAvlBalanceDTO.getFCubsHeader().setAction(serverConfigProperties.getHeaderAction());

            List<EsbServiceProcess> serviceInfo = iEsbService.getServiceInfo(Constant.SERVICE_ID, Constant.PRODUCT_REQUEST_TXN);
            String urlAPI = serviceInfo.get(0).getUrlApi() + serviceInfo.get(0).getConnectorUrl();
            SOAPMessage soapResponse = SoapGetAvlBalance.callSoapWebServiceFCUBSInternal(urlAPI, "", getAvlBalanceDTO);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            soapResponse.writeTo(out);
            String strMsg = new String(out.toByteArray());

            DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(strMsg)));
            responseModel = getDataAvlBalance(doc);
            log.info(getAvlBalanceDTO.getFCubsHeader().getMsgId() + " GetAvlBalance: " + responseModel.getResCode().getErrorDesc());
            return responseModel;
        } catch (NullPointerException e) {
            log.info(getAvlBalanceDTO.getFCubsHeader().getMsgId() + " GetAvlBalance: " + e);
            lpbResCode.setErrorCode(ErrorMessage.INPUT_ERROR.label);
            lpbResCode.setErrorDesc(ErrorMessage.INPUT_ERROR.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        } catch (Exception e) {
            log.info("Exception GetAvlBalance: " + e);
            lpbResCode.setErrorCode(ErrorMessage.FAIL.label);
            lpbResCode.setErrorDesc(ErrorMessage.FAIL.description);
            responseModel.setResCode(lpbResCode);
            throw new CommonException(responseModel);
        }
    }

}
