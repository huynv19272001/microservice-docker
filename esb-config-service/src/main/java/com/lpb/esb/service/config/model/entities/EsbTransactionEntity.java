package com.lpb.esb.service.config.model.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tudv1 on 2022-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "ESB_TRANSACTION")
public class EsbTransactionEntity {
    @Id
    @Column(name = "APPMSG_ID")
    private String appMsgId;
    @Column(name = "TRN_BRANCH")
    private String trnBranch;
    @Column(name = "TRN_DESC")
    private String trnDesc;
    @Column(name = "EBK_USER")
    private String ebkUser;
    @Column(name = "CUSTOMER_NO")
    private String customerNo;
    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Column(name = "KIND_OF_OTP")
    private String kindOfOtp;
    @Column(name = "AUTHEN_OTP_CHANEL")
    private String authenOtpChannel;
    @Column(name = "AUTHEN_OTP_STAT")
    private String authenOtpStat;
    @Column(name = "AUTHEN_OTP_DT")
    private String authenOtpDt;
    @Column(name = "INIT_DT")
    private String initDt;
    @Column(name = "TRN_DT")
    private String trnDt;
    @Column(name = "VALUE_DT")
    private String valueDt;
    @Column(name = "EXPIRED_DT")
    private String expiredDt;
    @Column(name = "STMT_DT")
    private String stmtDt;
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "EVENT_CODE")
    private String eventCode;
    @Column(name = "LAST_EVENT_SEQ_NO")
    private String lastEventSeqNo;
    @Column(name = "RELATED_TRN_ID")
    private String relatedTrnId;
    @Column(name = "TRN_STAT")
    private String trnStat;
    @Column(name = "MODULE_CODE")
    private String moduleCode;
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Column(name = "TXN_CODE")
    private String txnCode;
    @Column(name = "REQUEST_BY")
    private String requestBy;
    @Column(name = "REQUEST_CHANEL")
    private String requestChannel;
    @Column(name = "REQUEST_TERM_ID")
    private String requestTermId;
    @Column(name = "REQUEST_REF_NO")
    private String requestRefNo;
    @Column(name = "REQUEST_DT")
    private String requestDt;
    @Column(name = "REQUEST_TRN_CODE")
    private String requestTrnCode;
    @Column(name = "REQ_CONFIRM_STAT")
    private String requestConfirmStat;
    @Column(name = "REQ_CONFIRM_DT")
    private String requestConfirmDt;
    @Column(name = "RESPONSE_BY")
    private String responseBy;
    @Column(name = "RESPONSE_CHANEL")
    private String responseChannel;
    @Column(name = "OFFLINE_PROCESS")
    private String offlineProcess;
    @Column(name = "LAST_PROCESS")
    private String lastProcess;
    @Column(name = "LAST_PROC_ESR")
    private String lastProcEsr;
    @Column(name = "LAST_PROC_DT")
    private String lastProcDt;
    @Column(name = "APPLICATION_ID")
    private String applicationId;
    @Column(name = "MAKER_ID")
    private String makerId;
    @Column(name = "MAKER_DT")
    private String makerDt;
    @Column(name = "CHECKER_ID")
    private String checkerId;
    @Column(name = "CHECKER_DT")
    private String checkerDt;

}
