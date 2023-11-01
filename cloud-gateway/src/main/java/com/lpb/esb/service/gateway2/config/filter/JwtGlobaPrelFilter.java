package com.lpb.esb.service.gateway2.config.filter;

import brave.Tracer;
import com.lpb.esb.service.gateway2.config.security.JwtConfig;
import com.lpb.esb.service.gateway2.model.elasticsearch.EsbSystemLogEntity;
import com.lpb.esb.service.gateway2.service.EsbSystemLogService;
import com.lpb.esb.service.gateway2.utils.GatewayConstants;
import com.lpb.esb.service.gateway2.utils.LogicUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.PooledDataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;

/**
 * Created by tudv1 on 2021-09-01
 */
@Component
@Log4j2
public class JwtGlobaPrelFilter implements GlobalFilter, Ordered {

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
        ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
        final List<String> apiEndpoints = Arrays.asList("/esb-auth-service", "/ping");

        Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
            .noneMatch(uri -> r.getURI().getPath().contains(uri));

        if (isApiSecured.test(request)) {
            if (!request.getHeaders().containsKey(jwtConfig.getHeader())) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            // Get JWT Token
            final String token = request.getHeaders()
                .getOrEmpty(jwtConfig.getHeader()).get(0)
                .replace(jwtConfig.getPrefix(), "");
            log.info("Token JWT: {}", token);

            try {
                // 4. Validate the token
                Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

                String username = claims.getSubject();
                if (username != null) {
                    @SuppressWarnings("unchecked")
                    List<String> authorities = (List<String>) claims.get("authorities");
                    String fromIp = (String) claims.get("from-ip");
                    String toIp = (String) claims.get("to-ip");
                    String clientIp = request.getRemoteAddress().getHostString();
                    log.info("Client IP: {}", clientIp);
                    long fromIpLong = ipToLong(fromIp);
                    long toIpLong = ipToLong(toIp);
                    long ipClientLong = ipToLong(clientIp);

                    if (ipClientLong >= fromIpLong && ipClientLong <= toIpLong) {
                        log.info("Client IP [{}] match range ip from [{}] to [{}]", clientIp, fromIp, toIp);
                    } else {
                        log.error("Client IP [{}] not match range ip from [{}] to [{}]", clientIp, fromIp, toIp);
//                        throw new Exception("Ip scan not valid");
                    }

                } else {
                    throw new Exception("User not found");
                }
                exchange.getAttributes().put(GatewayConstants.X_USER, username);
            } catch (Exception e) {
                log.error("error when validate token: {}", e.getMessage(), e);
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);

                return response.setComplete();
            }
        }
        var responseMutated = new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> bodyOrigin) {
                exchange.getResponse().getHeaders().add(GatewayConstants.X_TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());
                EsbSystemLogEntity esbSystemLogEntity = (EsbSystemLogEntity) exchange.getAttribute(GatewayConstants.X_ESB_SYSTEM_LOG_ENTITY);
                esbSystemLogEntity.setResponseStatusCode(exchange.getResponse().getRawStatusCode());
                if (LOGGABLE_CONTENT_TYPES.contains(String.valueOf(getHeaders().getContentType()).toLowerCase())) {
                    return join(bodyOrigin).flatMap(db -> {
                        String body = StandardCharsets.UTF_8.decode(db.asByteBuffer()).toString();
                        logResponse(esbSystemLogEntity, body);
                        return getDelegate().writeWith(Mono.just(db));
                    });
                } else {
                    logResponse(esbSystemLogEntity, null);
                    return getDelegate().writeWith(bodyOrigin);
                }
            }
        };

        return chain.filter(exchange
            .mutate()
            .response(responseMutated)
            .build()
        );
    }

    public long ipToLong(String ipString) throws UnknownHostException {
        InetAddress ip = InetAddress.getByName(ipString);
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }

    private void logResponse(EsbSystemLogEntity esbSystemLogEntity, String body) {
        if (esbSystemLogEntity.getRequestUrl().getPath().contains("upload") || esbSystemLogEntity.getRequestUrl().getPath().contains("download")) {
            body = "file";
        }
        esbSystemLogEntity.setTimeResponse(new Date());
        esbSystemLogEntity.setTimeExecuteMillis(logicUtils.getTimeExecuteRequest(esbSystemLogEntity.getTimeRequest().getTime(), esbSystemLogEntity.getTimeResponse().getTime()));
        esbSystemLogEntity.setTimeExecuteSecond(((double) esbSystemLogEntity.getTimeExecuteMillis()) / 1000);
        esbSystemLogEntity.setResponseBody(body);

        log.info("Response: [{}] [{}] [{} {}] body {}"
            , esbSystemLogEntity.getTimeExecuteSecond() + "s"
            , esbSystemLogEntity.getServiceId()
            , esbSystemLogEntity.getResponseStatusCode()
            , esbSystemLogEntity.getForwardRequestInfo().getFullUrl()
            , esbSystemLogEntity.getResponseBody()
        );
        esbSystemLogService.saveEsbSystemLogAsync(esbSystemLogEntity);
    }

    private Mono<? extends DataBuffer> join(Publisher<? extends DataBuffer> dataBuffers) {
        Assert.notNull(dataBuffers, "'dataBuffers' must not be null");
        return Flux.from(dataBuffers)
            .collectList()
            .filter((list) -> !list.isEmpty())
            .map((list) -> list.get(0).factory().join(list))
            .doOnDiscard(PooledDataBuffer.class, DataBufferUtils::release);
    }

    private static final Set<String> LOGGABLE_CONTENT_TYPES = new HashSet<>(Arrays.asList(
        MediaType.APPLICATION_JSON_VALUE.toLowerCase(),
        MediaType.APPLICATION_JSON_UTF8_VALUE.toLowerCase(),
        MediaType.TEXT_PLAIN_VALUE,
        MediaType.TEXT_XML_VALUE
    ));

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
