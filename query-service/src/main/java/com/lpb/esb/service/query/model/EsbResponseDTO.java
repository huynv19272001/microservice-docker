package com.lpb.esb.service.query.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EsbResponseDTO {
    private EsbHeaderInfoDTO header;
    private EsbBodyInfoDTO body;
}
