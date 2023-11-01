package com.esb.transaction.respository;

import com.esb.transaction.dto.TransactionPostInfoDTO;
import com.esb.transaction.model.EsbPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsbTransactionRepository extends JpaRepository<EsbPermission, String> {
    @Query("SELECT new com.esb.transaction.dto.TransactionPostInfoDTO(a.trnBranch, a.trnDesc, b.accBranch, b.lcyAmount, b.accNo, b.relatedCustomer, b.drcrInd, b.accCcy) " +
            "FROM EsbTransaction a JOIN a.esbTransactionPosts b " +
            "WHERE a.trnStat IN ('P','I') and a.authenOtpStat = 'S' and b.recordStat = 'O' " +
            "AND b.appMsgId = :appMsgId ")
    List<TransactionPostInfoDTO> loadTransactionPost(@Param("appMsgId") String appMsgId);
}
