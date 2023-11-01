package com.lpb.esb.service.query.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbAccountInfoDTO {
    private String accountName;
    private String DOB;
    private String accountId;
    private String other;
    private String accountDesc;
    private String email;
    private String mobileNo;
    private String UIDValue;
    private String acFromDt;
    private String acToDt;
}
