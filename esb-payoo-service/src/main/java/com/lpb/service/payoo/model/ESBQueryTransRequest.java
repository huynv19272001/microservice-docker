package com.lpb.service.payoo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ESBQueryTransRequest extends AbstractBody{
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
        @JsonProperty(value = "service", required = false)
        private Service service;

        @JsonProperty(value = "transactionInfo", required = false)
        private TransInfo transactionInfo;

        @JsonCreator
        public Body(Service service, TransInfo transactionInfo){
            this.service = service;
            this.transactionInfo = transactionInfo;
        }

        public Service getService() {
            return service;
        }

        public void setService(Service service) {
            this.service = service;
        }

        public TransInfo getTransactionInfo() {
            return transactionInfo;
        }

        public void setTransactionInfo(TransInfo transactionInfo) {
            this.transactionInfo = transactionInfo;
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

    public static class TransInfo extends AbstractBody {
        @JsonProperty(value = "fromDt", required = false)
        private String fromDt;

        @JsonProperty(value = "toDt", required = false)
        private String toDt;

        @JsonProperty(value = "listTranInfo", required = false)
        private List<ListTransInfo> listTranInfo;

        @JsonCreator
        public TransInfo(String fromDt, String toDt, List<ListTransInfo> listTranInfo){
            this.fromDt = fromDt;
            this.toDt = toDt;
            this.listTranInfo = listTranInfo;
        }

        public String getFromDt() {
            return fromDt;
        }

        public void setFromDt(String fromDt) {
            this.fromDt = fromDt;
        }

        public String getToDt() {
            return toDt;
        }

        public void setToDt(String toDt) {
            this.toDt = toDt;
        }

        public List<ListTransInfo> getListTranInfo() {
            return listTranInfo;
        }

        public void setListTranInfo(List<ListTransInfo> listTranInfo) {
            this.listTranInfo = listTranInfo;
        }
    }

    public static class ListTransInfo extends AbstractBody {
        @JsonProperty(value = "senderCode", required = false)
        private String senderCode;

        @JsonProperty(value = "senderName", required = false)
        private String senderName;

        @JsonProperty(value = "receiverCode", required = false)
        private String receiverCode;

        @JsonProperty(value = "receiverName", required = false)
        private String receiverName;

        @JsonProperty(value = "msgRefid", required = false)
        private String msgRefid;

        @JsonProperty(value = "sendDate", required = false)
        private String sendDate;

        @JsonProperty(value = "originalCode", required = false)
        private String originalCode;

        @JsonProperty(value = "originalName", required = false)
        private String originalName;

        @JsonProperty(value = "trnRefId", required = false)
        private String trnRefId;

        @JsonProperty(value = "sendBank", required = false)
        private String sendBank;

        @JsonProperty(value = "receiveBank", required = false)
        private String receiveBank;

        @JsonProperty(value = "createdDt", required = false)
        private String createdDt;

        @JsonProperty(value = "makerid", required = false)
        private String makerId;

        @JsonProperty(value = "checkerid", required = false)
        private String checkerId;

        @JsonProperty(value = "requestRefNo", required = false)
        private String requestRefNo;

        @JsonProperty(value = "trnDt", required = false)
        private String trnDt;

        @JsonProperty(value = "ccy", required = false)
        private String ccy;

        @JsonProperty(value = "settleAmount", required = false)
        private String settleAmount;

        @JsonProperty(value = "senderAccNo", required = false)
        private String senderAccNo;

        @JsonProperty(value = "senderAccDesc", required = false)
        private String senderAccDesc;

        @JsonProperty(value = "senderBankCode", required = false)
        private String senderBankCode;

        @JsonProperty(value = "senderBankName", required = false)
        private String senderBankName;

        @JsonProperty(value = "drcrInd", required = false)
        private String drcrInd;

        @JsonProperty(value = "receiveBankCode", required = false)
        private String receiveBankCode;

        @JsonProperty(value = "receiveBankName", required = false)
        private String receiveBankName;

        @JsonProperty(value = "receiveAccNo", required = false)
        private String receiveAccNo;

        @JsonProperty(value = "receiveAccDesc", required = false)
        private String receiveAccDesc;

        @JsonProperty(value = "trnDesc", required = false)
        private String trnDesc;

        public String getSenderCode() {
            return senderCode;
        }

        public void setSenderCode(String senderCode) {
            this.senderCode = senderCode;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getReceiverCode() {
            return receiverCode;
        }

        public void setReceiverCode(String receiverCode) {
            this.receiverCode = receiverCode;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getMsgRefid() {
            return msgRefid;
        }

        public void setMsgRefid(String msgRefid) {
            this.msgRefid = msgRefid;
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
        }

        public String getOriginalCode() {
            return originalCode;
        }

        public void setOriginalCode(String originalCode) {
            this.originalCode = originalCode;
        }

        public String getOriginalName() {
            return originalName;
        }

        public void setOriginalName(String originalName) {
            this.originalName = originalName;
        }

        public String getTrnRefId() {
            return trnRefId;
        }

        public void setTrnRefId(String trnRefId) {
            this.trnRefId = trnRefId;
        }

        public String getSendBank() {
            return sendBank;
        }

        public void setSendBank(String sendBank) {
            this.sendBank = sendBank;
        }

        public String getReceiveBank() {
            return receiveBank;
        }

        public void setReceiveBank(String receiveBank) {
            this.receiveBank = receiveBank;
        }

        public String getCreatedDt() {
            return createdDt;
        }

        public void setCreatedDt(String createdDt) {
            this.createdDt = createdDt;
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

        public String getRequestRefNo() {
            return requestRefNo;
        }

        public void setRequestRefNo(String requestRefNo) {
            this.requestRefNo = requestRefNo;
        }

        public String getTrnDt() {
            return trnDt;
        }

        public void setTrnDt(String trnDt) {
            this.trnDt = trnDt;
        }

        public String getCcy() {
            return ccy;
        }

        public void setCcy(String ccy) {
            this.ccy = ccy;
        }

        public String getSettleAmount() {
            return settleAmount;
        }

        public void setSettleAmount(String settleAmount) {
            this.settleAmount = settleAmount;
        }

        public String getSenderAccNo() {
            return senderAccNo;
        }

        public void setSenderAccNo(String senderAccNo) {
            this.senderAccNo = senderAccNo;
        }

        public String getSenderAccDesc() {
            return senderAccDesc;
        }

        public void setSenderAccDesc(String senderAccDesc) {
            this.senderAccDesc = senderAccDesc;
        }

        public String getSenderBankCode() {
            return senderBankCode;
        }

        public void setSenderBankCode(String senderBankCode) {
            this.senderBankCode = senderBankCode;
        }

        public String getSenderBankName() {
            return senderBankName;
        }

        public void setSenderBankName(String senderBankName) {
            this.senderBankName = senderBankName;
        }

        public String getDrcrInd() {
            return drcrInd;
        }

        public void setDrcrInd(String drcrInd) {
            this.drcrInd = drcrInd;
        }

        public String getReceiveBankCode() {
            return receiveBankCode;
        }

        public void setReceiveBankCode(String receiveBankCode) {
            this.receiveBankCode = receiveBankCode;
        }

        public String getReceiveBankName() {
            return receiveBankName;
        }

        public void setReceiveBankName(String receiveBankName) {
            this.receiveBankName = receiveBankName;
        }

        public String getReceiveAccNo() {
            return receiveAccNo;
        }

        public void setReceiveAccNo(String receiveAccNo) {
            this.receiveAccNo = receiveAccNo;
        }

        public String getReceiveAccDesc() {
            return receiveAccDesc;
        }

        public void setReceiveAccDesc(String receiveAccDesc) {
            this.receiveAccDesc = receiveAccDesc;
        }

        public String getTrnDesc() {
            return trnDesc;
        }

        public void setTrnDesc(String trnDesc) {
            this.trnDesc = trnDesc;
        }
    }

}

