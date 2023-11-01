package com.esb.transaction.dto;

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
