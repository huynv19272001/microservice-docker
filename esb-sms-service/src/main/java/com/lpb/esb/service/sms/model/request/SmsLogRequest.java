package com.lpb.esb.service.sms.model.request;

import lombok.*;

/**
 * Created by tudv1 on 2021-08-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class SmsLogRequest {
    private String phoneSend;
    private String phoneReceive;
    private String message;
}
