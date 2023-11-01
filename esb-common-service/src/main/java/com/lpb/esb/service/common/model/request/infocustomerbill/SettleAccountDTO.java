package com.lpb.esb.service.common.model.request.infocustomerbill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettleAccountDTO {

    @JsonProperty("settleAcNo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String settleAcNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("settleAcBrn")
    private String settleAcBrn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("settleAcDesc")
    private String settleAcDesc;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("settleCustomerNo")
    private String settleCustomerNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("settleMerchant")
    private String settleMerchant;
}
