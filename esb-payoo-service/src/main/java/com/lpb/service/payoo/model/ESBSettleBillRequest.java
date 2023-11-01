package com.lpb.service.payoo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ESBSettleBillRequest extends AbstractBody{
    @JsonProperty(value = "header", required = true)
    private Header header;

    @JsonProperty(value = "body", required = true)
    private Body body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Header extends AbstractBody {
        @JsonProperty(value = "source", required = false)
        private String source;

        @JsonProperty(value = "ubscomp", required = false)
        private String ubscomp;

        @JsonProperty(value = "referenceNo", required = false)
        private String referenceNo;

        @JsonProperty(value = "msgid", required = true)
        private String msgId;

        @JsonProperty(value = "correlid", required = false)
        private String correlId;

        @JsonProperty(value = "userid", required = false)
        private String userId;

        @JsonProperty(value = "branch", required = false)
        private String branch;

        @JsonProperty(value = "moduleid", required = false)
        private String moduleId;

        @JsonProperty(value = "service", required = false)
        private String service;

        @JsonProperty(value = "operation", required = false)
        private String operation;

        @JsonProperty(value = "sourceOperation", required = false)
        private String sourceOperation;

        @JsonProperty(value = "sourceUserid", required = false)
        private String sourceUserId;

        @JsonProperty(value = "destination", required = true)
        private String destination;

        @JsonProperty(value = "multitripid", required = false)
        private String multitripId;

        @JsonProperty(value = "functionid", required = false)
        private String functionId;

        @JsonProperty(value = "action", required = false)
        private String action;

        @JsonProperty(value = "msgstat", required = false)
        private String msgStat;

        @JsonProperty(value = "password", required = false)
        private String password;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getUbscomp() {
            return ubscomp;
        }

        public void setUbscomp(String ubscomp) {
            this.ubscomp = ubscomp;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getCorrelId() {
            return correlId;
        }

        public void setCorrelId(String correlId) {
            this.correlId = correlId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getModuleId() {
            return moduleId;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getSourceOperation() {
            return sourceOperation;
        }

        public void setSourceOperation(String sourceOperation) {
            this.sourceOperation = sourceOperation;
        }

        public String getSourceUserId() {
            return sourceUserId;
        }

        public void setSourceUserId(String sourceUserId) {
            this.sourceUserId = sourceUserId;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getMultitripId() {
            return multitripId;
        }

        public void setMultitripId(String multitripId) {
            this.multitripId = multitripId;
        }

        public String getFunctionId() {
            return functionId;
        }

        public void setFunctionId(String functionId) {
            this.functionId = functionId;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getMsgStat() {
            return msgStat;
        }

        public void setMsgStat(String msgStat) {
            this.msgStat = msgStat;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Body extends AbstractBody {
        @JsonProperty(value = "SettleBill", required = false)
        private SettleBill settleBill;

        @JsonCreator
        public Body(SettleBill settleBill){
            this.settleBill = settleBill;
        }

        public SettleBill getSettleBill() {
            return settleBill;
        }

        public void setSettleBill(SettleBill settleBill) {
            this.settleBill = settleBill;
        }
    }
    public static class SettleBill extends AbstractBody {
        @JsonProperty(value = "confirmTrn", required = false)
        private String confirmTrn;

        @JsonProperty(value = "customerNo", required = false)
        private String customerNo;

        @JsonProperty(value = "trnBrn", required = false)
        private String trnBrn;

        @JsonProperty(value = "trnDesc", required = false)
        private String trnDesc;

        @JsonProperty(value = "transactionId", required = false)
        private String transactionId;

        @JsonProperty(value = "partner", required = false)
        private Partner partner;
        @JsonProperty(value = "service", required = false)
        private Service service;

        @JsonProperty(value = "listBillInfo", required = false)
        private List<BillInfo> listbillInfo;

        @JsonProperty(value = "PostInfo", required = false)
        private List<PostInfo> postInfo;

        @JsonProperty(value = "customerInfo", required = false)
        private CusInfo customerInfo;

        @JsonProperty(value = "settleAccountInfo", required = false)
        private AccInfo settleAccountInfo;
        @JsonCreator
        public SettleBill(String confirmTrn, String customerNo, String trnBrn, String trnDesc, String transactionId, Partner partner, Service service, List<BillInfo> listbillInfo, List<PostInfo> postInfo, CusInfo customerInfo, AccInfo settleAccountInfo){
            this.confirmTrn = confirmTrn;
            this.customerNo = customerNo;
            this.trnBrn = trnBrn;
            this.trnDesc = trnDesc;
            this.transactionId = transactionId;
            this.partner = partner;
            this.service = service;
            this.listbillInfo = listbillInfo;
            this.postInfo = postInfo;
            this.customerInfo = customerInfo;
            this.settleAccountInfo = settleAccountInfo;
        }

        public String getConfirmTrn() {
            return confirmTrn;
        }

        public void setConfirmTrn(String confirmTrn) {
            this.confirmTrn = confirmTrn;
        }

        public String getCustomerNo() {
            return customerNo;
        }

        public void setCustomerNo(String customerNo) {
            this.customerNo = customerNo;
        }

        public String getTrnBrn() {
            return trnBrn;
        }

        public void setTrnBrn(String trnBrn) {
            this.trnBrn = trnBrn;
        }

        public String getTrnDesc() {
            return trnDesc;
        }

        public void setTrnDesc(String trnDesc) {
            this.trnDesc = trnDesc;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public Partner getPartner() {
            return partner;
        }

        public void setPartner(Partner partner) {
            this.partner = partner;
        }

        public Service getService() {
            return service;
        }

        public void setService(Service service) {
            this.service = service;
        }

        public List<BillInfo> getListbillInfo() {
            return listbillInfo;
        }

        public void setListbillInfo(List<BillInfo> listbillInfo) {
            this.listbillInfo = listbillInfo;
        }

        public List<PostInfo> getPostInfo() {
            return postInfo;
        }

        public void setPostInfo(List<PostInfo> postInfo) {
            this.postInfo = postInfo;
        }

        public CusInfo getCustomerInfo() {
            return customerInfo;
        }

        public void setCustomerInfo(CusInfo customerInfo) {
            this.customerInfo = customerInfo;
        }

        public AccInfo getSettleAccountInfo() {
            return settleAccountInfo;
        }

        public void setSettleAccountInfo(AccInfo settleAccountInfo) {
            this.settleAccountInfo = settleAccountInfo;
        }
    }

    public static class Partner extends AbstractBody {
        @JsonProperty(value = "txnRefNo", required = false)
        private String txnRefNo;

        @JsonProperty(value = "txnDatetime", required = false)
        private String txnDatetime;

        @JsonProperty(value = "txnCode", required = false)
        private String txnCode;

        @JsonProperty(value = "chanel", required = false)
        private String chanel;

        public Partner(String txnRefNo, String txnDatetime, String txnCode, String chanel){
            this.txnRefNo = txnRefNo;
            this.txnDatetime = txnDatetime;
            this.txnCode = txnCode;
            this.chanel = chanel;
        }

        public String getTxnRefNo() {
            return txnRefNo;
        }

        public void setTxnRefNo(String txnRefNo) {
            this.txnRefNo = txnRefNo;
        }

        public String getTxnDatetime() {
            return txnDatetime;
        }

        public void setTxnDatetime(String txnDatetime) {
            this.txnDatetime = txnDatetime;
        }

        public String getTxnCode() {
            return txnCode;
        }

        public void setTxnCode(String txnCode) {
            this.txnCode = txnCode;
        }

        public String getChanel() {
            return chanel;
        }

        public void setChanel(String chanel) {
            this.chanel = chanel;
        }
    }


    public static class Service extends AbstractBody {
        @JsonProperty(value = "serviceId", required = false)
        private String serviceId;

        @JsonProperty(value = "productCode", required = true)
        private String productCode;

        @JsonProperty(value = "requestAccount", required = true)
        private String requestAccount;

        @JsonProperty(value = "receiveAccount", required = false)
        private String receiveAccount;

        @JsonProperty(value = "merchantId", required = true)
        private String merchantId;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getRequestAccount() {
            return requestAccount;
        }

        public void setRequestAccount(String requestAccount) {
            this.requestAccount = requestAccount;
        }

        public String getReceiveAccount() {
            return receiveAccount;
        }

        public void setReceiveAccount(String receiveAccount) {
            this.receiveAccount = receiveAccount;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }
    }

    public static class PostInfo extends AbstractBody {
        @JsonProperty(value = "sourceAcc", required = false)
        private String sourceAcc;

        @JsonProperty(value = "acNo", required = false)
        private String ac_no;

        @JsonProperty(value = "branchCode", required = false)
        private String branchCode;

        @JsonProperty(value = "ccy", required = false)
        private String ccy;

        @JsonProperty(value = "fcyAmount", required = false)
        private String fcyAmount;

        @JsonProperty(value = "lcyAmount", required = false)
        private String lcyAmount;

        @JsonProperty(value = "drcrInd", required = false)
        private String drcrInd;

        @JsonProperty(value = "amountTag", required = false)
        private String amountTag;

        @JsonProperty(value = "bankCode", required = false)
        private String bankCode;

        @JsonProperty(value = "bankName", required = false)
        private String bankName;

        @JsonProperty(value = "makerid", required = false)
        private String makerId;

        @JsonProperty(value = "checkerid", required = false)
        private String checkerId;

        @JsonProperty(value = "infoSourceAcc", required = false)
        private String infoSourceAcc;

        @JsonProperty(value = "infoAcNo", required = false)
        private String infoAcNo;

        @JsonProperty(value = "postDesc", required = false)
        private String postDesc;

        @JsonProperty(value = "postRefNo", required = false)
        private String postRefNo;

        public String getSourceAcc() {
            return sourceAcc;
        }

        public void setSourceAcc(String sourceAcc) {
            this.sourceAcc = sourceAcc;
        }

        public String getAc_no() {
            return ac_no;
        }

        public void setAc_no(String ac_no) {
            this.ac_no = ac_no;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getCcy() {
            return ccy;
        }

        public void setCcy(String ccy) {
            this.ccy = ccy;
        }

        public String getFcyAmount() {
            return fcyAmount;
        }

        public void setFcyAmount(String fcyAmount) {
            this.fcyAmount = fcyAmount;
        }

        public String getLcyAmount() {
            return lcyAmount;
        }

        public void setLcyAmount(String lcyAmount) {
            this.lcyAmount = lcyAmount;
        }

        public String getDrcrInd() {
            return drcrInd;
        }

        public void setDrcrInd(String drcrInd) {
            this.drcrInd = drcrInd;
        }

        public String getAmountTag() {
            return amountTag;
        }

        public void setAmountTag(String amountTag) {
            this.amountTag = amountTag;
        }

        public String getBankCode() {
            return bankCode;
        }

        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getMakerId() {
            return makerId;
        }

        public void setMakerId(String makerId) {
            this.makerId = makerId;
        }

        public String getCheckerId() {
            return checkerId;
        }

        public void setCheckerId(String checkerId) {
            this.checkerId = checkerId;
        }

        public String getInfoSourceAcc() {
            return infoSourceAcc;
        }

        public void setInfoSourceAcc(String infoSourceAcc) {
            this.infoSourceAcc = infoSourceAcc;
        }

        public String getInfoAcNo() {
            return infoAcNo;
        }

        public void setInfoAcNo(String infoAcNo) {
            this.infoAcNo = infoAcNo;
        }

        public String getPostDesc() {
            return postDesc;
        }

        public void setPostDesc(String postDesc) {
            this.postDesc = postDesc;
        }

        public String getPostRefNo() {
            return postRefNo;
        }

        public void setPostRefNo(String postRefNo) {
            this.postRefNo = postRefNo;
        }
    }
    public static class CusInfo extends AbstractBody {
        @JsonProperty(value = "custName", required = false)
        private String custName;

        @JsonProperty(value = "custId", required = false)
        private String custId;

        @JsonProperty(value = "custDesc", required = false)
        private String custDesc;

        @JsonProperty(value = "custType", required = false)
        private String custType;

        @JsonProperty(value = "custStatus", required = false)
        private String custStatus;

        @JsonProperty(value = "custEmail", required = false)
        private String custEmail;

        @JsonProperty(value = "custMobile", required = false)
        private String custMobile;

        @JsonProperty(value = "uidValue", required = false)
        private String uidValue;

        @JsonProperty(value = "providerId", required = false)
        private String providerId;

        @JsonProperty(value = "otherInfo", required = false)
        private String otherInfo;

        public CusInfo(String custName, String custId, String custDesc, String custType, String custStatus, String custEmail, String custMobile, String uidValue, String providerId, String otherInfo){
            this.custName = custName;
            this.custId = custId;
            this.custDesc = custDesc;
            this.custType = custType;
            this.custStatus = custStatus;
            this.custEmail = custEmail;
            this.custMobile = custMobile;
            this.uidValue = uidValue;
            this.providerId = providerId;
            this.otherInfo = otherInfo;

        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getCustId() {
            return custId;
        }

        public void setCustId(String custId) {
            this.custId = custId;
        }

        public String getCustDesc() {
            return custDesc;
        }

        public void setCustDesc(String custDesc) {
            this.custDesc = custDesc;
        }

        public String getCustType() {
            return custType;
        }

        public void setCustType(String custType) {
            this.custType = custType;
        }

        public String getCustStatus() {
            return custStatus;
        }

        public void setCustStatus(String custStatus) {
            this.custStatus = custStatus;
        }

        public String getCustEmail() {
            return custEmail;
        }

        public void setCustEmail(String custEmail) {
            this.custEmail = custEmail;
        }

        public String getCustMobile() {
            return custMobile;
        }

        public void setCustMobile(String custMobile) {
            this.custMobile = custMobile;
        }

        public String getUidValue() {
            return uidValue;
        }

        public void setUidValue(String uidValue) {
            this.uidValue = uidValue;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getOtherInfo() {
            return otherInfo;
        }

        public void setOtherInfo(String otherInfo) {
            this.otherInfo = otherInfo;
        }
    }

    public static class AccInfo extends AbstractBody {
        @JsonProperty(value = "settleAcNo", required = false)
        private String settleAcNo;

        @JsonProperty(value = "settleAcBrn", required = false)
        private String settleAcBrn;

        @JsonProperty(value = "settleAcDesc", required = false)
        private String settleAcDesc;

        @JsonProperty(value = "settleCustomerNo", required = false)
        private String settleCustomerNo;

        @JsonProperty(value = "settleMerchant", required = false)
        private String settleMerchant;

        public String getSettleAcNo() {
            return settleAcNo;
        }

        public void setSettleAcNo(String settleAcNo) {
            this.settleAcNo = settleAcNo;
        }

        public String getSettleAcBrn() {
            return settleAcBrn;
        }

        public void setSettleAcBrn(String settleAcBrn) {
            this.settleAcBrn = settleAcBrn;
        }

        public String getSettleAcDesc() {
            return settleAcDesc;
        }

        public void setSettleAcDesc(String settleAcDesc) {
            this.settleAcDesc = settleAcDesc;
        }

        public String getSettleCustomerNo() {
            return settleCustomerNo;
        }

        public void setSettleCustomerNo(String settleCustomerNo) {
            this.settleCustomerNo = settleCustomerNo;
        }

        public String getSettleMerchant() {
            return settleMerchant;
        }

        public void setSettleMerchant(String settleMerchant) {
            this.settleMerchant = settleMerchant;
        }
    }

    public static class BillInfo extends AbstractBody {
        @JsonProperty(value = "billCode", required = false)
        private String billCode;

        @JsonProperty(value = "billId", required = false)
        private String billId;

        @JsonProperty(value = "billDesc", required = false)
        private String billDesc;

        @JsonProperty(value = "billType", required = false)
        private String billType;

        @JsonProperty(value = "billStatus", required = false)
        private String billStatus;

        @JsonProperty(value = "billAmount", required = false)
        private String billAmount;

        @JsonProperty(value = "settledAmount", required = false)
        private String settledAmount;

        @JsonProperty(value = "paymentMethod", required = false)
        private String paymentMethod;

        @JsonProperty(value = "otherInfo", required = false)
        private String otherInfo;

        public String getBillCode() {
            return billCode;
        }

        public void setBillCode(String billCode) {
            this.billCode = billCode;
        }

        public String getBillId() {
            return billId;
        }

        public void setBillId(String billId) {
            this.billId = billId;
        }

        public String getBillDesc() {
            return billDesc;
        }

        public void setBillDesc(String billDesc) {
            this.billDesc = billDesc;
        }

        public String getBillType() {
            return billType;
        }

        public void setBillType(String billType) {
            this.billType = billType;
        }

        public String getBillStatus() {
            return billStatus;
        }

        public void setBillStatus(String billStatus) {
            this.billStatus = billStatus;
        }

        public String getBillAmount() {
            return billAmount;
        }

        public void setBillAmount(String billAmount) {
            this.billAmount = billAmount;
        }

        public String getSettledAmount() {
            return settledAmount;
        }

        public void setSettledAmount(String settledAmount) {
            this.settledAmount = settledAmount;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getOtherInfo() {
            return otherInfo;
        }

        public void setOtherInfo(String otherInfo) {
            this.otherInfo = otherInfo;
        }
    }

}

