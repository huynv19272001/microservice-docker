package com.lpb.esb.service.cache.repositories.oracle;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by tudv1 on 2021-08-19
 */
@Repository
public interface SequenceRepository {
    BigDecimal getSequenceId(String sequenceName);
}
