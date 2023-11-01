package com.lpb.esb.service.auth.service.impl;

import com.lpb.esb.service.auth.service.TokenCacheService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tudv1 on 2021-11-19
 */
@Service
@Log4j2
public class TokenCacheServiceImpl implements TokenCacheService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${api.cache.push}")
    String apiPushCache;

    @Override
    @Async
    public void pushTokenCache(String bodyReq) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            HttpEntity<String> request = new HttpEntity<String>(bodyReq, httpHeaders);

            ResponseEntity<String> response = restTemplate.postForEntity(apiPushCache, request, String.class);
            String body = response.getBody();
            log.info("res: {}", body);
        } catch (RestClientException e) {
            log.error("error when execute request: {}", e.getMessage(), e);
        }
    }
}
