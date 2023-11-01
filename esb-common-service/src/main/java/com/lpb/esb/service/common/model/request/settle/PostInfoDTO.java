package com.lpb.esb.service.common.model.request.settle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor

public class PostInfoDTO {
    @JsonProperty("sourceAcc")
    private String sourceAcc;
    @JsonProperty("acNo")
    private String acNo;
    @JsonProperty("branchCode")
    private String branchCode;
    @JsonProperty("ccy")
    private String ccy;
    @JsonProperty("fcyAmount")
    private String fcyAmount;
    @JsonProperty("lcyAmount")
    private String lcyAmount;
    @JsonProperty("drcrInd")
    private String drcrInd;
    @JsonProperty("amountTag")
    private String amountTag;
    @JsonProperty("bankCode")
    private String bankCode;
    @JsonProperty("bankName")
    private String bankName;
    @JsonProperty("makerid")
    private String makerid;
    @JsonProperty("checkerid")
    private String checkerid;
    @JsonProperty("infoSourceAcc")
    private String infoSourceAcc;
    @JsonProperty("infoAcNo")
    private String infoAcNo;
    @JsonProperty("postDesc")
    private String postDesc;
    @JsonProperty("postRefNo")
    private String postRefNo;
}
