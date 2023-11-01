package com.lpd.esb.service.econtract.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EcontractGetTokenReqDTO {
    private String username;
    private String password;
    private boolean rememberMe;
    private String clientid;
    private String clientsecret;
}
