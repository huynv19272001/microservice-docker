package com.esb.card.dto.cardtranlimit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCardTranLimitREQDTO {
    @JsonProperty("cardId")
    private String cardId;
    @JsonProperty("cif")
    private String cif;
    @JsonProperty("msgId")
    private String msgId;
}
