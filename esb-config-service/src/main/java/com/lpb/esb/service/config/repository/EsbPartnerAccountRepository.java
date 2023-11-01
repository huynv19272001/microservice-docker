package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbPartnerAccountEntity;
import com.lpb.esb.service.config.model.entities.id.EsbPartnerAccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tudv1 on 2022-07-08
 */
@Repository
public interface EsbPartnerAccountRepository extends JpaRepository<EsbPartnerAccountEntity, EsbPartnerAccountId> {
    EsbPartnerAccountEntity findByServiceIdAndProviderIdAndRecordStat(String serviceId, String providerId, String recordStat);

    List<EsbPartnerAccountEntity> findByRecordStatAndServiceIdAndProviderIdIn(String recordStat, String serviceId, List<String> providerIds);
}
