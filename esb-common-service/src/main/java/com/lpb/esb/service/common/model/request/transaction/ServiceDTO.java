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
public class ServiceDTO {
    @JsonProperty("merchant_id")
    private String merchantId;
    @JsonProperty("service_id")
    private String serviceId;
    @JsonProperty("product_code")
    private String productCode;
    @JsonProperty("request_account")
    private String requestAccount;
    @JsonProperty("receive_account")
    private String receiveAccount;
}

