package com.lpb.service.vimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QueryBillRes extends AbstractBody {
    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("merchant_code")
    private String merchantCode;

    @JsonProperty("checksum")
    private String checksum;

    @JsonProperty("data")
    private Data data;

    public static class Data extends AbstractBody {
        @JsonProperty("transaction_id")
        private String transactionId;

        @JsonProperty("billDetail")
        private List<Detail> billDetail;

        @JsonProperty("customerInfo")
        private CusInfo customerInfo;

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public List<Detail> getBillDetail() {
            return billDetail;
        }

        public void setBillDetail(List<Detail> billDetail) {
            this.billDetail = billDetail;
        }

        public CusInfo getCustomerInfo() {
            return customerInfo;
        }

        public void setCustomerInfo(CusInfo customerInfo) {
            this.customerInfo = customerInfo;
        }
    }

    public static class Detail extends AbstractBody {
        @JsonProperty("billNumber")
        private String billNumber;

        @JsonProperty("period")
        private String period;

        @JsonProperty("amount")
        private int amount;

        @JsonProperty("billType")
        private String billType;

        @JsonProperty("otherInfo")
        private String otherInfo;

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

    public static class CusInfo extends AbstractBody {
        @JsonProperty("customerCode")
        private String customerCode;

        @JsonProperty("customerName")
        private String customerName;

        @JsonProperty("customerAddress")
        private String customerAddress;

        @JsonProperty("customerOtherInfo")
        private String customerOtherInfo;

        public String getCustomerCode() {
            return customerCode;
        }

        public void setCustomerCode(String customerCode) {
            this.customerCode = customerCode;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        public String getCustomerOtherInfo() {
            return customerOtherInfo;
        }

        public void setCustomerOtherInfo(String customerOtherInfo) {
            this.customerOtherInfo = customerOtherInfo;
        }
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
