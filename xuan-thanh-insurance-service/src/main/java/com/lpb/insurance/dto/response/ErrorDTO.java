package com.lpb.insurance.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    @JsonProperty("ErrorCode")
    private String errorCode;
    @JsonProperty("ErrorMess")
    private String errorMess;
    @JsonProperty("ErrorMess_EN")
    private String errorMessEN;
    @JsonProperty("ErrorType")
    private String errorType;
    @JsonProperty("CreateDate")
    private String createDate;
    @JsonProperty("UpdateDate")
    private String updateDate;
}
