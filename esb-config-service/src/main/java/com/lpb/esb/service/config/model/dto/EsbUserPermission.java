package com.lpb.esb.service.config.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;


/**
 * Created by tudv1 on 2021-11-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbUserPermission {
    private String roleId;
    private String username;
    private String makerId;
    private String checkerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date makerDt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date checkerDt;
    private String recordStats;
    private String udf1;
    private String udf2;
    private String udf3;
    private String udf4;
}
