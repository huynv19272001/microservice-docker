package com.lpb.esb.service.common.model.request.infocustomerbill;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class ErroDTO {
    private String errorCode;
    private String errorDesc;
    private String refCode;
    private String refDesc;
}
