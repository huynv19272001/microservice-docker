package com.esb.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardRESDTO {
    @JsonProperty("Header")
    private CardRESHeaderDTO header;
    @JsonProperty("Body")
    private CardBodyDTO body;
    @JsonProperty("Signature")
    private String Signature;
}
