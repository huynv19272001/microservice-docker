package com.lpb.esb.service.config.model.entities;

import com.lpb.esb.service.config.model.entities.id.OtpId;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by cuongnm10 on 2022-06-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "USER_BYPASS_OTP")
@IdClass(OtpId.class)
public class UserBypassOtpEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;
    @Basic
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "MAKER_DT")
    @Basic
    private Date makerDt;
    @Column(name = "MAKER_ID")
    @Basic
    private String makerId;
    @Column(name = "UDF1")
    @Basic
    private String udf1;
    @Column(name = "UDF2")
    @Basic
    private String udf2;
    @Column(name = "UDF3")
    @Basic
    private String udf3;
    @Column(name = "UDF4")
    @Basic
    private String udf4;
}
