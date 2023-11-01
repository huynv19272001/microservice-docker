package com.lpb.esb.etax.inquiry.repository;
// EsbEtaxToken

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lpb.esb.etax.inquiry.model.entity.EsbEtaxToken;

@Repository
public interface EsbEtaxTokenRepository extends JpaRepository<EsbEtaxToken, Integer> {
	
	@Query(value = "SELECT * FROM ESB_ETAX_TOKEN t WHERE t.TOKEN_DATA = ?1 AND t.STATUS = 'O'", nativeQuery = true)
	EsbEtaxToken findTokenData(String tokenData);
	
	@Query(value = "SELECT * FROM ESB_ETAX_TOKEN t WHERE t.ORIGINAL_DATA = ?1 AND t.STATUS = 'O'", nativeQuery = true)
	EsbEtaxToken findOriginalData(String originalData);

}
