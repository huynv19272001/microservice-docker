package com.lpb.esb.service.config.model.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by tudv1 on 2021-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "ESB_USER")
public class EsbUserEntity {
    @Id
    @Column(name = "USERNAME")
    private String username;
    @Basic
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "RECORD_STATS")
    @Basic
    private String recordStats;
    @Column(name = "MAKERID")
    @Basic
    private String makerId;
    @Column(name = "CHECKERID")
    @Basic
    private String checkerId;
    @Column(name = "SCAN_IP")
    @Basic
    private String scanIp;
    @Column(name = "MAKER_DT")
    @Basic
    private Date markerDt;
    @Column(name = "CHECKER_DT")
    @Basic
    private Date checkerDt;
    @Column(name = "UDF_1")
    @Basic
    private String udf1;
    @Column(name = "UDF_2")
    @Basic
    private String udf2;
    @Column(name = "UDF_3")
    @Basic
    private String udf3;
    @Column(name = "UDF_4")
    @Basic
    private String udf4;
    @Column(name = "KEY_RSA")
    @Basic
    private String keyRSA;
}
