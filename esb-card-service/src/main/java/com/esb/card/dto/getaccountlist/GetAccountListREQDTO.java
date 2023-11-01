package com.esb.card.dto.getaccountlist;

import com.esb.card.dto.FCubsHeaderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountListREQDTO {
    private FCubsHeaderDTO fCubsHeader;
    private GetAccountListDTO getAccountList;
}
