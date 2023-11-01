package com.lpb.napas.ecom.repository;

import com.lpb.napas.ecom.model.EsbSystemEcomLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEsbSystemEcomLogRepository extends JpaRepository<EsbSystemEcomLog, String> {
    @Query(value = "SELECT USER_ESB.SEQ_ESB_SYS_LOG.NEXTVAL FROM DUAL",
            nativeQuery = true)
    Integer getAppMsgID();

    @Query(value = "SELECT A FROM EsbSystemEcomLog A WHERE A.requestorTransId=:requestorTransId AND A.methodAction=:methodAction")
    EsbSystemEcomLog checkExitsTransaction(@Param("requestorTransId") String requestorTransId, @Param("methodAction") String methodAction);

    @Query(value = "SELECT A FROM EsbSystemEcomLog A WHERE A.requestorTransId=:requestorTransId AND A.methodAction=:methodAction")
    List<EsbSystemEcomLog> getListEsbSystemEcomLog(@Param("requestorTransId") String requestorTransId, @Param("methodAction") String methodAction);
}
