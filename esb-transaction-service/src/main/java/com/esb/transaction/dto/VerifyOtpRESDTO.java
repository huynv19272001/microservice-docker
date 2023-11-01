package com.esb.transaction.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyOtpRESDTO {
    private String otp;
}
