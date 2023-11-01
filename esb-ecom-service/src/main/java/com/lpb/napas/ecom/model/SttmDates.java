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
@Table(name = "STTM_DATES", schema = "EBANK")
public class SttmDates {
    @Id
    @Column(name = "BRANCH_CODE")
    private String branchCode;
    @Column(name = "TODAY")
    private Date today;
    @Column(name = "PREV_WORKING_DAY")
    private String prevWorkingDay;
    @Column(name = "NEXT_WORKING_DAY")
    private String nextWorkingDay;
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "AUTH_STAT")
    private String authStat;
    @Column(name = "MOD_NO")
    private String modNo;
    @Column(name = "MAKER_ID")
    private String makerId;
    @Column(name = "MAKER_DT_STAMP")
    private String makerDtStamp;
    @Column(name = "CHECKER_ID")
    private String checkerId;
    @Column(name = "CHECKER_DT_STAMP")
    private String checkerDtStamp;
    @Column(name = "ONCE_AUTH")
    private String onceAuth;

    public SttmDates(Date today) {
        this.today = today;
    }

}
