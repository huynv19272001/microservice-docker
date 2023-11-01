package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tudv1 on 2021-11-17
 */
@Repository
public interface EsbUserRepository extends JpaRepository<EsbUserEntity, String> {
}
