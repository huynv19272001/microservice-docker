package com.lpb.esb.settle.respository;

import com.lpb.esb.settle.service.model.EsbTranId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GetTranIDServiceRes extends JpaRepository<EsbTranId,String> {
    @Query(value = "SELECT USER_ESB.SEQ_ESB_SYS_LOG.NEXTVAL FROM DUAL"
        , nativeQuery = true)
    String getTranId();
}
