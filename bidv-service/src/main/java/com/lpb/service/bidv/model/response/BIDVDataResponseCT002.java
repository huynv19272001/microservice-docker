package com.lpb.service.bidv.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BIDVDataResponseCT002 {
    private String f1;
    private String f2;
    private String f3;
    private String f4;
    private String f5;
    private String f6;
    private String f7;
    private String f8;
    private String f9;
    private String f10;
    private String f11;
    private String f12;
    private String f13;
    private String f14;
    private String f15;
    private String f16;
    private String f17;
    @JsonProperty("msg_type")
    private String msgType;
    @JsonProperty("mtid")
    private String mtId;
    @JsonProperty("sender_bank_name")
    private String senderBankName;
    @JsonProperty("rec_bank_name")
    private String recBankName;
}
