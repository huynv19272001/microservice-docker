package com.lpb.esb.service.tct.model.request.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created by cuongnm10 on 2022-06-22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EsbTctResponseDTO {
    private EsbTctHeaderDTO header;
    private EsbTctBodyDTO body;
}
