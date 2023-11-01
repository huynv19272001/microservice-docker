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
public class PayBillBERes extends AbstractBody{
    @JsonProperty(value = "ReturnCode")
    private int returnCode;

    @JsonProperty(value = "OrderNo")
    private String orderNo;

    @JsonProperty(value = "DescriptionCode")
    private String descriptionCode;

    @JsonProperty(value = "SubReturnCode")
    private int subReturnCode;

    @JsonProperty(value = "ExtraInfo")
    private String extraInfo;

    @JsonProperty(value = "AddInfo")
    private String addInfo;

    @JsonProperty(value = "Message")
    private String message;

    @JsonProperty(value = "Barcode")
    private String barcode;

    @JsonProperty(value = "Systemtrace")
    private String systemtrace;

    public int getReturnCode() {
        return returnCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getDescriptionCode() {
        return descriptionCode;
    }

    public int getSubReturnCode() {
        return subReturnCode;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public String getMessage() {
        return message;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getSystemtrace() {
        return systemtrace;
    }
}
