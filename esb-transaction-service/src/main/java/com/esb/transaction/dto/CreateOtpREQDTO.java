package com.esb.transaction.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOtpREQDTO {
    private String userId;
    private String appMsgId;
    private String mobileNo;
}
