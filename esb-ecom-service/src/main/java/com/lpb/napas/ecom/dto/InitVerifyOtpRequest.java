package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitVerifyOtpRequest {
    private String responseCode;
    private String responseMessage;
    private String partnerTransId;
}
