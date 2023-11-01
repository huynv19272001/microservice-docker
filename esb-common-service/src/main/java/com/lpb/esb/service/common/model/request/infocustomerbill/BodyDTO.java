package com.lpb.esb.service.common.model.request.infocustomerbill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BodyDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ServiceDTO service;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ServiceInfo serviceInfo;
    @JsonProperty("listCustomer")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CustomerDetailDTO> listCustomer;
    @JsonProperty("transactionInfo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TranDetailDTO transactionInfo;
}
