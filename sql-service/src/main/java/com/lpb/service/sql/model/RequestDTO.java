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
public class RequestDTO {
    @JsonProperty("header")
    private HeaderInfoDTO header;
    @JsonProperty("body")
    private BodyInfoDTO body;
}
