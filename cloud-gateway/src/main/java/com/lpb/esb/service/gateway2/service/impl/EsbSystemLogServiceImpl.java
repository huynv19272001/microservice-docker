package com.lpb.esb.service.gateway2.service.impl;

import brave.Tracer;
import com.lpb.esb.service.gateway2.model.elasticsearch.EsbSystemLogEntity;
import com.lpb.esb.service.gateway2.model.elasticsearch.UrlInfo;
import com.lpb.esb.service.gateway2.process.EsbSystemLogProcess;
import com.lpb.esb.service.gateway2.repositories.es.EsbSystemLogRepository;
import com.lpb.esb.service.gateway2.service.EsbSystemLogService;
import com.lpb.esb.service.gateway2.utils.GatewayConstants;
import com.lpb.esb.service.gateway2.utils.LogicUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

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
    public EsbSystemLogEntity getSystemLogFromRequestContext(ServerWebExchange exchange) {
        Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
        URI originalUri = (uris.isEmpty()) ? null : uris.iterator().next();
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        URI routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);

        UrlInfo original = UrlInfo.builder()
            .scheme(originalUri.getScheme())
            .host(originalUri.getHost())
            .path(logicUtils.getFullPath(originalUri))
            .port(originalUri.getPort())
            .fullUrl(originalUri.toString())
            .build();

        UrlInfo forward = UrlInfo.builder()
            .scheme(routeUri.getScheme())
            .host(routeUri.getHost())
            .path(logicUtils.getFullPath(routeUri))
            .port(routeUri.getPort())
            .fullUrl(routeUri.toString())
            .build();

        EsbSystemLogEntity esbSystemLogEntity = EsbSystemLogEntity.builder()
            .serviceId(route.getId())
            .traceId(tracer.currentSpan().context().traceIdString())
            .user(exchange.getAttribute(GatewayConstants.X_USER))
            .clientIp(exchange.getRequest().getRemoteAddress().getHostString())
            .method(exchange.getRequest().getMethod().toString())
            .requestUrl(original)
            .forwardRequestInfo(forward)
            .timeRequest(new Date())
            .build();
        return esbSystemLogEntity;
    }
}
