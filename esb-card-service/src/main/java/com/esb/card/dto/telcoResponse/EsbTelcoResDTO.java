package com.esb.card.dto.telcoResponse;

import com.esb.card.dto.telcoRequest.EsbTelCoBodyDTO;
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
public class EsbTelcoResDTO {
    @JsonProperty("Header")
    private EsbTelcoResHeaderDTO header;
    @JsonProperty("Body")
    private EsbTelCoBodyDTO body;
    @JsonProperty("Signature")
    private String signature;
}
