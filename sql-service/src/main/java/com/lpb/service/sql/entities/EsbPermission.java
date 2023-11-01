package com.lpb.service.sql.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ESB_PERMISSION")
public class EsbPermission {
    @Id
    @Column(name = "ROLE_ID")
    private String roleId;
    @Column(name = "HAS_ROLE")
    private String hasRole;
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Column(name = "MAKERID")
    private String makerId;
    @Column(name = "CHECKERID")
    private String checkerId;
    @Column(name = "MAKER_DT")
    private String makerDt;
    @Column(name = "CHECKER_DT")
    private String checkerDt;
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "UDF1")
    private String udf1;
    @Column(name = "UDF2")
    private String udf2;
    @Column(name = "UDF3")
    private String udf3;
    @Column(name = "UDF4")
    private String udf4;

    @OneToMany(targetEntity = EsbService.class)
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "SERVICE_ID")
    private List<EsbService> esbService;

    @OneToMany(targetEntity = EsbServiceProcess.class)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
    private List<EsbServiceProcess> esbServiceProcesses;
}
