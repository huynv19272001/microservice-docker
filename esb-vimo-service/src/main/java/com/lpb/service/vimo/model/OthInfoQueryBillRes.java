package com.lpb.service.vimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OthInfoQueryBillRes extends AbstractBody {
    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("expirationDate")
    private String expirationDate;

    @JsonProperty("payDate")
    private String payDate;

    @JsonProperty("addFee")
    private String addFee;

    @JsonProperty("partial")
    private String partial;

    @JsonProperty("minAmount")
    private String minAmount;

    @JsonProperty("maxAmount")
    private String maxAmount;

    @JsonProperty("channel_source")
    private String channelSource;

    @JsonProperty("evn_code")
    private String evnCode;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("relative")
    private String relative;

    public OthInfoQueryBillRes(String creationDate, String expirationDate, String payDate, String addFee, String minAmount, String channelSource, String fullName, String phone, String relative) {
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

    public OthInfoQueryBillRes(String creationDate, String expirationDate, String payDate, String addFee, String partial, String minAmount, String channelSource, String fullName, String phone, String relative) {
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.payDate = payDate;
        this.addFee = addFee;
        this.partial = partial;
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

    public String getPartial() {
        return partial;
    }

    public void setPartial(String partial) {
        this.partial = partial;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getEvnCode() {
        return evnCode;
    }

    public void setEvnCode(String evnCode) {
        this.evnCode = evnCode;
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

    public String getChannelSource() {
        return channelSource;
    }

    public void setChannelSource(String channelSource) {
        this.channelSource = channelSource;
    }

    // Creating toString
    @Override
    public String toString()
    {
        return "creationDate=" + creationDate + "| expirationDate=" + expirationDate + "| payDate=" + payDate + "| addFee=" + addFee + "| partial=" + partial
            + "| minAmount=" + minAmount + "| maxAmount=" + maxAmount + "| evnCode=" + evnCode + "| fullName=" + fullName + "| phone=" + phone + "| relative=" + relative;
    }
}
