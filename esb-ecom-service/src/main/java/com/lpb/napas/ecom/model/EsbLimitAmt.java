package com.lpb.napas.ecom.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ESB_LIMIT_AMT")
public class EsbLimitAmt {
    @Id
    @Column(name = "LMT_CODE")
    private String lmtCode;
    @Column(name = "CUSTOMER")
    private String customer;
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Column(name = "BASE_CCY")
    private String baseCcy;
    @Column(name = "BASE_MIN_AMT")
    private Integer baseMinAmt;
    @Column(name = "BASE_MAX_AMT")
    private Integer baseMaxAmt;
    @Column(name = "CCY")
    private String ccy;
    @Column(name = "MIN_AMT")
    private Integer minAmt;
    @Column(name = "MAX_AMT")
    private Integer maxAmt;
    @Column(name = "START_DT")
    private Date startDt;
    @Column(name = "EXPIRED_DT")
    private Date expiredDt;
    @Column(name = "ASSIGN_DT")
    private Date assignDt;
    @Column(name = "ASSIGN_BY")
    private String assignBy;
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "MAKER_DT")
    private Date makerDt;
    @Column(name = "MAKER_ID")
    private String makerId;
    @Column(name = "CHECKER_DT")
    private Date checkerDt;
    @Column(name = "CHECKER_ID")
    private String checkerId;
}
