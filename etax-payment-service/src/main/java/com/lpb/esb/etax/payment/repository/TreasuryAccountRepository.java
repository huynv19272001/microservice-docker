package com.lpb.esb.etax.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lpb.esb.etax.payment.model.entity.TreasuryAccount;

@Repository
public interface TreasuryAccountRepository extends JpaRepository<TreasuryAccount, Integer> {
	
	@Query(value = "SELECT USER_ESB.SEQ_ESB_SYS_LOG.NEXTVAL FROM DUAL", nativeQuery = true)
	Integer getTransId();
	
	TreasuryAccount findByTreasury4Code(String treasury4Code);
	
	@Query(value = "SELECT * FROM V_TREASURY_ACCOUNT t WHERE t.MA_KB_4SO = ?1 AND t.RECORD_STATUS = 'O'", nativeQuery = true)
	TreasuryAccount findAccountByTreasury4Code(String treasury4Code);
	
}
