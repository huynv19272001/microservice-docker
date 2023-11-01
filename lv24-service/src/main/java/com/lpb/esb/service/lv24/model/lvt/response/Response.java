package com.lpb.esb.service.lv24.model.lvt.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Response {
    private ResponseHeader responseHeader;
}
