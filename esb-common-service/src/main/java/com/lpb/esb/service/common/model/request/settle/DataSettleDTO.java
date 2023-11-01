package com.lpb.esb.service.common.model.request.settle;


import com.lpb.esb.service.common.model.request.infocustomerbill.HeaderDTO;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor

public class DataSettleDTO {
    private HeaderDTO header;
    private BodyDTO body;
}
