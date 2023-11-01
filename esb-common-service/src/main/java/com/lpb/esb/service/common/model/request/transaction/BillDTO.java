package com.lpb.esb.service.common.model.request.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement(localName = "BILL")
public class BillDTO {
    @JacksonXmlProperty(localName = "BILL_CODE")
    @JsonProperty("bill_code")
    private String billCode;
    @JacksonXmlProperty(localName = "BILL_DESC")
    @JsonProperty("bill_desc")
    private String billDesc;
    @JacksonXmlProperty(localName = "BILL_AMOUNT")
    @JsonProperty("bill_amount")
    private String billAmount;
    @JacksonXmlProperty(localName = "AMT_UNIT")
    @JsonProperty("amt_unit")
    private String amtUnit;
    @JacksonXmlProperty(localName = "SETTLED_AMOUNT")
    @JsonProperty("settled_amount")
    private String settledAmount;
    @JacksonXmlProperty(localName = "OTHER_INFO")
    @JsonProperty("other_info")
    private String otherInfo;
    @JacksonXmlProperty(localName = "BILL_TYPE")
    @JsonProperty("bill_type")
    private String billType;
    @JacksonXmlProperty(localName = "BILL_STATUS")
    @JsonProperty("bill_status")
    private String billStatus;
    @JacksonXmlProperty(localName = "BILL_ID")
    @JsonProperty("bill_id")
    private String billId;
    @JacksonXmlProperty(localName = "PAYMENT_METHOD")
    @JsonProperty("payment_method")
    private String paymentMethod;
}
