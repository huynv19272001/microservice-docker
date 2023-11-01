package com.esb.card.dto.telcoRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by cuongnm on 2022-08-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbTelCoBodyDTO {
    @JsonProperty("ContentData")
    private String contentData;
}
