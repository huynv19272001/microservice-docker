package com.lpb.napas.ecom.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ESB_TRANSACTION")
public class EsbTransaction {
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
    @Column(name = "KIND_OF_OTP")
    private String kindOfOtp;
    @Column(name = "AUTHEN_OTP_CHANEL")
    private String authenOtpChanel;
    @Column(name = "AUTHEN_OTP_STAT")
    private String authenOtpStat;
    @Column(name = "AUTHEN_OTP_DT")
    private Date authenOtpDt;
    @Column(name = "INIT_DT")
    private Date initDt;
    @Column(name = "TRN_DT")
    private Date trnDt;
    @Column(name = "VALUE_DT")
    private Date valueDt;
    @Column(name = "EXPIRED_DT")
    private Date expiredDt;
    @Column(name = "STMT_DT")
    private Date stmtDt;
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @Column(name = "EVENT_CODE")
    private String eventCode;
    @Column(name = "LAST_EVENT_SEQ_NO")
    private Integer lastEventSeqNo;
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
    private String requestChanel;
    @Column(name = "REQUEST_TERM_ID")
    private String requestTermId;
    @Column(name = "REQUEST_REF_NO")
    private String requestRefNo;
    @Column(name = "REQUEST_DT")
    private String requestDt;
    @Column(name = "REQUEST_TRN_CODE")
    private String requestTrnCode;
    @Column(name = "REQ_CONFIRM_STAT")
    private String reqConfirmStat;
    @Column(name = "REQ_CONFIRM_DT")
    private String reqConfirmDt;
    @Column(name = "RESPONSE_BY")
    private String responseBy;
    @Column(name = "RESPONSE_CHANEL")
    private String responseChanel;
    @Column(name = "OFFLINE_PROCESS")
    private String offlineProcess;
    @Column(name = "LAST_PROCESS")
    private String lastProcess;
    @Column(name = "LAST_PROC_ESR")
    private Integer lastProcEsr;
    @Column(name = "LAST_PROC_DT")
    private Date lastProcDt;
    @Column(name = "APPLICATION_ID")
    private String applicationId;
    @Column(name = "MAKER_ID")
    private String makerId;
    @Column(name = "MAKER_DT")
    private Date makerDt;
    @Column(name = "CHECKER_ID")
    private String checkId;
    @Column(name = "CHECKER_DT")
    private Date checkerDt;
}
