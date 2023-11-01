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
@JacksonXmlRootElement(localName = "ACCOUNT")
public class PostInfoDTO {
    @JacksonXmlProperty(localName = "SOURCE_ACC")
    @JsonProperty("source_acc")
    private String sourceAcc;
    @JacksonXmlProperty(localName = "AC_NO")
    @JsonProperty("ac_no")
    private String acNo;
    @JacksonXmlProperty(localName = "BRANCH_CODE")
    @JsonProperty("branch_code")
    private String branchCode;
    @JacksonXmlProperty(localName = "CCY")
    @JsonProperty("ccy")
    private String ccy;
    @JacksonXmlProperty(localName = "FCY_AMOUNT")
    @JsonProperty("fcy_amount")
    private String fcyAmount;
    @JacksonXmlProperty(localName = "LCY_AMOUNT")
    @JsonProperty("lcy_amount")
    private String lcyAmount;
    @JacksonXmlProperty(localName = "DRCR_IND")
    @JsonProperty("drcr_ind")
    private String drcrInd;
    @JacksonXmlProperty(localName = "AMOUNT_TAG")
    @JsonProperty("amount_tag")
    private String amountTag;
    @JacksonXmlProperty(localName = "BANK_CODE")
    @JsonProperty("bank_code")
    private String bankCode;
    @JacksonXmlProperty(localName = "BANK_NAME")
    @JsonProperty("bank_name")
    private String bankName;
    @JacksonXmlProperty(localName = "MAKER_ID")
    @JsonProperty("maker_id")
    private String makerId;
    @JacksonXmlProperty(localName = "CHECKER_ID")
    @JsonProperty("checker_id")
    private String checkerId;
    @JacksonXmlProperty(localName = "INFO_SOURCE_ACC")
    @JsonProperty("info_source_acc")
    private String infoSourceAcc;
    @JacksonXmlProperty(localName = "INFO_AC_NO")
    @JsonProperty("info_ac_no")
    private String infoAcNo;
    @JacksonXmlProperty(localName = "POST_DESC")
    @JsonProperty("post_desc")
    private String postDesc;
    @JacksonXmlProperty(localName = "POST_REF_NO")
    @JsonProperty("post_ref_no")
    private String postRefNo;

    //dành riêng cho init transaction, ko dùng cho settle bill
    @JsonProperty("card_no")
    private String cardNo;
    @JsonProperty("clr_service")
    private String clrService;
    @JsonProperty("clr_product")
    private String clrProduct;
    @JsonProperty("curr_no")
    private String currNo;
    @JsonProperty("txn_ccy")
    private String txnCcy;
    @JsonProperty("exc_rate")
    private String excRate;
    @JsonProperty("detail_of_charge")
    private String detailOfCharge;
    @JsonProperty("customer_no")
    private String customerNo;
    @JsonProperty("ac_desc")
    private String acDesc;
    @JsonProperty("module")
    private String module;
}

