package com.lpb.service.vimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetAddFeeRes extends AbstractBody{
    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("merchant_code")
    private String merchantCode;

    @JsonProperty("checksum")
    private String checkSum;

    @JsonProperty("data")
    private Data data;

    public static class Data extends AbstractBody {
        @JsonProperty("addfee")
        private int addfee;

        public int getAddfee() {
            return addfee;
        }

        public void setAddfee(int addfee) {
            this.addfee = addfee;
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

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
