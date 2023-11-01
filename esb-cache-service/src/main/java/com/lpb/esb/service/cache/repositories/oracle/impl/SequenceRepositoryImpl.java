package com.lpb.esb.service.cache.repositories.oracle.impl;

import com.lpb.esb.service.cache.repositories.oracle.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;

/**
 * Created by tudv1 on 2021-08-19
 */
@Repository
public class SequenceRepositoryImpl implements SequenceRepository {
    @Autowired
    EntityManager em;

    private final String QUERY_SEQ_RAW = "SELECT %s.NEXTVAL FROM DUAL";

    @Override
    public BigDecimal getSequenceId(String sequenceName) {
        String query = String.format(QUERY_SEQ_RAW, sequenceName);
        Query q = em.createNativeQuery(query);
        return (java.math.BigDecimal) q.getSingleResult();
    }
}
