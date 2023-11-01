package com.esb.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardREQDTO {
    @JsonProperty("Header")
    private CardHeaderDTO header;
    @JsonProperty("Body")
    private CardBodyDTO body;
    @JsonProperty("Signature")
    private String signature;
}
