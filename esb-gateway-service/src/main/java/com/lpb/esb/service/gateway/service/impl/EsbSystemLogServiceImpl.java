package com.lpb.esb.service.gateway.service.impl;

import brave.Tracer;
import com.lpb.esb.service.gateway.model.elasticsearch.EsbSystemLogEntity;
import com.lpb.esb.service.gateway.model.elasticsearch.UrlInfo;
import com.lpb.esb.service.gateway.process.EsbSystemLogProcess;
import com.lpb.esb.service.gateway.repositories.es.EsbSystemLogRepository;
import com.lpb.esb.service.gateway.service.EsbSystemLogService;
import com.lpb.esb.service.gateway.utils.LogicUtils;
import com.lpb.esb.service.gateway.utils.ZuulConstants;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by tudv1 on 2021-08-10
 */
@Service
@Log4j2
public class EsbSystemLogServiceImpl implements EsbSystemLogService {
    @Autowired
    EsbSystemLogRepository esbSystemLogRepository;

    @Autowired
    EsbSystemLogProcess esbSystemLogProcess;
    @Autowired
    LogicUtils logicUtils;

    @Autowired
    Tracer tracer;

    @Override
    public void saveEsbSystemLog(EsbSystemLogEntity esbSystemLogEntity) {

        if (esbSystemLogEntity.getRequestUrl().getFullUrl().contains("actuator") || esbSystemLogEntity.getRequestUrl().getPath().startsWith("/ping")) {
            if (!esbSystemLogEntity.getRequestUrl().getPath().startsWith("/ping")) {
                log.warn("log info, not save to system log");
            }
        } else {
            String appId = System.currentTimeMillis() + "";
            try {
                appId = esbSystemLogProcess.getNextSequenceSystemLog().getData().getData().toString();
            } catch (Exception e) {
                log.error("error: {}", e.getMessage(), e);
            }
            log.info("appId seq: {}", appId);
            esbSystemLogEntity.setAppId(appId);
            esbSystemLogRepository.save(esbSystemLogEntity);
        }
    }

    @Async
    @Override
    public void saveEsbSystemLogAsync(EsbSystemLogEntity esbSystemLogEntity) {
        saveEsbSystemLog(esbSystemLogEntity);
    }


    @Override
    public EsbSystemLogEntity getSystemLogFromRequestContext(RequestContext ctx, HttpServletRequest request, HttpServletResponse response, String responseBody) {
        String requestData = null;
        try {
            requestData = (String) request.getAttribute(ZuulConstants.X_REQUEST_BODY);
        } catch (Exception e) {
            log.error("Error parsing request", e);
        }
        EsbSystemLogEntity esbSystemLogEntity = EsbSystemLogEntity.builder()
            .serviceId((String) ObjectUtils.defaultIfNull(ctx.get("serviceId"), "service not found"))
            .traceId(tracer.currentSpan().context().traceIdString())
            .user((String) request.getAttribute(ZuulConstants.X_USER))
            .clientIp(request.getRemoteAddr())
            .method(request.getMethod())
            .requestUrl((UrlInfo) request.getAttribute(ZuulConstants.X_REQUEST_ORIGINAL_URL))
            .timeRequest(new Date((Long) request.getAttribute(ZuulConstants.X_REQUEST_TIME_HEADER)))
            .requestBody(requestData)
            .timeResponse(new Date(System.currentTimeMillis()))
            .responseStatusCode(response.getStatus())
            .responseBody(responseBody)
            .build();
        esbSystemLogEntity.setTimeExecuteMillis(
            logicUtils.getTimeExecuteRequest(esbSystemLogEntity.getTimeRequest().getTime(), esbSystemLogEntity.getTimeResponse().getTime())
        );
        esbSystemLogEntity.setTimeExecuteSecond(((double) esbSystemLogEntity.getTimeExecuteMillis()) / 1000);
        return esbSystemLogEntity;
    }

    @Override
    public EsbSystemLogEntity getSystemLogFromRequestContext(RequestContext ctx, HttpServletRequest request, HttpServletResponse response) {
        String requestData = null;
        try {
            requestData = (String) request.getAttribute(ZuulConstants.X_REQUEST_BODY);
        } catch (Exception e) {
            log.error("Error parsing request", e);
        }
        EsbSystemLogEntity esbSystemLogEntity = EsbSystemLogEntity.builder()
            .serviceId((String) ObjectUtils.defaultIfNull(ctx.get("serviceId"), "service not found"))
            .traceId(tracer.currentSpan().context().traceIdString())
            .user((String) request.getAttribute(ZuulConstants.X_USER))
            .clientIp(request.getRemoteAddr())
            .method(request.getMethod())
            .requestUrl((UrlInfo) request.getAttribute(ZuulConstants.X_REQUEST_ORIGINAL_URL))
            .timeRequest(new Date((Long) request.getAttribute(ZuulConstants.X_REQUEST_TIME_HEADER)))
            .requestBody(requestData)
            .timeResponse(new Date(System.currentTimeMillis()))
            .responseStatusCode(response.getStatus())
//            .responseBody(responseBody)
            .build();
        esbSystemLogEntity.setTimeExecuteMillis(
            logicUtils.getTimeExecuteRequest(esbSystemLogEntity.getTimeRequest().getTime(), esbSystemLogEntity.getTimeResponse().getTime())
        );
        esbSystemLogEntity.setTimeExecuteSecond(((double) esbSystemLogEntity.getTimeExecuteMillis()) / 1000);
        return esbSystemLogEntity;
    }
}
