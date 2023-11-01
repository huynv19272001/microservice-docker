package com.lpb.napas.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardInfoREQDTO {
    private String clientCode;
    private String cardID;
    private String cardNumber;
    private String cmnd;
    private String passport;
    private String inputType;
    private String appId;
}
