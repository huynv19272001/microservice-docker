package com.lpb.esb.service.sms.repositories;

import com.lpb.esb.service.sms.model.entities.MidSmsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MidSmsCategoryRepository extends JpaRepository<MidSmsCategoryEntity, String> {
}
