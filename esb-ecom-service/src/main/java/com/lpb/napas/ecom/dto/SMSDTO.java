package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SMSDTO {
    private FCubsSMSBodyDTO fCubsSMSBodyDTO;
    private FCubsHeaderDTO fCubsHeaderDTO;
    private String appId;
    private String requestorTransId;
}
