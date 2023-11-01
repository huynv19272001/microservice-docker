package com.lpb.esb.service.common.model.request.infocustomerbill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("custName")
    private String custName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("custId")
    private String custId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("custDesc")
    private String custDesc;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("custType")
    private String custType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("custStatus")
    private String custStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("custEmail")
    private String custEmail;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("custMobile")
    private String custMobile;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("uidValue")
    private String uidValue;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("providerId")
    private String providerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("otherInfo")
    private String otherInfo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("customer_no")
    private String customerNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("kind_of_otp")
    private String kindOfOtp;
}
