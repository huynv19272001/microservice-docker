package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetAvlBalanceREQDTO {
    private FCubsHeaderDTO fCubsHeader;
    private GetAvlBalanceDTO GetAvlBalance;
}
