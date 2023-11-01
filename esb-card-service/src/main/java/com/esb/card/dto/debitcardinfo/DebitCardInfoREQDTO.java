package com.esb.card.dto.debitcardinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebitCardInfoREQDTO {
    private String cardNumber;
    private String cardID;
    private String inputType;
    private String appId;
}
