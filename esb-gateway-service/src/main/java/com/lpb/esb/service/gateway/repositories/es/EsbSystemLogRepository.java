package com.lpb.esb.service.gateway.repositories.es;

import com.lpb.esb.service.gateway.model.elasticsearch.EsbSystemLogEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tudv1 on 2021-08-10
 */
@Repository
public interface EsbSystemLogRepository extends ElasticsearchRepository<EsbSystemLogEntity, String> {
    List<EsbSystemLogEntity> findAllByTraceId(String traceId);
}
