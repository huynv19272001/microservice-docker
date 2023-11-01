package com.lpb.esb.service.config.model.entities;

import com.lpb.esb.service.config.model.entities.id.EsbUserPermissionID;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tudv1 on 2021-11-17
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table(name = "ESB_USER_PERMISSION")
@IdClass(EsbUserPermissionID.class)
public class EsbUserPermissionEntity {
    @Basic
    @Id
    @Column(name = "ROLE_ID")
    private String roleId;
    @Basic
    @Id
    @Column(name = "USERNAME")
    private String username;
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
}
