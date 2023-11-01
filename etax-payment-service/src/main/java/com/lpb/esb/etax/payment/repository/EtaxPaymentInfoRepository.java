package com.lpb.esb.etax.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lpb.esb.etax.payment.model.entity.EtaxPaymentInfo;

@Repository
public interface EtaxPaymentInfoRepository extends JpaRepository<EtaxPaymentInfo, String> {

	EtaxPaymentInfo findByTransId(String transId);

    @Query(value = "SELECT * FROM ESB_ETAX_PAYMENT p WHERE p.REFERENCE_NUMBER = ?1 AND p.TRANS_PROGRESS = 'C' AND p.TRANS_STATUS = 'S'", nativeQuery = true)
    EtaxPaymentInfo findByRefNo(String refNo);

}
