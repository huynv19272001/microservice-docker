package com.lpb.napas.ecom.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ESB_SERVICE")
public class EsbService {
    @Id
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PROVIDER")
    private String provider;
    @Column(name = "MAKERID")
    private String makerId;
    @Column(name = "CHECKERID")
    private String checkerId;
    @Column(name = "MAKER_DT")
    private String makerDt;
    @Column(name = "CHECKER_DT")
    private String checkerDt;
    @Column(name = "RECORD_STATS")
    private String recordStat;
    @Column(name = "START_DT")
    private String startDt;
    @Column(name = "EXPIRED_DT")
    private String expiredDt;
    @Column(name = "UDF_1")
    private String udf1;
    @Column(name = "UDF_2")
    private String udf2;
    @Column(name = "UDF_3")
    private String udf3;
    @Column(name = "UDF_4")
    private String udf4;

    @Column(name = "SERVICE_TYPE")
    private String serviceType;
    @Column(name = "CR_HOLD_AC_NO")
    private String CRHoldAcNo;
    @Column(name = "CR_HOLD_CCY")
    private String CRHoldCCY;
    @Column(name = "TXN_BRANCH")
    private String txnBranch;
    @Column(name = "CR_HOLD_BRN")
    private String CRHoldBRN;
    @Column(name = "AUTHEN_OTP")
    private String authenOTP;
    @Column(name = "DR_HOLD_AC_NO")
    private String DRHoldAcNo;
    @Column(name = "DR_HOLD_CCY")
    private String DRHoldCYY;
    @Column(name = "DR_HOLD_BRN")
    private String DRHoldBRN;
}