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
@JacksonXmlRootElement(localName = "PARTNER")
public class PartnerDTO {
    @JacksonXmlProperty(localName = "TXN_CODE")
    @JsonProperty("txn_code")
    private String txnCode;
    @JacksonXmlProperty(localName = "TXN_DATETIME")
    @JsonProperty("txn_datetime")
    private String txnDatetime;
    @JacksonXmlProperty(localName = "CHANEL")
    @JsonProperty("chanel")
    private String chanel;
    @JacksonXmlProperty(localName = "TXN_REF_NO")
    @JsonProperty("txn_ref_no")
    private String txnRefNo;
    @JacksonXmlProperty(localName = "TXN_CONFIRM_DT")
    @JsonProperty("txn_confirm_dt")
    private String txnConfirmDt;
    @JacksonXmlProperty(localName = "TERMINAL_ID")
    @JsonProperty("terminal_id")
    private String terminalId;
}
