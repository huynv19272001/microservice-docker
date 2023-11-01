package com.lpb.esb.service.sms.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by tudv1 on 2021-08-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "MESSAGE_INPUT_TEMP")
public class MsgInputTempEntity {
    @Id
    @Column(name = "MESSAGE_INPUT_ID")
    private String messageInputId;
    @Basic
    @Column(name = "BATCH")
    private String batchNumber;
    @Basic
    @Column(name = "CATEGORY")
    private String category;
    @Basic
    @Column(name = "BRANCH_CODE")
    private String branchCode;
    @Basic
    @Column(name = "TO_ADD")
    private String toAdd;
    @Basic
    @Column(name = "KIND_OF_MSG")
    private String kindOfMsg;
    @Basic
    @Column(name = "MSG_DESCRIPTION")
    private String msgDescription;
    @Basic
    @Column(name = "STATUS")
    private String status;
    @Basic
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ",timezone = "Asia/Ho_Chi_Minh")
    @Basic
    @Column(name = "CREATE_DT")
    private Date createDt;
    @Basic
    @Column(name = "MAKER_ID")
    private String markerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ",timezone = "Asia/Ho_Chi_Minh")
    @Basic
    @Column(name = "SEND_TIME")
    private Date sendTime;
}
