package com.lpb.esb.service.job.service.impl;

import com.lpb.esb.service.job.model.entities.EsbJobScheduleEntity;
import com.lpb.esb.service.job.repositories.EsbJobRepository;
import com.lpb.esb.service.job.service.EsbJobService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-09-10
 */
@Service
@Log4j2
public class EsbJobServiceImpl implements EsbJobService {
    @Autowired
    EsbJobRepository esbJobRepository;

    @Override
    public EsbJobScheduleEntity findJobByJobName(String jobName) {
        return esbJobRepository.findByJobNameAndRecordStats(jobName, "O");
    }
}
