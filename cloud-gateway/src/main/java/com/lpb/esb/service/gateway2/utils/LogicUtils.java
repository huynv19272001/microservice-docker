package com.lpb.esb.service.gateway2.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by tudv1 on 2021-08-11
 */
@Component
public class LogicUtils {
    public String getFullUrl(HttpServletRequest request) {
        String queryParam = request.getQueryString();
        String url = request.getRequestURL().toString();
        if (StringUtils.isNotEmpty(queryParam)) {
            url = url + "?" + queryParam;
        }
        return url;
    }

    public String getFullPath(HttpServletRequest request) {
        String queryParam = request.getQueryString();
        String path = request.getServletPath();
        if (StringUtils.isNotEmpty(queryParam)) {
            path = path + "?" + queryParam;
        }
        return path;
    }

    public String getFullPath(URI uri) {
        String queryParam = uri.getQuery();
        String path = uri.getPath();
        if (StringUtils.isNotEmpty(queryParam)) {
            path = path + "?" + queryParam;
        }
        return path;
    }

    public long getTimeExecuteRequest(Long startTime, Long endTime) {
        return (endTime.longValue() - startTime.longValue());
    }

    public String getIpAddrFromUrl(String url){
        try {
            URL url1 = new URL(url);
            return url1.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
