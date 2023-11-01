package com.esb.transaction.respository;

import com.esb.transaction.model.EsbSystemEcomLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEsbSystemEcomLogRepository extends JpaRepository<EsbSystemEcomLog, String> {
    @Query(value = "SELECT USER_ESB.SEQ_ESB_SYS_LOG.NEXTVAL FROM DUAL",
            nativeQuery = true)
    Integer getAppMsgID();
}
