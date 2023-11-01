package com.lpb.esb.service.sms.model.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Created by cuongnm10 on 2022-07-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "MID_TELCO_UPDATE")
public class MidTelcoUpdateEntity {
    @Id
    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Basic
    @Column(name = "TELCO_CODE")
    private String telcoCode;
    @Basic
    @Column(name = "SENT_TIME")
    private String sentTime;
    @Basic
    @Column(name = "SENDER")
    private String sender;
    @Basic
    @Column(name = "KEYWORD")
    private String keyword;
    @Basic
    @Column(name = "MESSAGE_ID")
    private String messageId;
    @Basic
    @Column(name = "STATUS")
    private String status;
}
