package com.lpb.esb.service.sms.model.request;

import lombok.*;

/**
 * Created by tudv1 on 2022-04-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbBody {
    private String mobileNumber;
    private String content;
    private int serviceLog;
    private String category;
}
