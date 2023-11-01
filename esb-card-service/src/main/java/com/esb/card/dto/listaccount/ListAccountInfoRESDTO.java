package com.esb.card.dto.listaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListAccountInfoRESDTO {
    @JsonProperty("CardId")
    private String CardId;
    @JsonProperty("EmbossedName")
    private String EmbossedName;
    @JsonProperty("CardType")
    private String CardType;
    @JsonProperty("AccountNumber")
    private List<AccountNumberDTO> AccountNumber;
}
