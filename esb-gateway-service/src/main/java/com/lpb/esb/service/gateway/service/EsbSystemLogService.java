package com.lpb.esb.service.gateway.service;

import com.lpb.esb.service.gateway.model.elasticsearch.EsbSystemLogEntity;
import com.netflix.zuul.context.RequestContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tudv1 on 2021-08-10
 */
@Service
public interface EsbSystemLogService {
    public void saveEsbSystemLog(EsbSystemLogEntity esbSystemLogEntity);

    @Async
    public void saveEsbSystemLogAsync(EsbSystemLogEntity esbSystemLogEntity);

    public EsbSystemLogEntity getSystemLogFromRequestContext(RequestContext ctx, HttpServletRequest request, HttpServletResponse response, String body);

    EsbSystemLogEntity getSystemLogFromRequestContext(RequestContext ctx, HttpServletRequest request, HttpServletResponse response);
}
