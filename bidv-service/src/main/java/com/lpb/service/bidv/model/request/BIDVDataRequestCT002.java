
package com.lpb.service.bidv.model.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BIDVDataRequestCT002 {
    @JsonProperty("bank_id")
    private String bankID;
    @JsonProperty("export_date")
    private String exportDate;
}

