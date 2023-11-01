package com.lpb.service.payoo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PayBillBEReq extends AbstractBody {
    @JsonProperty(value = "Data", required = true)
    private String data;

    public static class Data extends AbstractBody {
        @JsonProperty(value = "UserId", required = true)
        private String userId;

        @JsonProperty(value = "BillIds", required = true)
        private String billIds;

        @JsonProperty(value = "ContactPhone", required = true)
        private String contactPhone;

        @JsonProperty(value = "SystemTrace", required = true)
        private String systemTrace;

        @JsonProperty(value = "MoneyTotal", required = false)
        private String moneyTotal;

        @JsonProperty(value = "TransactionTime", required = true)
        private String transactionTime;

        @JsonProperty(value = "ChannelPerform", required = true)
        private String channelPerform;

        @JsonProperty(value = "PaymentFee", required = false)
        private String paymentFee;

        @JsonCreator
        public Data(String userId, String billIds, String contactPhone, String systemTrace, String moneyTotal, String transactionTime, String channelPerform, String paymentFee) {
            this.userId = userId;
            this.billIds = billIds;
            this.contactPhone = contactPhone;
            this.systemTrace = systemTrace;
            this.moneyTotal = moneyTotal;
            this.transactionTime = transactionTime;
            this.channelPerform = channelPerform;
            this.paymentFee = paymentFee;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getBillIds() {
            return billIds;
        }

        public void setBillIds(String billIds) {
            this.billIds = billIds;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getSystemTrace() {
            return systemTrace;
        }

        public void setSystemTrace(String systemTrace) {
            this.systemTrace = systemTrace;
        }

        public String getMoneyTotal() {
            return moneyTotal;
        }

        public void setMoneyTotal(String moneyTotal) {
            this.moneyTotal = moneyTotal;
        }

        public String getTransactionTime() {
            return transactionTime;
        }

        public void setTransactionTime(String transactionTime) {
            this.transactionTime = transactionTime;
        }

        public String getPaymentFee() {
            return paymentFee;
        }

        public void setPaymentFee(String paymentFee) {
            this.paymentFee = paymentFee;
        }

        public String getChannelPerform() {
            return channelPerform;
        }

        public void setChannelPerform(String channelPerform) {
            this.channelPerform = channelPerform;
        }
    }
    @JsonCreator
    public PayBillBEReq(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
