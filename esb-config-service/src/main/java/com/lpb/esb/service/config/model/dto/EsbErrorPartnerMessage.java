package com.lpb.esb.service.config.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by cuongnm10 on 2022-08-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbErrorPartnerMessage {

    private Integer codeId;

    private String serviceId;

    private String errorCode;

    private String errorDesc;

    private String partnerId;

    private String partnerCode;

    private String partnerDesc;

    private String description;

    private String makerId;

    private String makerDt;
}
