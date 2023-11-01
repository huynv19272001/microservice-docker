package com.esb.card.dto.canceldpp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelDppREQDTO {
    @JsonProperty("cardId")
    private String cardId;
    @JsonProperty("cif")
    private String cif;
    @JsonProperty("operId")
    private String operId;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("clientRequestTime")
    private String clientRequestTime;
    @JsonProperty("user")
    private String user;
    @JsonProperty("channel")
    private String channel;
}
