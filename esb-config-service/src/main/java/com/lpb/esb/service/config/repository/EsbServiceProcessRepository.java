package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbServiceProcessEntity;
import com.lpb.esb.service.config.model.entities.id.ServiceProcessID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tudv1 on 2021-07-15
 */
@Repository
public interface EsbServiceProcessRepository extends JpaRepository<EsbServiceProcessEntity, ServiceProcessID> {
    List<EsbServiceProcessEntity> findAllByProductCode(String productCode);
}
