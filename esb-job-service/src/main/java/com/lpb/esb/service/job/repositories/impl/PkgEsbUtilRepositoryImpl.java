package com.lpb.esb.service.job.repositories.impl;

import com.lpb.esb.service.job.repositories.PkgEsbUtilRepository;
import com.lpb.esb.service.job.utils.PkgEsbUtilConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

/**
 * Created by tudv1 on 2021-09-13
 */
@Repository
@Log4j2
public class PkgEsbUtilRepositoryImpl implements PkgEsbUtilRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void esbWriteTblLog(String apiCode, String logType, String contentLog) {
        String spName = PkgEsbUtilConstant.SP_WRITE_TBL_LOG;
        log.info("Start call procedure {}", spName);
        StoredProcedureQuery sp = entityManager.createStoredProcedureQuery(spName);
        sp.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);

        sp.setParameter(1, apiCode);
        sp.setParameter(2, logType);
        sp.setParameter(3, contentLog);
        sp.execute();
    }
}
