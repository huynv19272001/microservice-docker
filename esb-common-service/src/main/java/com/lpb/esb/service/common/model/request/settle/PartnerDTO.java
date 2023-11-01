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

public class PartnerDTO {
    @JsonProperty("txnRefNo")
    private String txnRefNo;
    @JsonProperty("txnDatetime")
    private String txnDatetime;
    @JsonProperty("txnCode")
    private String txnCode;
    @JsonProperty("chanel")
    private String chanel;
    @JsonProperty("terminalId")
    private String terminalId;
    @JsonProperty("txnConfirmDt")
    private String txnConfirmDt;
}
