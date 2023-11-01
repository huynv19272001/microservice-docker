package com.lpb.esb.service.common.model.response;

import lombok.*;

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
