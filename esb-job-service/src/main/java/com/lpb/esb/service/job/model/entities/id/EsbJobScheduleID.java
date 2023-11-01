package com.lpb.esb.service.job.model.entities.id;

import lombok.*;

import java.io.Serializable;

/**
 * Created by tudv1 on 2021-09-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbJobScheduleID implements Serializable {
    private String jobId;
    private String jobName;
}
