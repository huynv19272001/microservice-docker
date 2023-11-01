package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyOtpREQDTO {
    private String appMsgId;
    private String userId;
    private String otpCode;
}
