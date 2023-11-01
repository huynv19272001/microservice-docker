package com.lpb.esb.service.config.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * Created by tudv1 on 2021-11-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbServiceProduct {
    private String productCode;
    private String description;
    private String productType;
    private String serviceId;
    private String txnCode;
    private String recordStat;
    private String makerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ", timezone = "Asia/Ho_Chi_Minh")
    private Date makerDt;
    private String checkerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ", timezone = "Asia/Ho_Chi_Minh")
    private Date checkerDt;
    private String txnCcy;
    private String udf1;
    private String udf2;
    private String udf3;
    private String udf4;
}
