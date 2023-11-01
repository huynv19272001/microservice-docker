package com.lpd.esb.service.econtract.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EcontractRecipientsDTO {
    @JsonProperty("is_esign")
    private boolean isEsign;
    private String recipientId;
    private String email;
    private String personalName;
    private String telephoneNumber;
    private String contactId;
    private int page;
    private EcontractFieldBoxDTO fieldBox;
}
