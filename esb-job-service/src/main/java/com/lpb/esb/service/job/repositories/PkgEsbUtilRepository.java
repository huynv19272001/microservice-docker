package com.lpb.esb.service.job.repositories;

import org.springframework.stereotype.Repository;

/**
 * Created by tudv1 on 2021-09-13
 */
@Repository
public interface PkgEsbUtilRepository {
    void esbWriteTblLog(String apiCode, String logType, String contentLog);
}
