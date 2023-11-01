package com.lpb.esb.service.common.model.request.infocustomerbill;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranDetailDTO {
    @JsonProperty("fromDt")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fromDt;

    @JsonProperty("toDt")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String toDt;

    @JsonProperty("listTranInfo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TranDTO> listTranInfo;
}
