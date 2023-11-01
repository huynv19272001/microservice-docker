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

public class BodyDTO {
    @JsonProperty("SettleBill")
    private SettleBillDTO settleBill;
}
