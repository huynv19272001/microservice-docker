package com.lpb.esb.service.sms.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by tudv1 on 2021-07-19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "MID_SMS_CATEGORY")
public class MidSmsCategoryEntity {
    @Id
    @Column(name = "CATEGORY")
    private String category;
    @Basic
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Basic
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Basic
    @Column(name = "RECORD_STATUS")
    private String recordStatus;
    @Basic
    @Column(name = "MAKER_ID")
    private String makerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ",timezone = "Asia/Ho_Chi_Minh")
    @Basic
    @Column(name = "MAKER_DT")
    private Date makerDt;
    @Basic
    @Column(name = "REQUEST_BY")
    private String requestBy;
    @Basic
    @Column(name = "HAS_ROLE")
    private String hasRole;

}
