package com.esb.transaction.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ESB_TRANSACTION_POST")
public class EsbTransactionPost {
    @Id
    @Column(name = "APPMSG_ID")
    private String appMsgId;
    @Column(name = "EVENT_CODE")
    private String eventCode;
    @Column(name = "EVENT_SEQ_NO")
    private String eventSeqNo;
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "TRN_STAT")
    private String trnStat;
    @Column(name = "AUTH_STAT")
    private String authStat;
    @Column(name = "POST_DATE")
    private String postDate;
    @Column(name = "VALUE_DT")
    private String valueDt;
    @Column(name = "TRN_DT")
    private String trnDt;
    @Column(name = "AC_NO")
    private String accNo;
    @Column(name = "AC_CCY")
    private String accCcy;
    @Column(name = "AC_BRANCH")
    private String accBranch;
    @Column(name = "CUST_GL")
    private String custGL;
    @Column(name = "RELATED_CUSTOMER")
    private String relatedCustomer;
    @Column(name = "RELATED_ACCOUNT")
    private String relatedAccount;
    @Column(name = "RELATED_REF_NO")
    private String relatedRefNo;
    @Column(name = "DRCR_IND")
    private String drcrInd;
    @Column(name = "AMOUNT")
    private String amount;
    @Column(name = "EXC_RATE")
    private String excRate;
    @Column(name = "LCY_AMOUNT")
    private String lcyAmount;
    @Column(name = "AMOUNT_TAG")
    private String amountTag;
    @Column(name = "CURR_NO")
    private String currNo;
    @Column(name = "ENTRY_SEQ_NO")
    private String entrySeqNo;
    @Column(name = "CORE_BLOCK_NO")
    private String coreBlockNo;
    @Column(name = "CORE_BLOCK_DT")
    private String coreBlockDt;
    @Column(name = "CORE_REF_NO")
    private String coreRefNo;
    @Column(name = "CORE_TRN_DT")
    private String coreTrnDt;
    @Column(name = "UDF_1")
    private String udf1;
    @Column(name = "UDF_2")
    private String udf2;
    @Column(name = "UDF_3")
    private String udf3;
    @Column(name = "UDF_4")
    private String udf4;

    @OneToMany(targetEntity = EsbTransaction.class)
    @JoinColumn(name = "APPMSG_ID", referencedColumnName = "APPMSG_ID")
    private List<EsbTransaction> esbTransactions;
}
