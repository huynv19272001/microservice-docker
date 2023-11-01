package com.lpb.napas.ecom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataVerifyPaymentRequest {
    private MerchantRequest merchant;
    private TransactionRequest transaction;
    private CardRequest card;
    private AccountRequest account;
    @JsonProperty("otherInfo")
    private List<OtherInfoRequest> otherInfo;
}
