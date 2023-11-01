package com.lpb.esb.service.sms.repositories.impl;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.sms.repositories.PkgEsbUtilRepository;
import com.lpb.esb.service.sms.utils.PkgEsbUtilConstant;
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
    public void esbLoadErrorDesc(LpbResCode lpbResCode) {
        String spName = PkgEsbUtilConstant.SP_LOAD_ERROR_DESC;
        log.info("Start call procedure {}", spName);
        StoredProcedureQuery sp = entityManager.createStoredProcedureQuery(spName);
        sp.registerStoredProcedureParameter(1, String.class, ParameterMode.INOUT);
        sp.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

        sp.setParameter(1, lpbResCode.getErrorCode());
        String errorCode = (String) sp.getOutputParameterValue(1);
        String errorDesc = (String) sp.getOutputParameterValue(2);
        log.info("ECODE: {}\t EDESC: {}", errorCode, errorDesc);
        lpbResCode.setErrorCode(errorCode);
        lpbResCode.setErrorDesc(errorDesc);
        sp.execute();
    }
}
