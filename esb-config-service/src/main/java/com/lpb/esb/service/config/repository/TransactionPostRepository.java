package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionPostRepository extends JpaRepository<EsbUserEntity, String> {
    @Query(value = "SELECT A.TRN_BRANCH as trnBranch, A.TRN_DESC as TrnDesc, B.AC_BRANCH as AcBranch,\n" +
        "B.LCY_AMOUNT as lcyAmount, B.AC_NO as acNo, B.RELATED_CUSTOMER as relatedCustomer, \n" +
        "B.DRCR_IND as drcrInd, B.AC_CCY as acCcy \n" +
        "FROM ESB_TRANSACTION A JOIN ESB_TRANSACTION_POST B ON A.APPMSG_ID = B.APPMSG_ID\n" +
        "WHERE A.TRN_STAT IN ('P','I') and A.AUTHEN_OTP_STAT = 'S' and B.RECORD_STAT = 'O' \n" +
        "AND B.APPMSG_ID = :appMsgId", nativeQuery = true)
    List<TransactionPostRepository.TransactionPost> getTransactionPost(@Param("appMsgId") String appMsgId);

    interface TransactionPost {
        String getTrnBranch();

        String getTrnDesc();

        String getAcBranch();

        String getLcyAmount();

        String getAcNo();

        String getRelatedCustomer();

        String getDrcrInd();

        String getAcCcy();
    }
}
