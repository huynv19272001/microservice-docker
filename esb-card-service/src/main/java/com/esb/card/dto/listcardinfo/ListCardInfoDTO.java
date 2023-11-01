package com.esb.card.dto.listcardinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListCardInfoDTO {
    @JsonProperty("CardInfo")
    private ArrayList<CardInfoDTO> cardInfo;
}
