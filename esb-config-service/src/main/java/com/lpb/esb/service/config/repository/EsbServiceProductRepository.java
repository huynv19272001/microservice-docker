package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbServiceProductEntity;
import com.lpb.esb.service.config.model.entities.id.ServiceProductID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tudv1 on 2021-07-15
 */
@Repository
public interface EsbServiceProductRepository extends JpaRepository<EsbServiceProductEntity, ServiceProductID> {

}
