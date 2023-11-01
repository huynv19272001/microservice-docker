package com.lpb.service.vimo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerOtherInfo extends AbstractBody {
    @JsonProperty("customer_phone")
    private String customerPhone;

    @JsonProperty("tax_code")
    private String taxCode;

    public CustomerOtherInfo(String customerPhone, String taxCode) {
        this.customerPhone = customerPhone;
        this.taxCode = taxCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Override
    public String toString()
    {
        return "customer_phone=" + customerPhone + "| tax_code=" + taxCode;
    }
}
