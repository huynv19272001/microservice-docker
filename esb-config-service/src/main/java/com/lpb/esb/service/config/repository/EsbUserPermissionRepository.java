package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbUserPermissionEntity;
import com.lpb.esb.service.config.model.entities.id.EsbUserPermissionID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tudv1 on 2021-11-17
 */
@Repository
public interface EsbUserPermissionRepository extends JpaRepository<EsbUserPermissionEntity, EsbUserPermissionID> {
    Page<EsbUserPermissionEntity> findAllByUsername(String username, Pageable makerDt);
}
