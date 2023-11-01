package com.lpb.esb.service.common.model.response;

import lombok.*;

/**
 * Created by tudv1 on 2021-11-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class LpbResCode {
    private String errorCode;
    private String errorDesc;
    private String refCode;
    private String refDesc;
}
