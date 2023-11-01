package com.lpb.esb.service.job.service.impl;

import com.lpb.esb.service.job.model.entities.EsbJobScheduleEntity;
import com.lpb.esb.service.job.process.NotifyTransProcess;
import com.lpb.esb.service.job.repositories.PkgEsbUtilRepository;
import com.lpb.esb.service.job.service.EsbJobService;
import com.lpb.esb.service.job.service.JobScheduleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-09-13
 */
@Service("jobScheduleBvsnServiceImpl")
@Log4j2
public class JobScheduleBvsnServiceImpl implements JobScheduleService {

    @Value("${job.bvsn:JOB_BVSN}")
    String jobName;
    @Autowired
    EsbJobService esbJobService;
    @Autowired
    PkgEsbUtilRepository pkgEsbUtilRepository;
    @Autowired
    NotifyTransProcess notifyTransProcess;

    @Scheduled(fixedDelay = 1000 * 10, initialDelay = 1000 * 2)
    @Override
    public void runScheduleService() {
        EsbJobScheduleEntity job = esbJobService.findJobByJobName(this.jobName);
        if (job == null) {
            log.info("JOB {} not found", this.jobName);
            return;
        }
        log.info(job.toString());
//        pkgEsbUtilRepository.esbWriteTblLog(
//            PkgEsbUtilConstant.ESB_GATEWAY
//            , "INFO"
//            , jobName + " AC_NO: " + job.getUdf1()
//        );
        log.info("start send notify trans for job {}", jobName);
        notifyTransProcess.notifyTransaction(
            job.getUdf1()
            , job.getJobId()
            , job.getUdf2()
            , job.getUdf3()
            , job.getUdf4()
        );

    }
}
