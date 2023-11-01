package com.lpb.esb.service.config.model.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Created by tudv1 on 2022-07-08
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table(name = "ESB_SETTLE_ACCOUNT_DETAIL")
public class EsbSettleAccountDetailEntity {
    @Id
    @Column(name = "ACCOUNT_ID")
    private String accountId;
    @Basic
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Basic
    @Column(name = "AC_NO")
    private String accNo;
    @Basic
    @Column(name = "AC_BRN")
    private String accBranch;
    @Basic
    @Column(name = "AC_DESC")
    private String accDesc;
    @Basic
    @Column(name = "CUSTOMER_NO")
    private String accCusNo;
}
