package com.lpb.esb.service.common.model.request.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
    @JsonProperty("account_name")
    private String accountName;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("other")
    private String other;
    @JsonProperty("account_desc")
    private String accountDesc;
    @JsonProperty("email")
    private String email;
    @JsonProperty("mobile_no")
    private String mobileNo;
    @JsonProperty("uid_value")
    private String uidValue;
    @JsonProperty("ac_from_dt")
    private String acFromDt;
    @JsonProperty("ac_to_dt")
    private String acToDt;
}
