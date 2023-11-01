package com.lpb.esb.service.common.model.request.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "CUSTOMER")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    @JsonProperty("user_id")
    @JacksonXmlProperty(localName = "USER_ID")
    private String userId;
    @JsonProperty("customer_no")
    @JacksonXmlProperty(localName = "CUSTOMER_NO")
    private String customerNo;
    @JsonProperty("kind_of_otp")
    @JacksonXmlProperty(localName = "KIND_OF_OTP")
    private String kindOfOtp;
}
