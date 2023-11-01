package com.lpb.esb.service.job.process;

import org.springframework.stereotype.Component;

/**
 * Created by tudv1 on 2021-09-14
 */
@Component
public interface NotifyTransProcess {
    void notifyTransaction(String accNo, String jobId, String urlWs, String serviceId, String productCode);
}
