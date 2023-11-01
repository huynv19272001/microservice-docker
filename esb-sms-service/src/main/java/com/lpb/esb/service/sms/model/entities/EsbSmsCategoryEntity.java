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
@Table(name = "ESB_SMS_CATEGORY")
public class EsbSmsCategoryEntity {
    @Id
    @Column(name = "CATEGORY")
    private String category;
    @Basic
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Basic
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Basic
    @Column(name = "RECORD_STATUS")
    private String recordStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ",timezone = "Asia/Ho_Chi_Minh")
    @Basic
    @Column(name = "CREATE_DT")
    private Date createDt;
    @Basic
    @Column(name = "REQUEST_BY")
    private String requestBy;
    @Basic
    @Column(name = "SERVICE_NAME")
    private String serviceName;
    @Basic
    @Column(name = "PRODUCT_CODE")
    private String productCode;
}
