package com.lpd.esb.service.econtract.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EcontractBodyDTO {
    private String refId;
    private String file;
    private String fileName;
    @JsonProperty("is_marker")
    private boolean isMarker;
    private ArrayList<EcontractHeaderFieldsDTO> headerFields;
    private ArrayList<EcontractRecipientsDTO> recipients;
}
