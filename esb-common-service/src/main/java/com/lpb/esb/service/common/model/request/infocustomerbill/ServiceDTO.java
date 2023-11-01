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
public class ServiceDTO {
    @JsonProperty("serviceId")
    private String serviceId;
    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("requestAccount")
    private String requestAccount;
    @JsonProperty("receiveAccount")
    private String receiveAccount;
    @JsonProperty("merchantId")
    private String merchantId;
}
