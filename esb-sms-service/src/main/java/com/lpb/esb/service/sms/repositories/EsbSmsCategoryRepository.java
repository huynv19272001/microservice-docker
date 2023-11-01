package com.lpb.esb.service.sms.repositories;

import com.lpb.esb.service.sms.model.entities.EsbSmsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tudv1 on 2021-07-19
 */
@Repository
public interface EsbSmsCategoryRepository extends JpaRepository<EsbSmsCategoryEntity, String> {
}
