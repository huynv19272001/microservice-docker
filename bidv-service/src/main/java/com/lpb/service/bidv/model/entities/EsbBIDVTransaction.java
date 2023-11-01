package com.lpb.service.bidv.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ESB_BIDV_TRANSACTION")
public class EsbBIDVTransaction {
    @Id
    @Column(name = "APP_ID")
    private String appId;
    @Column(name = "MSG_ID")
    private String msgId;
    @Lob
    @Column(name = "REQUEST_ESB")
    private String requestEsb;
    @Lob
    @Column(name = "RESPONSE_ESB")
    private String responseEsb;
    @Lob
    @Column(name = "REQUEST_PARTNER")
    private String requestPartner;
    @Lob
    @Column(name = "RESPONSE_PARTNER")
    private String responsePartner;
    @Column(name = "ERROR_CODE")
    private String errorCode;
    @Column(name = "ERROR_DESC")
    private String errorDesc;
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Column(name = "CREATE_DT")
    private Date createDt;
}
