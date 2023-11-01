package com.lpb.service.payoo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillInfoBERes extends AbstractBody{
    @JsonProperty(value = "BillId")
    private String billId;

    @JsonProperty(value = "ServiceId")
    private String serviceId;

    @JsonProperty(value = "ServiceName")
    private String serviceName;

    @JsonProperty(value = "ProviderId")
    private String providerId;

    @JsonProperty(value = "ProviderName")
    private String providerName;

    @JsonProperty(value = "Month")
    private String month;

    @JsonProperty(value = "MoneyAmount")
    private String moneyAmount;

    @JsonProperty(value = "PaymentFee")
    private String paymentFee;

    @JsonProperty(value = "CustomerId")
    private String customerId;

    @JsonProperty(value = "CustomerName")
    private String customerName;

    @JsonProperty(value = "Address")
    private String address;

    @JsonProperty(value = "ExpiredDate")
    private String expiredDate;

    @JsonProperty(value = "MinRange")
    private String minRange;

    @JsonProperty(value = "MaxRange")
    private String maxRange;

    @JsonProperty(value = "IsPrepaid")
    private boolean isPrepaid;

    @JsonProperty(value = "InfoEx")
    private String infoEx;

    @JsonProperty(value = "PaymentRule")
    private int paymentRule;

    @JsonProperty(value = "ShippingDateNum")
    private int shippingDateNum;

    public String getBillId() {
        return billId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getMonth() {
        return month;
    }

    public String getMoneyAmount() {
        return moneyAmount;
    }

    public String getPaymentFee() {
        return paymentFee;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public String getMinRange() {
        return minRange;
    }

    public String getMaxRange() {
        return maxRange;
    }

    public boolean isPrepaid() {
        return isPrepaid;
    }

    public String getInfoEx() {
        return infoEx;
    }

    public int getPaymentRule() {
        return paymentRule;
    }

    public int getShippingDateNum() {
        return shippingDateNum;
    }
}
