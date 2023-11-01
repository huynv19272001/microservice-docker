package com.esb.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ESB_SYSTEM_ECOM_LOG")
public class EsbSystemEcomLog {
    @Id
    @Column(name = "MSG_ID")
    @JsonProperty("MSG_ID")
    private String msgId;
    @Column(name = "APP_ID")
    @JsonProperty("APP_ID")
    private String appId;
    @Column(name = "REQUESTOR_CODE")
    @JsonProperty("REQUESTOR_CODE")
    private String requestorCode;
    @Column(name = "REQUESTOR_TRANS_ID")
    @JsonProperty("REQUESTOR_TRANS_ID")
    private String requestorTransId;
    @Column(name = "STATUS")
    @JsonProperty("STATUS")
    private String status;
    @Column(name = "ERROR_CODE")
    @JsonProperty("ERROR_CODE")
    private String errorCode;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    @JsonProperty("createDate")
    private Date createDate;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    @JsonProperty("updatedDate")
    private Date updatedDate;
    @Column(name = "ERROR_DESC")
    @JsonProperty("ERROR_DESC")
    private String errorDesc;
    @Column(name = "RECORD_STATS")
    @JsonProperty("RECORD_STATS")
    private String recordStats;
    @Column(name = "UDF_1")
    @JsonProperty("UDF_1")
    private String udf1;
    @Column(name = "UDF_2")
    @JsonProperty("UDF_2")
    private String udf2;
    @Column(name = "UDF_3")
    @JsonProperty("UDF_3")
    private String udf3;
    @Column(name = "UDF_4")
    @JsonProperty("UDF_4")
    private String udf4;
    @Column(name = "UDF_5")
    @JsonProperty("UDF_5")
    private String udf5;
    @Column(name = "METHOD_ACTION")
    @JsonProperty("METHOD_ACTION")
    private String methodAction;
    @Column(name = "LAST_EVENT_SEQ_NO")
    @JsonProperty("LAST_EVENT_SEQ_NO")
    private Integer lastEventSeqNo;
}
