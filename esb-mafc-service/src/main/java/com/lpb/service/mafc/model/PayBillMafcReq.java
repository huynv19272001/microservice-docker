package com.lpb.service.mafc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PayBillMafcReq extends AbstractBody {
    @JsonProperty(value = "transactionid", required = true)
    private String transactionId;

    @JsonProperty(value = "agreeid", required = true)
    private String agreeId;

    @JsonProperty(value = "amount", required = true)
    private int amount;

    @JsonProperty(value = "channel", required = true)
    private String channel;

    @JsonProperty(value = "collector", required = true)
    private String collector;

    @JsonCreator
    public PayBillMafcReq(String transactionId, String agreeId, int amount, String channel, String collector) {
        this.transactionId = transactionId;
        this.agreeId = agreeId;
        this.amount = amount;
        this.channel = channel;
        this.collector = collector;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAgreeId() {
        return agreeId;
    }

    public void setAgreeId(String agreeId) {
        this.agreeId = agreeId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }
}
