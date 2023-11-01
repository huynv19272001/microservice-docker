package com.lpb.esb.service.sms.model.dto;

import lombok.*;
import oracle.sql.TIMESTAMP;


/**
 * Created by cuongnm on 2022-07-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class TelcoUpdateDTO {
    String name;
    String value;
}
