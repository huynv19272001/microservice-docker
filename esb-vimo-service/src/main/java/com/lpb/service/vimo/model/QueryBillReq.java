package com.lpb.service.vimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryBillReq extends AbstractBody{
    @JsonProperty(value = "mc_request_id", required = true)
    private String mcRequestId;

    @JsonProperty(value = "service_code", required = true)
    private String serviceCode;

    @JsonProperty(value = "publisher", required = true)
    private String publisher;

    @JsonProperty(value = "customer_code", required = true)
    private String customerCode;


    public QueryBillReq(String mcRequestId, String serviceCode, String publisher, String customerCode) {
        this.mcRequestId = mcRequestId;
        this.serviceCode = serviceCode;
        this.publisher = publisher;
        this.customerCode = customerCode;
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
}
