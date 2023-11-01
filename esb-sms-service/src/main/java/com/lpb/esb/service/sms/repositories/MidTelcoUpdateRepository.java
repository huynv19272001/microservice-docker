package com.lpb.esb.service.sms.repositories;

import com.lpb.esb.service.sms.model.entities.MidTelcoUpdateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MidTelcoUpdateRepository extends JpaRepository<MidTelcoUpdateEntity, String> {
}
