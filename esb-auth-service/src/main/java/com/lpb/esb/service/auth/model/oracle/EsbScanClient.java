package com.lpb.esb.service.auth.model.oracle;

import com.lpb.esb.service.auth.model.oracle.id.EsbScanClientID;
import lombok.*;

import javax.persistence.*;

/**
 * Created by tudv1 on 2021-07-13
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "ESB_SCAN_CLIENT")
@IdClass(EsbScanClientID.class)
public class EsbScanClient {
    @Id
    @Column(name = "SCAN_IP")
    private String scanIp;
    @Column(name = "RANGE_IP")
    @Basic
    private String rangeIp;
    @Column(name = "FROM_")
    @Basic
    private int from;
    @Column(name = "TO_")
    @Basic
    private int to;
    @Id
    @Column(name = "UDF_1")
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
    @Column(name = "RECORD_STATS")
    @Basic
    private String recordStats;
}


