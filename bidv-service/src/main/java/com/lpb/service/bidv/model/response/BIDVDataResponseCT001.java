package com.lpb.service.bidv.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BIDVDataResponseCT001 {
    @JsonProperty("msg_id")
    private BigInteger msgId;
    @JsonProperty("hash")
    private String hash;
    @JsonProperty("bank_id")
    private String bankId;
    @JsonProperty("mtid")
    private String mtId;
    @JsonProperty("ref")
    private String ref;
    @JsonProperty("value_date")
    private String valueDate;
    @JsonProperty("err_code")
    private String errCode;
    @JsonProperty("err_desc")
    private String errDesc;
}
