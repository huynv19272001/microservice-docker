package com.esb.card.dto.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;

/**
 * Created by cuongnm10 on 2022-07-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "ESB_ERROR_PARTNER_MESSAGE")
public class EsbErrorPartnerMessageEntity {
    @Id
    @Column(name = "CODE_ID")
    private Integer codeId;
    @Basic
    @Column(name = "SERVICE_ID")
    private String serviceId;
    @Basic
    @Column(name = "ERROR_CODE")
    private String errorCode;
    @Basic
    @Column(name = "ERROR_DESC")
    private String errorDesc;
    @Basic
    @Column(name = "PARTNER_ID")
    private String partnerId;
    @Basic
    @Column(name = "PARTNER_CODE")
    private String partnerCode;
    @Basic
    @Column(name = "PARTNER_DESC")
    private String partnerDesc;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic
    @Column(name = "MAKER_ID")
    private String makerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ",timezone = "Asia/Ho_Chi_Minh")
    @Basic
    @Column(name = "MAKER_DT")
    private String makerDt;

}

