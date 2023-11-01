package com.lpb.esb.service.lv24.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EsbHeaderInfoDTO {
    @NotBlank
    private String msgId;
    private String serviceId;
    private String productCode;
    private String userId;
    private String password;
    private String operation;
}
