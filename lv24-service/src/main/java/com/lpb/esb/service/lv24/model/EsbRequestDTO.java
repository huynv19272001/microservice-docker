package com.lpb.esb.service.lv24.model;

import lombok.*;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class EsbRequestDTO {
    @Valid
    private EsbHeaderInfoDTO header;
    private EsbBodyInfoDTO body;
}
