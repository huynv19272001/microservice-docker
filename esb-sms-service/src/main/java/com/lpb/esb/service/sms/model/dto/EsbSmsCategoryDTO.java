package com.lpb.esb.service.sms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;

/**
 * Created by tudv1 on 2022-04-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbSmsCategoryDTO {
    private String category;
    private String categoryName;
    private String serviceId;
    private String recordStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ",timezone = "Asia/Ho_Chi_Minh")
    private Date createDt;
    private String requestBy;
    private String serviceName;
    private String productCode;
}
