package com.lpb.esb.service.config.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lpb.esb.service.common.model.request.transaction.*;
import com.lpb.esb.service.common.utils.StringUtils;
import com.lpb.esb.service.config.repository.SettleBillRepository;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Log4j2
public class Utils {
    public static String createXmlServiceDTO(ServiceDTO serviceDTO, String msgId) {
        StringBuilder bu = new StringBuilder();
        bu.append("<SERVICE>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getServiceId())) {
            bu.append("<SERVICE_ID>");
            bu.append(serviceDTO.getServiceId());
            bu.append("</SERVICE_ID>");
        }
        bu.append("<PRODUCT>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getProductCode())) {
            bu.append("<PRODUCT_CODE>");
            bu.append(serviceDTO.getProductCode());
            bu.append("</PRODUCT_CODE>");
        }
        bu.append("</PRODUCT>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getMerchantId())) {
            bu.append("<MERCHANT_ID>");
            bu.append(serviceDTO.getServiceId());
            bu.append("</MERCHANT_ID>");
        }
        bu.append("</SERVICE>");
        log.info(msgId + " CreateXmlServiceDTO: " + bu.toString());
        return bu.toString();
    }

    public static String createXmlCustomer(CustomerDTO customerNo, String msgId) {
        try {
            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            ObjectMapper objectMapper = new XmlMapper(xmlModule);
            String xml = objectMapper.writeValueAsString(customerNo);
            log.info(msgId + " CreateXmlCustomer: " + xml);
            return xml;
        } catch (Exception e) {
            log.info(msgId + " Exception CreateXmlCustomer: " + e);
            return null;
        }
    }

    public static String createXmlPartner(PartnerDTO partnerDTO, String msgId) {
        try {
            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            ObjectMapper objectMapper = new XmlMapper(xmlModule);
            String xml = objectMapper.writeValueAsString(partnerDTO);
            log.info(msgId + " CreateXmlPartner: " + xml);
            return xml;
        } catch (Exception e) {
            log.info(msgId + " Exception CreateXmlPartner: " + e);
            return null;
        }
    }

    public static String createXmlPostInfo(List<PostInfoDTO> listPost, String msgId) {
        try {
            StringBuilder bu = new StringBuilder();
            bu.append("<TRN_POST>");
            for (PostInfoDTO postInfoDTO : listPost) {
                bu.append("<POST_INFO>");
                bu.append("<ACCOUNT>");
                if (!StringUtils.isNullOrBlank(postInfoDTO.getAcNo())) {
                    bu.append("<AC_NO>");
                    bu.append(postInfoDTO.getAcNo());
                    bu.append("</AC_NO>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getCcy())) {
                    bu.append("<CCY>");
                    bu.append(postInfoDTO.getCcy());
                    bu.append("</CCY>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getBranchCode())) {
                    bu.append("<BRANCH_CODE>");
                    bu.append(postInfoDTO.getBranchCode());
                    bu.append("</BRANCH_CODE>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getCustomerNo())) {
                    bu.append("<CUSTOMER_NO>");
                    bu.append(postInfoDTO.getCustomerNo());
                    bu.append("</CUSTOMER_NO>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getCardNo())) {
                    bu.append("<REF_ACCOUNT><AC_NO>");
                    bu.append(postInfoDTO.getCardNo());
                    bu.append("</AC_NO></REF_ACCOUNT>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getAcDesc())) {
                    bu.append("<AC_DESC>");
                    bu.append(postInfoDTO.getAcDesc());
                    bu.append("</AC_DESC>");
                }
                bu.append("</ACCOUNT>");

                if (!StringUtils.isNullOrBlank(postInfoDTO.getClrService())) {
                    bu.append("<CLR_SERVICE>");
                    bu.append(postInfoDTO.getClrService());
                    bu.append("</CLR_SERVICE>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getClrProduct())) {
                    bu.append("<CLR_PRODUCT>");
                    bu.append(postInfoDTO.getClrProduct());
                    bu.append("</CLR_PRODUCT>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getCurrNo())) {
                    bu.append("<CURR_NO>");
                    bu.append(postInfoDTO.getCurrNo());
                    bu.append("</CURR_NO>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getTxnCcy())) {
                    bu.append("<TXN_CCY>");
                    bu.append(postInfoDTO.getTxnCcy());
                    bu.append("</TXN_CCY>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getFcyAmount())) {
                    bu.append("<FCY_AMOUNT>");
                    bu.append(postInfoDTO.getFcyAmount());
                    bu.append("</FCY_AMOUNT>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getExcRate())) {
                    bu.append("<EXC_RATE>");
                    bu.append(postInfoDTO.getExcRate());
                    bu.append("</EXC_RATE>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getLcyAmount())) {
                    bu.append("<LCY_AMOUNT>");
                    bu.append(postInfoDTO.getLcyAmount());
                    bu.append("</LCY_AMOUNT>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getDrcrInd())) {
                    bu.append("<DRCR_IND>");
                    bu.append(postInfoDTO.getDrcrInd());
                    bu.append("</DRCR_IND>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getAmountTag())) {
                    bu.append("<AMOUNT_TAG>");
                    bu.append(postInfoDTO.getAmountTag());
                    bu.append("</AMOUNT_TAG>");
                }
                if (!StringUtils.isNullOrBlank(postInfoDTO.getDetailOfCharge())) {
                    bu.append("<DETAIL_OF_CHARGE>");
                    bu.append(postInfoDTO.getAmountTag());
                    bu.append("</DETAIL_OF_CHARGE>");
                }
                bu.append("</POST_INFO>");
            }
            bu.append("</TRN_POST>");
            log.info(msgId + " CreateXmlPostInfo: " + bu.toString());
            return bu.toString();
        } catch (Exception e) {
            log.info(msgId + " Exception CreateXmlPostInfo: " + e);
            return null;
        }
    }

    public static String buildCustomerNo(String customerNo, String msgId) {
        String customerTag = "<CUSTOMER><CUSTOMER_NO>%s</CUSTOMER_NO></CUSTOMER>";
        log.info(msgId + " BuildCustomerNo: " + String.format(customerTag, customerNo));
        return String.format(customerTag, customerNo);
    }

    public static String buildServiceInfo(String serviceInfo) {
        String service = "<SERVICE_INFO>%s</SERVICE_INFO>";
        return String.format(service, serviceInfo);
    }

    public static List<SettleBillTransferDTO> convertSettleBillTransfer(List<SettleBillRepository.TransactionSettleBillTransfer>
                                                                            listSettleBillTransfer) {
        try {
            List<SettleBillTransferDTO> list = new ArrayList<>();
            for (SettleBillRepository.TransactionSettleBillTransfer item : listSettleBillTransfer) {

                ServiceDTO serviceDTO = ServiceDTO.builder()
                    .serviceId(item.getServiceId())
                    .productCode(item.getProductCode())
                    .build();

                PartnerDTO partnerDTO = PartnerDTO.builder()
                    .txnCode(item.getRequestTrnCode())
                    .txnDatetime(item.getRequestDt())
                    .chanel(item.getRequestChanel())
                    .txnRefNo(item.getRequestRefNo())
                    .build();

                SettleBillTransferDTO settleBillTransferDTO = SettleBillTransferDTO.builder()
                    .transactionId(item.getTransactionId())
                    .lastEventSeqNo(item.getLastEventSeqNo())
                    .lastProcEsr(item.getLastProcEsr())
                    .trnBranch(item.getTrnBranch())
                    .ebkUser(item.getEbkUser())
                    .customerNo(item.getCustomerNo())
                    .requestBy(item.getRequestBy())
                    .responseBy(item.getResponseBy())
                    .trnDesc(item.getTrnDesc())
                    .partnerInfo(partnerDTO)
                    .serviceInfo(serviceDTO)
                    .serviceRowId(item.getServiceRowId())
                    .txnAccount(convertXmlToListAccountInfoDTO(item.getTxnAccount().getSubString(1, (int) item.getTxnAccount().length())))
                    .txnAmount(item.getTxnAmount())
                    .txnCcy(item.getTxnCcy())
                    .drcrInd(item.getDrcrInd())
                    .traceNo(item.getTraceNo())
                    .connectorName(item.getConnectorName())
                    .urlApi(item.getUrlApi())
                    .connectorUrl(item.getConnectorUrl())
                    .connectorPort(item.getConnectorPort())
                    .methodPort(item.getMethodPort())
                    .serviceType(item.getServiceType())
                    .executedBy(item.getExecutedBy())
                    .build();
                list.add(settleBillTransferDTO);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(" Exception ConvertSettleBillTransfer: " + e);
            return null;
        }
    }

    public static List<SettleBillingDTO> convertSettleBilling(List<SettleBillRepository.TransactionSettleBilling>
                                                                  listTransactionSettleBilling) {
        try {
            List<SettleBillingDTO> list = new ArrayList<>();
            for (SettleBillRepository.TransactionSettleBilling item : listTransactionSettleBilling) {

                ServiceDTO serviceDTO = ServiceDTO.builder()
                    .serviceId(item.getServiceId())
                    .productCode(item.getProductCode())
                    .requestAccount(getValueXml(item.getRequestAccount(), "REQUEST_ACCOUNT"))
                    .receiveAccount(getValueXml(item.getReceiveAccount(), "RECEIVE_ACCOUNT"))
                    .build();

                PartnerDTO partnerDTO = PartnerDTO.builder()
                    .txnCode(item.getRequestTrnCode())
                    .txnDatetime(item.getRequestDt())
                    .chanel(item.getRequestChanel())
                    .txnRefNo(item.getRequestRefNo())
                    .build();

                SettleBillingDTO settleBillingDTO = SettleBillingDTO.builder()
                    .transactionId(item.getTransactionId())
                    .lastEventSeqNo(item.getLastEventSeqNo())
                    .lastProcEsr(item.getLastProcEsr())
                    .trnBranch(item.getTrnBranch())
                    .ebkUser(item.getEbkUser())
                    .customerNo(item.getCustomerNo())
                    .requestBy(item.getRequestBy())
                    .serviceProvider(item.getServiceProvider())
                    .confirmTrn(item.getReqConfirmStat())
                    .trnDesc(item.getTrnDesc())
                    .traceNo(item.getTraceNo())
                    .serviceRowId(item.getServiceRowId())
                    .serviceInfo(serviceDTO)
                    .partnerInfo(partnerDTO)
                    .billInfo(convertXmlToListBillDTO(item.getBillInfo().getSubString(1, (int) item.getBillInfo().length())))
                    .totalAmt(item.getTotalAmt())
                    .connectorName(item.getConnectorName())
                    .urlApi(item.getUrlApi())
                    .connectorUrl(item.getConnectorUrl())
                    .connectorPort(item.getConnectorPort())
                    .methodAction(item.getMethodAction())
                    .serviceType(item.getServiceType())
                    .executedBy(item.getExecutedBy())
                    .build();
                list.add(settleBillingDTO);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            log.info(" Exception ConvertSettleBilling: " + e);
            return null;
        }
    }

    public static Document convertStringToXMLDocument(String xmlString) {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();
            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        } catch (Exception e) {
            log.info("Exception ConvertStringToXMLDocument: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static List<AccountInfoDTO> convertXmlToListAccountInfoDTO(String xml) {
        Document doc = Utils.convertStringToXMLDocument(xml);
        NodeList listAccountInfoDTO = doc.getElementsByTagName("ACCOUNT");
        List<AccountInfoDTO> list = new ArrayList<>();

        for (int temp = 0; temp < listAccountInfoDTO.getLength(); temp++) {
            Node node = listAccountInfoDTO.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                AccountInfoDTO accountInfoDTO = AccountInfoDTO.builder().build();
                if (eElement.getElementsByTagName("AC_NO").getLength() > 0)
                    accountInfoDTO.setAcNo(eElement.getElementsByTagName("AC_NO").item(0).getTextContent());
                if (eElement.getElementsByTagName("AMOUNT_TAG").getLength() > 0)
                    accountInfoDTO.setAmountTag(eElement.getElementsByTagName("AMOUNT_TAG").item(0).getTextContent());
                if (eElement.getElementsByTagName("BRANCH_CODE").getLength() > 0)
                    accountInfoDTO.setBranchCode(eElement.getElementsByTagName("BRANCH_CODE").item(0).getTextContent());
                if (eElement.getElementsByTagName("CCY").getLength() > 0)
                    accountInfoDTO.setCcy(eElement.getElementsByTagName("CCY").item(0).getTextContent());
                if (eElement.getElementsByTagName("DRCR_IND").getLength() > 0)
                    accountInfoDTO.setDrcrInd(eElement.getElementsByTagName("DRCR_IND").item(0).getTextContent());
                if (eElement.getElementsByTagName("FCY_AMOUNT").getLength() > 0)
                    accountInfoDTO.setFcyAmount(eElement.getElementsByTagName("FCY_AMOUNT").item(0).getTextContent());
                if (eElement.getElementsByTagName("LCY_AMOUNT").getLength() > 0)
                    accountInfoDTO.setLcyAmount(eElement.getElementsByTagName("LCY_AMOUNT").item(0).getTextContent());
                if (eElement.getElementsByTagName("SOURCE_ACC").getLength() > 0)
                    accountInfoDTO.setSourceAcc(eElement.getElementsByTagName("SOURCE_ACC").item(0).getTextContent());
                if (eElement.getElementsByTagName("BANK_CODE").getLength() > 0)
                    accountInfoDTO.setBankCode(eElement.getElementsByTagName("BANK_CODE").item(0).getTextContent());
                if (eElement.getElementsByTagName("BANK_NAME").getLength() > 0)
                    accountInfoDTO.setBankName(eElement.getElementsByTagName("BANK_NAME").item(0).getTextContent());
                if (eElement.getElementsByTagName("MAKERID").getLength() > 0)
                    accountInfoDTO.setMakerId(eElement.getElementsByTagName("MAKERID").item(0).getTextContent());
                if (eElement.getElementsByTagName("CHECKERID").getLength() > 0)
                    accountInfoDTO.setCheckerId(eElement.getElementsByTagName("CHECKERID").item(0).getTextContent());
                if (eElement.getElementsByTagName("INFO_SOURCE_ACC").getLength() > 0)
                    accountInfoDTO.setInfoSourceAcc(eElement.getElementsByTagName("INFO_SOURCE_ACC").item(0).getTextContent());
                if (eElement.getElementsByTagName("INFO_AC_NO").getLength() > 0)
                    accountInfoDTO.setInfoAcNo(eElement.getElementsByTagName("INFO_AC_NO").item(0).getTextContent());
                if (eElement.getElementsByTagName("POST_DESC").getLength() > 0)
                    accountInfoDTO.setPostDesc(eElement.getElementsByTagName("POST_DESC").item(0).getTextContent());
                if (eElement.getElementsByTagName("POST_REF_NO").getLength() > 0)
                    accountInfoDTO.setPostRefNo(eElement.getElementsByTagName("POST_REF_NO").item(0).getTextContent());
                list.add(accountInfoDTO);
            }
        }
        return list;
    }

    public static List<BillDTO> convertXmlToListBillDTO(String xml) {
        Document doc = Utils.convertStringToXMLDocument(xml);
        NodeList listBillDTO = doc.getElementsByTagName("BILL");
        List<BillDTO> list = new ArrayList<>();

        for (int temp = 0; temp < listBillDTO.getLength(); temp++) {
            Node node = listBillDTO.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                BillDTO billDTO = BillDTO.builder().build();
                if (eElement.getElementsByTagName("BILL_CODE").getLength() > 0)
                    billDTO.setBillCode(eElement.getElementsByTagName("BILL_CODE").item(0).getTextContent());
                if (eElement.getElementsByTagName("BILL_DESC").getLength() > 0)
                    billDTO.setBillDesc(eElement.getElementsByTagName("BILL_DESC").item(0).getTextContent());
                if (eElement.getElementsByTagName("BILL_AMOUNT").getLength() > 0)
                    billDTO.setBillAmount(eElement.getElementsByTagName("BILL_AMOUNT").item(0).getTextContent());
                if (eElement.getElementsByTagName("AMT_UNIT").getLength() > 0)
                    billDTO.setAmtUnit(eElement.getElementsByTagName("AMT_UNIT").item(0).getTextContent());
                if (eElement.getElementsByTagName("SETTLED_AMOUNT").getLength() > 0)
                    billDTO.setSettledAmount(eElement.getElementsByTagName("SETTLED_AMOUNT").item(0).getTextContent());
                if (eElement.getElementsByTagName("OTHER_INFO").getLength() > 0)
                    billDTO.setOtherInfo(eElement.getElementsByTagName("OTHER_INFO").item(0).getTextContent());
                if (eElement.getElementsByTagName("BILL_TYPE").getLength() > 0)
                    billDTO.setBillType(eElement.getElementsByTagName("BILL_TYPE").item(0).getTextContent());
                if (eElement.getElementsByTagName("BILL_STATUS").getLength() > 0)
                    billDTO.setBillStatus(eElement.getElementsByTagName("BILL_STATUS").item(0).getTextContent());
                if (eElement.getElementsByTagName("BILL_ID").getLength() > 0)
                    billDTO.setBillId(eElement.getElementsByTagName("BILL_ID").item(0).getTextContent());
                if (eElement.getElementsByTagName("PAYMENT_METHOD").getLength() > 0)
                    billDTO.setPaymentMethod(eElement.getElementsByTagName("PAYMENT_METHOD").item(0).getTextContent());
                list.add(billDTO);
            }
        }
        return list;
    }

    public static String getValueXml(String xml, String tagNamePath) {
        if (!StringUtils.isNullOrBlank(xml)) {
            Document doc = Utils.convertStringToXMLDocument(xml);
            NodeList nodeList = doc.getElementsByTagName(tagNamePath);
            String value = null;
            if (nodeList.getLength() > 0) {
                if (!StringUtils.isNullOrBlank(nodeList.item(0).getTextContent())) {
                    value = nodeList.item(0).getTextContent();
                }
            }
            return value;
        } else {
            return null;
        }
    }

    public static String genBillsXML(ServiceDTO serviceDTO, List<BillDTO> billInfo, String msgId) {
        try {
            StringBuilder bu = new StringBuilder();
            for (BillDTO bill : billInfo) {

                if (serviceDTO.getServiceId().equals("030006_16") && serviceDTO.getProductCode().equals("WTTHANHHOA")) {
                    log.info("hard code billamount for WTTHANHHOA");
                    bill.setBillAmount(bill.getSettledAmount());
                }

                JacksonXmlModule xmlModule = new JacksonXmlModule();
                xmlModule.setDefaultUseWrapper(false);
                ObjectMapper objectMapper = new XmlMapper(xmlModule);
                String xml = objectMapper.writeValueAsString(bill);
                bu.append(xml);
            }
            String result = String.format(genServiceSettleBillXml(serviceDTO), bu.toString());
            log.info(msgId + " GenBillsXML: " + result);
            return result;
        } catch (Exception e) {
            log.info(msgId + " Exception genBillsXML: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static String genServiceSettleBillXml(ServiceDTO serviceDTO) {
        StringBuilder bu = new StringBuilder();
        bu.append("<SERVICE>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getServiceId())) {
            bu.append("<SERVICE_ID>");
            bu.append(serviceDTO.getServiceId());
            bu.append("</SERVICE_ID>");
        }
        bu.append("<PRODUCT>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getProductCode())) {
            bu.append("<PRODUCT_CODE>");
            bu.append(serviceDTO.getProductCode());
            bu.append("</PRODUCT_CODE>");
        }
        if (!StringUtils.isNullOrBlank(serviceDTO.getMerchantId())) {
            bu.append("<MERCHANT_ID>");
            bu.append(serviceDTO.getServiceId());
            bu.append("</MERCHANT_ID>");
        }
        bu.append("</PRODUCT>");
        bu.append("<SERVICE_INFO>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getRequestAccount())) {
            bu.append("<REQUEST_ACCOUNT>");
            bu.append(serviceDTO.getRequestAccount());
            bu.append("</REQUEST_ACCOUNT>");
        }
        if (!StringUtils.isNullOrBlank(serviceDTO.getReceiveAccount())) {
            bu.append("<RECEIVE_ACCOUNT>");
            bu.append(serviceDTO.getReceiveAccount());
            bu.append("</RECEIVE_ACCOUNT>");
        }
        bu.append("<BILLS>");
        bu.append("%s");
        bu.append("</BILLS>");
        bu.append("</SERVICE_INFO>");
        bu.append("</SERVICE>");
        return bu.toString();
    }


    public static String genTransferXML(ServiceDTO serviceDTO, List<PostInfoDTO> postInfoDTO, String msgId) {
        try {
            StringBuilder bu = new StringBuilder();
            for (PostInfoDTO postInfo : postInfoDTO) {
                JacksonXmlModule xmlModule = new JacksonXmlModule();
                xmlModule.setDefaultUseWrapper(false);
                ObjectMapper objectMapper = new XmlMapper(xmlModule);
                String xml = objectMapper.writeValueAsString(postInfo);
                bu.append(xml);
            }
            String result = String.format(genServiceSettleTransferXml(serviceDTO), bu.toString());
            log.info(msgId + " GenTransferXML: " + result);
            return result;
        } catch (Exception e) {
            log.info(msgId + " Exception genTransferXML: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static String genServiceSettleTransferXml(ServiceDTO serviceDTO) {
        StringBuilder bu = new StringBuilder();
        bu.append("<SERVICE>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getServiceId())) {
            bu.append("<SERVICE_ID>");
            bu.append(serviceDTO.getServiceId());
            bu.append("</SERVICE_ID>");
        }
        bu.append("<PRODUCT>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getProductCode())) {
            bu.append("<PRODUCT_CODE>");
            bu.append(serviceDTO.getProductCode());
            bu.append("</PRODUCT_CODE>");
        }
        if (!StringUtils.isNullOrBlank(serviceDTO.getMerchantId())) {
            bu.append("<MERCHANT_ID>");
            bu.append(serviceDTO.getServiceId());
            bu.append("</MERCHANT_ID>");
        }
        bu.append("</PRODUCT>");
        bu.append("<SERVICE_INFO>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getRequestAccount())) {
            bu.append("<REQUEST_ACCOUNT>");
            bu.append(serviceDTO.getRequestAccount());
            bu.append("</REQUEST_ACCOUNT>");
        }
        if (!StringUtils.isNullOrBlank(serviceDTO.getReceiveAccount())) {
            bu.append("<RECEIVE_ACCOUNT>");
            bu.append(serviceDTO.getReceiveAccount());
            bu.append("</RECEIVE_ACCOUNT>");
        }
        bu.append("<ACCOUNTS>");
        bu.append("%s");
        bu.append("</ACCOUNTS>");
        bu.append("</SERVICE_INFO>");
        bu.append("</SERVICE>");
        return bu.toString();
    }


    public static String genTransferRevertXML(ServiceDTO serviceDTO, List<BillDTO> billInfo, String msgId) {
        try {
            String result = String.format(genServiceRevertTransferXml(serviceDTO), billInfo.toString());
            log.info(msgId + " GenTransferRevertXML: " + result);
            return result;
        } catch (Exception e) {
            log.info(msgId + " Exception genTransferRevertXML: " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static String genServiceRevertTransferXml(ServiceDTO serviceDTO) {
        StringBuilder bu = new StringBuilder();
        bu.append("<SERVICE>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getServiceId())) {
            bu.append("<SERVICE_ID>");
            bu.append(serviceDTO.getServiceId());
            bu.append("</SERVICE_ID>");
        }
        bu.append("<PRODUCT>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getProductCode())) {
            bu.append("<PRODUCT_CODE>");
            bu.append(serviceDTO.getProductCode());
            bu.append("</PRODUCT_CODE>");
        }
        if (!StringUtils.isNullOrBlank(serviceDTO.getMerchantId())) {
            bu.append("<MERCHANT_ID>");
            bu.append(serviceDTO.getServiceId());
            bu.append("</MERCHANT_ID>");
        }
        bu.append("</PRODUCT>");
        bu.append("<SERVICE_INFO>");
        if (!StringUtils.isNullOrBlank(serviceDTO.getRequestAccount())) {
            bu.append("<REQUEST_ACCOUNT>");
            bu.append(serviceDTO.getRequestAccount());
            bu.append("</REQUEST_ACCOUNT>");
        }
        if (!StringUtils.isNullOrBlank(serviceDTO.getReceiveAccount())) {
            bu.append("<RECEIVE_ACCOUNT>");
            bu.append(serviceDTO.getReceiveAccount());
            bu.append("</RECEIVE_ACCOUNT>");
        }
        bu.append("%s");
        bu.append("</SERVICE_INFO>");
        bu.append("</SERVICE>");
        return bu.toString();
    }

    public static String genOtherRevertXML(ServiceDTO serviceDTO, List<PostInfoDTO> postInfoDTO, String msgId) {
        try {
            String result = String.format(genServiceRevertTransferXml(serviceDTO), postInfoDTO.toString());
            log.info(msgId + " GenTransferRevertXML: " + result);
            return result;
        } catch (Exception e) {
            log.info(msgId + " Exception genTransferRevertXML: " + e);
            e.printStackTrace();
            return null;
        }
    }

}
