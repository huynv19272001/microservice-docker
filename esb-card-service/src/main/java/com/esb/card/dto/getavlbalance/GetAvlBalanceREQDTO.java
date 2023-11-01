package com.esb.card.dto.getavlbalance;

import com.esb.card.dto.FCubsHeaderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAvlBalanceREQDTO {
    private FCubsHeaderDTO fCubsHeader;
    private GetAvlBalanceDTO GetAvlBalance;
}
