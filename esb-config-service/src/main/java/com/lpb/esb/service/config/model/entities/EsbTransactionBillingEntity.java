package com.lpb.esb.service.config.model.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Table(name = "ESB_TRANSACTION_BILLING")
public class EsbTransactionBillingEntity {
    @Id
    @Column(name = "APPMSG_ID")
    private String appMsgId;
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "EXPIRED_DT")
    private String expiredDt;
    @Column(name = "TRN_DT")
    private String trnDt;
    @Column(name = "VALUE_DT")
    private String valueDt;
    @Column(name = "STMT_DT")
    private String stmtDt;
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Column(name = "PRODUCT_CODE")
    private String productCod;
    @Column(name = "REQUEST_ACCOUNT")
    private String requestAccount;
    @Column(name = "RECEIVE_ACCOUNT")
    private String receiveAccount;
    @Column(name = "SETTLE_AMOUNT")
    private Long settleAmount;
    @Column(name = "SRVC_TXN_CCY")
    private Date srvcTxnCcy;
    @Column(name = "TOTAL_AMT")
    private Long totalAmt;
    @Column(name = "PAY_DT")
    private String payDt;
    @Column(name = "PAY_STAT")
    private String payStat;
    @Column(name = "BILL_INFO")
    private String billInfo;
    @Column(name = "TRACE_NO")
    private String traceNo;
}


