package com.lpb.esb.etax.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lpb.esb.etax.payment.model.entity.TreasuryCitadCode;

@Repository
public interface TreasuryCitadCodeRepository extends JpaRepository<TreasuryCitadCode, Integer> {

	@Query(value = "SELECT USER_ESB.SEQ_ESB_SYS_LOG.NEXTVAL FROM DUAL", nativeQuery = true)
	Integer getTransId();
	
//	List<TreasuryCitadCode> findByTreasuryCode(String treasuryCode);
	TreasuryCitadCode findByTreasuryCode(String treasuryCode);

}
