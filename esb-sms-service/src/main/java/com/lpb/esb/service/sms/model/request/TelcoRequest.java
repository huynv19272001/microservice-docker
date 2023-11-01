package com.lpb.esb.service.sms.model.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class TelcoRequest {
    private String mobileNo;
    private String telcoCode;
    private Long sentTime;
    private String sender;
    private String keyWord;
    private String messageId;
    private String status;
}
