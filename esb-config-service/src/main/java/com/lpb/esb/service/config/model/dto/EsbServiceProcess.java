package com.lpb.esb.service.config.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;


/**
 * Created by tudv1 on 2021-11-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbServiceProcess {
    private String productCode;
    private String roleId;
    private String serviceId;
    private String executedBy;
    private String connectorName;
    private String urlApi;
    private String connectorUrl;
    private Integer connectorPort;
    private String methodAction;
    private String recordStat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ", timezone = "Asia/Ho_Chi_Minh")
    private Date effDate;
    private String udf1;
    private String udf2;
    private String udf3;
    private String udf4;
    private String udf5;
    private String udf6;
    private String udf7;
    private String udf8;
}
