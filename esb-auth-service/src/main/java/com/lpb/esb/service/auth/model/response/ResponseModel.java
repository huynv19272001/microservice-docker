package com.lpb.esb.service.auth.model.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ResponseModel<T> {
    private String errorCode;
    private String errorDesc;
    private T data;
}
