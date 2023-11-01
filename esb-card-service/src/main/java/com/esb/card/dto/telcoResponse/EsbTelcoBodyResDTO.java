package com.esb.card.dto.telcoResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by cuongnm on 2022-08-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbTelcoBodyResDTO {
    @JsonProperty("CardId")
    private String cardId;
    @JsonProperty("PhoneNo")
    private String phoneNo;
    @JsonProperty("TelCode")
    private String telCode;
}
