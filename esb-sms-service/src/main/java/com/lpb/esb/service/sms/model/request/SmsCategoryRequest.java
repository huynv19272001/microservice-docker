package com.lpb.esb.service.sms.model.request;

import lombok.*;

/**
 * Created by tudv1 on 2022-05-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class SmsCategoryRequest {
    private String category;
    private String msgId;
    private String mobileNumber;
    private String content;
    private int serviceLog;
}
