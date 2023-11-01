package com.lpb.esb.service.common.model.request.infocustomerbill;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor

public class DataQueryDTO {
    private BodyDTO body;
    private HeaderDTO header;
}
