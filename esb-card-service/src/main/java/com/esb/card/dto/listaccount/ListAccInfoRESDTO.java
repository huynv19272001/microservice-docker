package com.esb.card.dto.listaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListAccInfoRESDTO {
    @JsonProperty("CardId")
    private String CardId;
    @JsonProperty("EmbossedName")
    private String EmbossedName;
    @JsonProperty("CardType")
    private String CardType;
    @JsonProperty("ListAccount")
    private List<ListAccountDTO> ListAccount;
}
