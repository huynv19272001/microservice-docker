package com.lpb.service.bidv.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LPBRequest<T> {
    @JsonProperty("MsgId")
    private String msgId;
    @JsonProperty("Data")
    List<T> data;
}
