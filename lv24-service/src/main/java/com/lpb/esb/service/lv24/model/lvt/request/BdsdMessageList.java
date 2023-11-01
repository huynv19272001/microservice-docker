package com.lpb.esb.service.lv24.model.lvt.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BdsdMessageList {
    private List<BdsdMessage> listMessage;
}
