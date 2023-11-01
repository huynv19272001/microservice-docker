package com.lpd.esb.service.econtract.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ErrorBodyDTO {
    private String status;
    private JsonNode errorBody;
}
