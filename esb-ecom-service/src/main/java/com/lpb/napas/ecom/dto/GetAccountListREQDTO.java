package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetAccountListREQDTO {
    private FCubsHeaderDTO fCubsHeader;
    private GetAccountListDTO getAccountList;
}
