package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerDTO {
    private String txnCode;
    private String txnDatetime;
    private String chanel;
    private String txnRefNo;
}
