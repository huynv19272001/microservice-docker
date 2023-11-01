package com.lpb.esb.etax.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lpb.esb.etax.payment.model.entity.EtaxReceiptInfo;

@Repository
public interface EtaxReceiptInfoRepository extends JpaRepository<EtaxReceiptInfo, String> {

	EtaxReceiptInfo findByTransId(String transId);

    @Query(value = "SELECT * FROM ESB_ETAX_RECEIPT p WHERE p.REFERENCE_NUMBER = ?1", nativeQuery = true)
    EtaxReceiptInfo findByRefNo(String refNo);

}
