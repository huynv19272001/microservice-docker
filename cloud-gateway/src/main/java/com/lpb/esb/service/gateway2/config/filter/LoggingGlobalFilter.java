package com.lpb.esb.service.gateway2.config.filter;

import brave.Tracer;
import com.lpb.esb.service.gateway2.config.security.JwtConfig;
import com.lpb.esb.service.gateway2.model.elasticsearch.EsbSystemLogEntity;
import com.lpb.esb.service.gateway2.service.EsbSystemLogService;
import com.lpb.esb.service.gateway2.utils.GatewayConstants;
import com.lpb.esb.service.gateway2.utils.LogicUtils;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

;

/**
 * Created by tudv1 on 2021-08-30
 */
@Component
@Log4j2
public class LoggingGlobalFilter implements GlobalFilter {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    Tracer tracer;
    @Autowired
    JwtConfig jwtConfig;
    @Autowired
    EsbSystemLogService esbSystemLogService;
    @Autowired
    LogicUtils logicUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        EsbSystemLogEntity esbSystemLogEntity = logRequestIncoming(exchange);

        var requestMutated = new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public Flux<DataBuffer> getBody() {
                getHeaders().toSingleValueMap().forEach((k, v) -> {
                    log.info("Header: {} : {}", k, v);
                });
                exchange.getAttributes().put(GatewayConstants.X_ESB_SYSTEM_LOG_ENTITY, esbSystemLogEntity);
                if (LOGGABLE_CONTENT_TYPES.contains(String.valueOf(getHeaders().getContentType()).toLowerCase())) {
                    return super.getBody().map(ds -> {
                        String body = StandardCharsets.UTF_8.decode(ds.asByteBuffer()).toString();
                        esbSystemLogEntity.setRequestBody(body);
                        log.info("Request body: {}", body);
                        return ds;
                    });
                } else {
                    return super.getBody();
                }
            }
        };


        return chain.filter(exchange
            .mutate()
            .request(requestMutated)
            .build()
        );
    }

    private EsbSystemLogEntity logRequestIncoming(ServerWebExchange exchange) {
        EsbSystemLogEntity esbSystemLogEntity = esbSystemLogService.getSystemLogFromRequestContext(exchange);
        log.info("Incoming request from IP: {} [{} {}] is routed to id: {}, uri: {}"
            , esbSystemLogEntity.getClientIp()
            , esbSystemLogEntity.getMethod()
            , esbSystemLogEntity.getRequestUrl().getFullUrl()
            , esbSystemLogEntity.getServiceId()
            , esbSystemLogEntity.getForwardRequestInfo().getFullUrl()
        );

        return esbSystemLogEntity;
    }


    private static final Set<String> LOGGABLE_CONTENT_TYPES = new HashSet<>(Arrays.asList(
        MediaType.APPLICATION_JSON_VALUE.toLowerCase(),
        MediaType.APPLICATION_JSON_UTF8_VALUE.toLowerCase(),
        MediaType.TEXT_PLAIN_VALUE,
        MediaType.TEXT_XML_VALUE
    ));

//    @Override
//    public int getOrder() {
//        return Ordered.HIGHEST_PRECEDENCE;
//    }
}
