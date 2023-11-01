package com.esb.card.dto.checklatedatcard;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckLateDateCardByCifREQDTO {
    @JsonProperty("cif")
    private String cif;
    @JsonProperty("msgId")
    private String msgId;
}
