package com.lpb.esb.service.query.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class EsbPartnerDTO {
    @JsonProperty("TxnRefNo")
    private String txnRefNo;
    @JsonProperty("TxtDateTime")
    private String txtDateTime;
    @JsonProperty("TxnCode")
    private String txnCode;
    @JsonProperty("Channel")
    private String channel;
}
