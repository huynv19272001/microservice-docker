package com.lpb.service.sql.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeaderInfoDTO {
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("source")
    private String source;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("serviceId")
    private String serviceId;
    @JsonProperty("operation")
    private String operation;
    @JsonProperty("productCode")
    private String productCode;
}
