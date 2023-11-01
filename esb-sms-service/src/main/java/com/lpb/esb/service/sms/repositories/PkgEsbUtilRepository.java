package com.lpb.esb.service.sms.repositories;

import com.lpb.esb.service.common.model.response.LpbResCode;
import org.springframework.stereotype.Repository;

/**
 * Created by tudv1 on 2021-09-13
 */
@Repository
public interface PkgEsbUtilRepository {
    void esbLoadErrorDesc(LpbResCode lpbResCode);
}
