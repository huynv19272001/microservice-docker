package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbPartnerAccountDetailEntity;
import com.lpb.esb.service.config.model.entities.id.EsbPartnerAccountDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsbPartnerAccountDetailRepository extends JpaRepository<EsbPartnerAccountDetailEntity, EsbPartnerAccountDetailId> {
    List<EsbPartnerAccountDetailEntity> findByRecordStatAndServiceIdAndProductCodeAndProviderId(String recordStat,String serviceId, String productCode, String providerId);
}
