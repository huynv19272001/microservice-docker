package com.lpb.service.sql.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParamDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("value")
    private String value;
    @JsonProperty("idx")
    private String idx;
}
