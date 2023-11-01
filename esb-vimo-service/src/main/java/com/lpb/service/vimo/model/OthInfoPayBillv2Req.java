package com.lpb.service.vimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OthInfoPayBillv2Req extends AbstractBody {
    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("expirationDate")
    private String expirationDate;

    @JsonProperty("payDate")
    private String payDate;

    @JsonProperty("addFee")
    private String addFee;

    @JsonProperty("minAmount")
    private String minAmount;

    @JsonProperty("channel_source")
    private String channelSource;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("relative")
    private String relative;

    public OthInfoPayBillv2Req(String creationDate, String expirationDate, String payDate, String addFee, String minAmount, String channelSource, String fullName, String phone, String relative) {
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
}
