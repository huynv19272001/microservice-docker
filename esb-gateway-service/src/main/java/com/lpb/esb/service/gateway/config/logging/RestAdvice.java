package com.lpb.esb.service.gateway.config.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.gateway.model.elasticsearch.EsbSystemLogEntity;
import com.lpb.esb.service.gateway.service.EsbSystemLogService;
import com.lpb.esb.service.gateway.utils.ZuulConstants;
import com.netflix.zuul.context.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class RestAdvice implements ResponseBodyAdvice<Object> {

    private final Logger logger = LogManager.getLogger("RequestLog");

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EsbSystemLogService esbSystemLogService;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        String bodyString = null;
        if (body != null) {
            bodyString = mapToString(body);
        }
        EsbSystemLogEntity esbSystemLogEntity = esbSystemLogService.getSystemLogFromRequestContext(RequestContext.getCurrentContext()
            , ((ServletServerHttpRequest) request).getServletRequest()
            , ((ServletServerHttpResponse) response).getServletResponse()
            , bodyString
        );
        response.getHeaders().add(ZuulConstants.X_TRACE_ID_HEADER, esbSystemLogEntity.getTraceId());
        if (!esbSystemLogEntity.getRequestUrl().getPath().startsWith("/ping")) {
            logger.info("Response {} {} : {}", request.getMethod(), esbSystemLogEntity.getRequestUrl().getFullUrl(), body);
        }
        esbSystemLogService.saveEsbSystemLogAsync(esbSystemLogEntity);

        return body;
    }

    private String mapToString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error(e);
            return null;
        }
    }
}
