package com.lpb.esb.service.common.model.request.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountInfoDTO {
    @JsonProperty("ac_no")
    @JacksonXmlProperty(localName = "AC_NO")
    private String acNo;
    @JsonProperty("amount_tag")
    @JacksonXmlProperty(localName = "AMOUNT_TAG")
    private String amountTag;
    @JsonProperty("branch_code")
    @JacksonXmlProperty(localName = "BRANCH_CODE")
    private String branchCode;
    @JsonProperty("ccy")
    @JacksonXmlProperty(localName = "CCY")
    private String ccy;
    @JsonProperty("drcr_ind")
    @JacksonXmlProperty(localName = "DRCR_IND")
    private String drcrInd;
    @JsonProperty("fcy_amount")
    @JacksonXmlProperty(localName = "FCY_AMOUNT")
    private String fcyAmount;
    @JsonProperty("lcy_amount")
    @JacksonXmlProperty(localName = "LCY_AMOUNT")
    private String lcyAmount;
    @JsonProperty("source_acc")
    @JacksonXmlProperty(localName = "SOURCE_ACC")
    private String sourceAcc;
    @JsonProperty("bank_code")
    @JacksonXmlProperty(localName = "BANK_CODE")
    private String bankCode;
    @JsonProperty("bank_name")
    @JacksonXmlProperty(localName = "BANK_NAME")
    private String bankName;
    @JsonProperty("maker_id")
    @JacksonXmlProperty(localName = "MAKERID")
    private String makerId;
    @JsonProperty("checker_id")
    @JacksonXmlProperty(localName = "CHECKERID")
    private String checkerId;
    @JsonProperty("info_source_acc")
    @JacksonXmlProperty(localName = "INFO_SOURCE_ACC")
    private String infoSourceAcc;
    @JsonProperty("info_ac_no")
    @JacksonXmlProperty(localName = "INFO_AC_NO")
    private String infoAcNo;
    @JsonProperty("post_desc")
    @JacksonXmlProperty(localName = "POST_DESC")
    private String postDesc;
    @JsonProperty("post_ref_no")
    @JacksonXmlProperty(localName = "POST_REF_NO")
    private String postRefNo;
}
