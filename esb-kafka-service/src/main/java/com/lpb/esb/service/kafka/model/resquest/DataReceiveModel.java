package com.lpb.esb.service.kafka.model.resquest;

import lombok.*;

/**
 * Created by tudv1 on 2021-07-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class DataReceiveModel {
    private String topic;
    private String message;
}
