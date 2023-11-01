package com.lpb.esb.service.config.model.dto;

import lombok.*;

import java.util.Date;

/**
 * Created by cuongnm10 on 2022-06-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class OtpDTO {
    private String userId;
    private String recordStat;
    private Date makerDt;
    private String makerId;
    private String udf1;
    private String udf2;
    private String udf3;
    private String udf4;
}
