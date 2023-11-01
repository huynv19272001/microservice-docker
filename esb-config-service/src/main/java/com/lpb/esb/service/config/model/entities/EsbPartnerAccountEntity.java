package com.lpb.esb.service.config.model.entities;

import com.lpb.esb.service.config.model.entities.id.EsbPartnerAccountId;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tudv1 on 2022-07-08
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table(name = "ESB_PARTNER_ACCOUNT")
@IdClass(EsbPartnerAccountId.class)
public class EsbPartnerAccountEntity implements Serializable {
    @Id
    @Column(name = "PROVIDER_ID")
    private String providerId;
    @Id
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Basic
    @Column(name = "ACCOUNT_ID")
    private String accountId;
    @Basic
    @Column(name = "RECORD_STAT")
    private String recordStat;
}
