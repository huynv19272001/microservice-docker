package com.lpb.esb.service.job.repositories;

import com.lpb.esb.service.job.model.entities.EsbJobScheduleEntity;
import com.lpb.esb.service.job.model.entities.id.EsbJobScheduleID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tudv1 on 2021-09-09
 */
@Repository
public interface EsbJobRepository extends JpaRepository<EsbJobScheduleEntity, EsbJobScheduleID> {
    EsbJobScheduleEntity findByJobNameAndRecordStats(String jobName, String recordStats);
}
