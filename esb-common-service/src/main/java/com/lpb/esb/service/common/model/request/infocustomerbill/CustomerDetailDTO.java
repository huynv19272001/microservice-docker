package com.lpb.esb.service.common.model.request.infocustomerbill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CustomerDetailDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("customerInfo")
    private CustomerDTO customerInfo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("settleAccountInfo")
    private List<SettleAccountDTO> settleAccountInfo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("listBillInfo")
    private List<BillDTO> listBillInfo;
}
