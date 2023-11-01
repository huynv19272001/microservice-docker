package com.lpb.esb.service.config.model.entities;

import com.lpb.esb.service.config.model.entities.id.EsbPermissionID;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by tudv1 on 2021-11-16
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table(name = "ESB_PERMISSION")
@IdClass(EsbPermissionID.class)
public class EsbPermissionEntity implements Serializable {

    @Column(name = "ROLE_ID")
    private String roleId;
    @Id
    @Column(name = "HAS_ROLE")
    private String hasRole;
    @Id
    @Column(name = "SERVICE_ID")
    private String serviceId;
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

//    @OneToMany(targetEntity = EsbServiceEntity.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERVICE_ID")
//    private List<EsbServiceEntity> esbServiceEntity;
//
//    @OneToMany(targetEntity = EsbServiceProcessEntity.class, cascade = CascadeType.ALL)
//    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
//    private List<EsbServiceProcessEntity> esbServiceProcesses;
}
