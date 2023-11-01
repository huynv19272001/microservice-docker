package com.lpb.esb.service.sms.model.request;

import lombok.*;

/**
 * Created by tudv1 on 2021-08-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class TelegramRequest {
    private String chatId;
    private String message;
}
