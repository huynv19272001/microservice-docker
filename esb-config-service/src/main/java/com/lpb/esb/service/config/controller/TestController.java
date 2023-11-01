package com.lpb.esb.service.config.controller;

import com.lpb.esb.service.common.model.response.SettleAccountInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tudv1 on 2021-11-17
 */
@RestController
@RequestMapping(value = "test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Log4j2
public class TestController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplate restTemplateLB;

    @RequestMapping(value = "header", method = RequestMethod.POST)
    public ResponseEntity test(@RequestBody String e, HttpServletRequest request) throws Exception {
        log.info("body: " + e);
        Map<String, String> header = getAllHeader(request);
        header.forEach((k, v) -> log.info("header: {}\t{}", k, v));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "partner", method = RequestMethod.GET)
    public ResponseEntity testpartner(@RequestParam(value = "service_id") String serviceId
    ) {
        List<SettleAccountInfo> data = ConnectInfoService.getPartnerInfo(restTemplateLB, serviceId, "PA2201_1", "PA2201_0");
        return ResponseEntity.ok().body(data);
    }


    private Map<String, String> getAllHeader(HttpServletRequest request) {
        Map<String, String> mapHeader = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            mapHeader.put(name, request.getHeader(name));
        }
        return mapHeader;

    }
}
