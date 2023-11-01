package com.lpb.service.vimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PayBillv2Req extends AbstractBody{
    @JsonProperty(value = "mc_request_id", required = true)
    private String mcRequestId;

    @JsonProperty(value = "service_code", required = true)
    private String serviceCode;

    @JsonProperty(value = "publisher", required = true)
    private String publisher;

    @JsonProperty(value = "customer_code", required = true)
    private String customerCode;

    @JsonProperty(value = "bill_payment", required = true)
    private List<Payment> billPayment;

    public PayBillv2Req(String mcRequestId, String serviceCode, String publisher, String customerCode, List<Payment> billPayment){
        this.mcRequestId = mcRequestId;
        this.serviceCode = serviceCode;
        this.publisher = publisher;
        this.customerCode = customerCode;
        this.billPayment = billPayment;
    }

    public static class Payment extends AbstractBody {
        @JsonProperty(value = "billNumber", required = true)
        private String billNumber;

        @JsonProperty(value = "period", required = false)
        private String period;

        @JsonProperty(value = "amount", required = true)
        private int amount;

        @JsonProperty(value = "billType", required = false)
        private String billType;

        @JsonProperty(value = "otherInfo", required = true)
        private String otherInfo;

        public Payment(String billNumber, String period, int amount, String billType, String otherInfo){
            this.billNumber = billNumber;
            this.period = period;
            this.amount = amount;
            this.billType = billType;
            this.otherInfo = otherInfo;

        }

        public String getBillNumber() {
            return billNumber;
        }

        public void setBillNumber(String billNumber) {
            this.billNumber = billNumber;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getBillType() {
            return billType;
        }

        public void setBillType(String billType) {
            this.billType = billType;
        }

        public String getOtherInfo() {
            return otherInfo;
        }

        public void setOtherInfo(String otherInfo) {
            this.otherInfo = otherInfo;
        }
    }

    public static class OthInfo extends AbstractBody {

        @JsonProperty(value = "creationDate", required = true)
        private String creationDate;

        @JsonProperty(value = "expirationDate", required = true)
        private String expirationDate;

        @JsonProperty(value = "payDate", required = true)
        private String payDate;

        @JsonProperty(value = "addFee", required = true)
        private String addFee;

        @JsonProperty(value = "minAmount", required = true)
        private String minAmount;

        @JsonProperty(value = "channel_source", required = true)
        private String channelSource;

        @JsonProperty(value = "full_name", required = true)
        private String fullName;

        @JsonProperty(value = "phone", required = true)
        private String phone;

        @JsonProperty(value = "relative", required = true)
        private String relative;

        public OthInfo(String creationDate, String expirationDate, String payDate, String addFee, String minAmount, String channelSource, String fullName, String phone, String relative){
            this.creationDate = creationDate;
            this.expirationDate = expirationDate;
            this.payDate = payDate;
            this.addFee = addFee;
            this.minAmount = minAmount;
            this.channelSource = channelSource;
            this.fullName = fullName;
            this.phone = phone;
            this.relative = relative;

        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

        public String getAddFee() {
            return addFee;
        }

        public void setAddFee(String addFee) {
            this.addFee = addFee;
        }

        public String getMinAmount() {
            return minAmount;
        }

        public void setMinAmount(String minAmount) {
            this.minAmount = minAmount;
        }

        public String getChannelSource() {
            return channelSource;
        }

        public void setChannelSource(String channelSource) {
            this.channelSource = channelSource;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRelative() {
            return relative;
        }

        public void setRelative(String relative) {
            this.relative = relative;
        }
    }

    public String getMcRequestId() {
        return mcRequestId;
    }

    public void setMcRequestId(String mcRequestId) {
        this.mcRequestId = mcRequestId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public List<Payment> getBillPayment() {
        return billPayment;
    }

    public void setBillPayment(List<Payment> billPayment) {
        this.billPayment = billPayment;
    }
}
