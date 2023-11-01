package com.lpb.esb.service.gateway.config.logging.zuul;

import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.lpb.esb.service.gateway.model.elasticsearch.EsbSystemLogEntity;
import com.lpb.esb.service.gateway.model.elasticsearch.UrlInfo;
import com.lpb.esb.service.gateway.service.EsbSystemLogService;
import com.lpb.esb.service.gateway.utils.ZuulConstants;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.apache.RibbonApacheHttpResponse;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Created by tudv1 on 2021-08-02
 */
@Component
@Log4j2
public class LogResponseZuulFilter extends ZuulFilter {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private Tracer tracer;

    @Autowired
    EsbSystemLogService esbSystemLogService;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        // Add more header to response
        try {
            ctx.getResponse().addHeader(ZuulConstants.X_TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        RibbonApacheHttpResponse ribbonResponse = null;
        try {
            ribbonResponse = (RibbonApacheHttpResponse) ctx.get("ribbonResponse");
        } catch (Exception e) {
            e.printStackTrace();
        }

        EsbSystemLogEntity esbSystemLogEntity = esbSystemLogService.getSystemLogFromRequestContext(ctx
            , ctx.getRequest()
            , ctx.getResponse()
        );
        if (ribbonResponse != null) {
            URI uri = ribbonResponse.getRequestedURI();
            UrlInfo ribbonProxyInfo = UrlInfo.builder()
                .scheme(ribbonResponse.getRequestedURI().getScheme())
                .host(uri.getHost())
                .port(uri.getPort())
                .path(StringUtils.isEmpty(uri.getQuery()) ? uri.getPath() : uri.getPath() + "?" + uri.getQuery())
                .fullUrl(uri.toString())
                .build();
            esbSystemLogEntity.setForwardRequestInfo(ribbonProxyInfo);
        }
        Long requestTime = esbSystemLogEntity.getTimeRequest().getTime();

        String responseData = "file";
        Map<String, String> mapZuulResponseHeader = ctx.getZuulResponseHeaders().stream().collect(Collectors.toMap(Pair::first, Pair::second, (s, a) -> s + ", " + a));
        mapZuulResponseHeader.forEach((k, v) -> {
            log.info("Zuul Response header:  {} : {} ", k, v);
        });
        if (mapZuulResponseHeader.containsKey(HttpHeaders.CONTENT_DISPOSITION)) {
//        if (esbSystemLogEntity.getRequestUrl().getPath().contains("upload") || esbSystemLogEntity.getRequestUrl().getPath().contains("download")) {
            esbSystemLogEntity.setResponseBody(responseData);
        } else {
            try (final InputStream responseDataStream = ctx.getResponseDataStream()) {
                responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
            } catch (Exception e) {
                log.error("error: {}", e.getMessage(), e);
                throw new ZuulException(e, INTERNAL_SERVER_ERROR.value(), e.getMessage());
            }
            esbSystemLogEntity.setResponseBody(responseData);
            ctx.setResponseBody(esbSystemLogEntity.getResponseBody());

        }


        if (!esbSystemLogEntity.getRequestUrl().getPath().startsWith("/ping")) {
            log.info("Response: [{}] [{}] [{}] {} {}"
                , ((double) System.currentTimeMillis() - requestTime.doubleValue()) / 1000 + "s"
                , esbSystemLogEntity.getServiceId()
                , esbSystemLogEntity.getResponseStatusCode()
                , esbSystemLogEntity.getForwardRequestInfo().getFullUrl()
                , esbSystemLogEntity.getResponseBody()
            );
        }
        esbSystemLogService.saveEsbSystemLogAsync(esbSystemLogEntity);
        return null;
    }
}
