//package com.lpb.esb.service.gateway.config.logging.zuul;
//
//import com.google.common.io.CharStreams;
//import com.lpb.esb.service.gateway.config.es.EsProperties;
//import com.lpb.esb.service.gateway.model.elasticsearch.EsbSystemLogEntity;
//import com.lpb.esb.service.gateway.service.EsbSystemLogService;
//import com.lpb.esb.service.gateway.utils.ZuulConstants;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import com.netflix.zuul.http.HttpServletRequestWrapper;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * Created by tudv1 on 2021-08-02
// */
//@Component
//@Log4j2
//public class LogRequestZuulFilter extends ZuulFilter {
//    @Override
//    public String filterType() {
//        return FilterConstants.PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return 2;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Autowired
//    EsbSystemLogService esbSystemLogService;
//
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequestWrapper request = new HttpServletRequestWrapper(ctx.getRequest());
//        String requestData = null;
//        try {
//            if (request.getContentLength() > 0) {
//                requestData = CharStreams.toString(request.getReader());
//            }
//        } catch (Exception e) {
//            log.error("Error parsing request", e);
//        }
//        ctx.addZuulRequestHeader(ZuulConstants.X_REQUEST_TIME_HEADER, System.currentTimeMillis() + "");
//        log.info("Request: [Client IP: {}] [{}] {}\tbody: {}"
//            , request.getRemoteAddr()
//            , request.getMethod()
//            , request.getRequestURL()
//            , requestData);
//        return null;
//    }
//
//}
