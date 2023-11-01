package com.lpb.esb.service.config.model.entities;

import com.lpb.esb.service.config.model.entities.id.EsbPartnerAccountDetailId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table(name = "ESB_PARTNER_ACCOUNT_DETAIL")
@IdClass(EsbPartnerAccountDetailId.class)
public class EsbPartnerAccountDetailEntity implements Serializable {
    @Id
    @Column(name = "PROVIDER_ID")
    private String providerId;
    @Id
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Id
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Id
    @Column(name = "ACCOUNT_TYPE")
    private String accountType;
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
