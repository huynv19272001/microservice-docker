package com.lpb.napas.ecom.repository;

import com.lpb.napas.ecom.model.EsbTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IEsbTransactionRepository extends JpaRepository<EsbTransaction, String> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE ESB_TRANSACTION set REQUEST_REF_NO =?1 where APPMSG_ID =?2",
            nativeQuery = true)
    void updateEsbTransaction(String requestRefNo,  String appMsgId);
}
