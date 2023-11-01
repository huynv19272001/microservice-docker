package com.lpb.napas.ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardRequest {
    private String number;
    @JsonProperty("nameOnCard")
    private String nameOnCard;
    private String month;
    private String year;
    private String type;
}
