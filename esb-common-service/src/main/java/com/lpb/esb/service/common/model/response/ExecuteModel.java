package com.lpb.esb.service.common.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lpb.esb.service.common.utils.code.ExecuteCode;
import lombok.*;

/**
 * Created by tudv1 on 2021-08-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class ExecuteModel<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ExecuteCode executeCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
