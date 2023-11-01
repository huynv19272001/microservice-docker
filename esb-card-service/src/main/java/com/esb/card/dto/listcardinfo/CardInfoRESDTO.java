package com.esb.card.dto.listcardinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardInfoRESDTO {
    @JsonProperty("Cards")
    private ListCardInfoDTO cards;
}
