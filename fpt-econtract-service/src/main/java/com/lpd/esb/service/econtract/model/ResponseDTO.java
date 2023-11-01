package com.lpd.esb.service.econtract.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ResponseDTO<T> {
    private EsbDTO header;
    private T body;
}
