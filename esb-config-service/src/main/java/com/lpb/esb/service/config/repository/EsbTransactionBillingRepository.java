package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbTransactionBillingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EsbTransactionBillingRepository extends JpaRepository<EsbTransactionBillingEntity, String> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE ESB_TRANSACTION_BILLING set PAY_STAT = 'P', PAY_DT = SYSDATE where APPMSG_ID =?1 AND SERVICE_ID =?2",
        nativeQuery = true)
    void updateEsbTransactionBilling(String appMsgId, String serviceId);
}
