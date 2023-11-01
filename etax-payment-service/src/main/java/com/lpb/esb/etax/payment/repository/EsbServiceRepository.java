package com.lpb.esb.etax.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lpb.esb.etax.payment.model.entity.EsbService;

@Repository
public interface EsbServiceRepository extends JpaRepository<EsbService, Integer> {
	
	EsbService findByServiceId(String serviceId);
	
}
