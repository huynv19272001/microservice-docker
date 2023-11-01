package com.lpb.esb.service.job.model.entities;

import com.lpb.esb.service.job.model.entities.id.EsbJobScheduleID;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by tudv1 on 2021-09-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "ESB_JOB_SCHEDULES")
@IdClass(EsbJobScheduleID.class)
public class EsbJobScheduleEntity {
    @Id
    @Column(name = "JOB_ID")
    private String jobId;
    @Id
    @Column(name = "JOB_NAME")
    private String jobName;
    @Basic
    @Column(name = "JOB_DESC")
    private String jobDesc;
    @Basic
    @Column(name = "JOB_INTERVAL")
    private String jobInterval;
    @Basic
    @Column(name = "MAKERID")
    private String makerId;
    @Basic
    @Column(name = "CHECKERID")
    private String checkerId;
    @Basic
    @Column(name = "MAKER_DT")
    private Date makerDt;
    @Basic
    @Column(name = "CHECKER_DT")
    private Date checkerDt;
    @Basic
    @Column(name = "RECORD_STATS")
    private String recordStats;
    @Basic
    @Column(name = "UDF_1")
    private String udf1;
    @Basic
    @Column(name = "UDF_2")
    private String udf2;
    @Basic
    @Column(name = "UDF_3")
    private String udf3;
    @Basic
    @Column(name = "UDF_4")
    private String udf4;
    @Basic
    @Column(name = "UDF_5")
    private String udf5;
    @Basic
    @Column(name = "UDF_6")
    private String udf6;
//    @Basic
//    @Column(name = "UDF_7")
//    private String udf7;

}
