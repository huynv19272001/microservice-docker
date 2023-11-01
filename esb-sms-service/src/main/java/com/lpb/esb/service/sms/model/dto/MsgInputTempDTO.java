package com.lpb.esb.service.sms.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;

/**
 * Created by tudv1 on 2022-04-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class MsgInputTempDTO {

    private String messageInputId;
    private String batchNumber;
    private String category;
    private String branchCode;
    private String toAdd;
    private String kindOfMsg;
    private String msgDescription;
    private String status;
    private String recordStat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ", timezone = "Asia/Ho_Chi_Minh")
    private Date createDt;
    private String markerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ", timezone = "Asia/Ho_Chi_Minh")
    private Date sendTime;
}
