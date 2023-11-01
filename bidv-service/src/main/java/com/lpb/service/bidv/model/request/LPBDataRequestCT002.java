package com.lpb.service.bidv.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LPBDataRequestCT002 {
    @JsonProperty("BankId")
    private String bankID;
    @JsonProperty("ExportDate")
    private String exportDate;
}
