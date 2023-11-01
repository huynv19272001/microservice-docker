package com.lpb.esb.service.pti.model.pti;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class PtiResponse<T> {
    private String code;
    private String message;
    private String systemMessage;
    private T data;
    @JsonProperty(value = "Total")
    private int total;
}
