package com.esb.card.dto.availinfocard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailInfoCardREQDTO {
    @JsonProperty("cardNumber")
    private String cardNumber;
    @JsonProperty("cardId")
    private String cardId;
    @JsonProperty("inputType")
    private String inputType;
    @JsonProperty("appId")
    private String appId;
}
