package com.lpb.esb.service.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.lpb.esb.service.common.model.request.CacheEntity;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tudv1 on 2022-04-06
 */
@Log4j2
public class CacheUtils {
    public static final String URL_PUT_CACHE = "http://esb-cache-service/cache/put";
    public static final String URL_GET_CACHE = "http://esb-cache-service/cache/get";

    public static boolean putDataToCache(RestTemplate restTemplate, String module, String key, String value, Long ttl) {
        if (ttl == null) {
            ttl = 3600l;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);


        CacheEntity cacheEntity = CacheEntity.builder()
            .key(key)
            .module(module)
            .value(value)
            .ttl(ttl)
            .build();
        try {
            HttpEntity<String> entity = new HttpEntity<String>(objectMapper.writeValueAsString(cacheEntity), headers);
            ResponseEntity<String> res = restTemplate.postForEntity(URL_PUT_CACHE, entity, String.class);
            return true;
        } catch (Exception e) {
            log.error("error: {}", e.getMessage(), e);
        }

        return false;

    }

    public static String getCacheValue(RestTemplate restTemplate, String module, String key) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);


        CacheEntity cacheEntity = CacheEntity.builder()
            .key(key)
            .module(module)
            .build();
        try {
            ResponseEntity<String> res = RequestUtils.executePostReq(restTemplate, URL_GET_CACHE, objectMapper.writeValueAsString(cacheEntity));
            if (HttpStatus.OK.equals(res.getStatusCode())) {
                String body = res.getBody();
                JSONObject jsonObject = new JSONObject(body);
                String value = jsonObject.getString("data");
                return value;
            }
        } catch (Exception e) {
            log.error("error: {}", e.getMessage(), e);
        }

        return null;

    }
}
