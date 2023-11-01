package com.lpb.service.sql.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BodyInfoDTO {
    @JsonProperty("params")
    private List<ParamDTO> params;
    @JsonProperty("rows")
    private List<ColumnDTO> rows;
}
