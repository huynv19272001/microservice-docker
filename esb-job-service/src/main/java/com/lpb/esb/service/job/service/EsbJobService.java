package com.lpb.esb.service.job.service;

import com.lpb.esb.service.job.model.entities.EsbJobScheduleEntity;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-09-10
 */
@Service
public interface EsbJobService {
    EsbJobScheduleEntity findJobByJobName(String jobName);
}
