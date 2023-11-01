package com.lpb.service.payoo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryBillExRes extends AbstractBody {
    @JsonProperty(value = "ReturnCode")
    private int returnCode;

    @JsonProperty(value = "Bills")
    private BillInfoBERes[] bills;

    @JsonProperty(value = "Customers")
    private CustomerInfoRes[] customers;

    @JsonProperty(value = "DescriptionCode")
    private String descriptionCode;

    @JsonProperty(value = "PaymentFeeType")
    private int paymentFeeType;

    @JsonProperty(value = "PercentFee")
    private String percentFee;

    @JsonProperty(value = "ConstantFee")
    private String constantFee;

    @JsonProperty(value = "MinFee")
    private String minFee;

    @JsonProperty(value = "MaxFee")
    private String maxFee;

    @JsonProperty(value = "IsShowMoney")
    private Boolean showMoney;

    @JsonProperty(value = "SubReturnCode")
    private int subReturnCode;

    @JsonProperty(value = "ExtraInfo")
    private String extraInfo;

    public int getReturnCode() {
        return returnCode;
    }

    public BillInfoBERes[] getBills() {
        return bills;
    }

    public CustomerInfoRes[] getCustomers() {
        return customers;
    }

    public String getDescriptionCode() {
        return descriptionCode;
    }

    public int getPaymentFeeType() {
        return paymentFeeType;
    }

    public String getPercentFee() {
        return percentFee;
    }

    public String getConstantFee() {
        return constantFee;
    }

    public String getMinFee() {
        return minFee;
    }

    public String getMaxFee() {
        return maxFee;
    }

    public Boolean getShowMoney() {
        return showMoney;
    }

    public int getSubReturnCode() {
        return subReturnCode;
    }

    public String getExtraInfo() {
        return extraInfo;
    }
}
