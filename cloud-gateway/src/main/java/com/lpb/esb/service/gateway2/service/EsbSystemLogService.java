package com.lpb.esb.service.gateway2.service;

import com.lpb.esb.service.gateway2.model.elasticsearch.EsbSystemLogEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

/**
 * Created by tudv1 on 2021-08-10
 */
@Service
public interface EsbSystemLogService {
    public void saveEsbSystemLog(EsbSystemLogEntity esbSystemLogEntity);

    @Async
    public void saveEsbSystemLogAsync(EsbSystemLogEntity esbSystemLogEntity);

    public EsbSystemLogEntity getSystemLogFromRequestContext(ServerWebExchange exchange);
}
