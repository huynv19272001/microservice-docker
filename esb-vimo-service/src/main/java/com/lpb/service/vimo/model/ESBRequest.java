package com.lpb.service.vimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ESBRequest extends AbstractBody{
    @JsonProperty(value = "header", required = false)
    private Header header;

    @JsonProperty(value = "body", required = false)
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

        @JsonProperty(value = "listCustomer", required = false)
        private List<ListCus> listCustomer;

        public Body(Service service, List<ListCus> listCustomer){
            this.service = service;
            this.listCustomer = listCustomer;
        }

        public Service getService() {
            return service;
        }

        public void setService(Service service) {
            this.service = service;
        }

        public List<ListCus> getListCustomer() {
            return listCustomer;
        }

        public void setListCustomer(List<ListCus> listCustomer) {
            this.listCustomer = listCustomer;
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

    public static class ListCus extends AbstractBody {
        @JsonProperty(value = "customerInfo", required = false)
        private CusInfo customerInfo;

        @JsonProperty(value = "settleAccountInfo", required = false)
        private AccInfo settleAccountInfo;

        @JsonProperty(value = "listBillInfo", required = false)
        private List<BillInfo> listbillInfo;

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

        public List<BillInfo> getListbillInfo() {
            return listbillInfo;
        }

        public void setListbillInfo(List<BillInfo> listbillInfo) {
            this.listbillInfo = listbillInfo;
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
