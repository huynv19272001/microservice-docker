package com.lpb.esb.etax.payment.repository;
// EsbEtaxToken

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lpb.esb.etax.payment.model.entity.EsbEtaxToken;

@Repository
public interface EsbEtaxTokenRepository extends JpaRepository<EsbEtaxToken, Integer> {
	
	@Query(value = "SELECT * FROM ESB_ETAX_TOKEN t WHERE t.LINK_REQ_ID = ?1 AND t.CREATED_DATE = TRUNC(SYSDATE,'DD') AND t.STATUS = 'I'", nativeQuery = true)
	EsbEtaxToken findNewRequest2Token(String requestId);
	
	@Query(value = "SELECT * FROM ESB_ETAX_TOKEN t WHERE t.UNLINK_REQ_ID = ?1 AND t.STATUS = 'O'", nativeQuery = true)
	EsbEtaxToken findTokenByUnLinkReq(String requestId);
	
	@Query(value = "SELECT * FROM ESB_ETAX_TOKEN t WHERE t.TOKEN_DATA = ?1 AND t.STATUS = 'O'", nativeQuery = true)
	EsbEtaxToken findTokenData(String tokenData);

}
