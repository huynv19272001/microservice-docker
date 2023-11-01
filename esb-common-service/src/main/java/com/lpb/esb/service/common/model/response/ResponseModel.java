package com.lpb.esb.service.common.model.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created by tudv1 on 2021-07-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResponseModel<T> {
    private LpbResCode resCode;
    private T data;
    private String errorCode;
    private String errorDesc;
}

