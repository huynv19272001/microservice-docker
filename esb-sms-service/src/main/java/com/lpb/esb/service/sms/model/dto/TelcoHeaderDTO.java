package com.lpb.esb.service.sms.model.dto;

import lombok.*;

/**
 * Created by cuongnm on 2022-07-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class TelcoHeaderDTO {
    private String msgId;
    private String source;
    private String branch;
    private String serviceId;
    private String operation;
    private String productCode;
}
