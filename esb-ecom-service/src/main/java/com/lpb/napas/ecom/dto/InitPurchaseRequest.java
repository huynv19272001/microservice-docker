package com.lpb.napas.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitPurchaseRequest {
    private String responseCode;
    private String responseMessage;
    private String partnerTransId;
}
