package com.lpb.esb.service.common.model.request.settle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class RsBillDTO {

    @JsonProperty("bill_code")
    private String bill_code;
    @JsonProperty("bill_desc")
    private String bill_desc;
    @JsonProperty("bill_amount")
    private String bill_amount;
    @JsonProperty("other_info")
    private String other_info;
    @JsonProperty("bill_type")
    private String bill_type;
    @JsonProperty("bill_status")
    private String bill_status;
    @JsonProperty("bill_id")
    private String bill_id;
}
