package com.lpb.esb.service.gateway.controller;

import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.gateway.model.elasticsearch.EsbSystemLogEntity;
import com.lpb.esb.service.gateway.repositories.es.EsbSystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tudv1 on 2021-07-09
 */
@RestController
public class PingController {
    @Autowired
    EsbSystemLogRepository esbSystemLogRepository;

    @RequestMapping(value = "ping", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel<String>> ping() {
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode("00")
            .errorDesc("Ping success")
            .build();
        ResponseModel<String> responseModel = ResponseModel.<String>builder()
            .data("Ping ok")
            .resCode(lpbResCode)
            .build();
        return ResponseEntity.ok(responseModel);
    }

    @RequestMapping(value = "ping/{traceId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseModel> ping2(@PathVariable(value = "traceId") String traceId) {
        List<EsbSystemLogEntity> list = esbSystemLogRepository.findAllByTraceId(traceId);
        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode("00")
            .errorDesc(traceId)
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .data(list)
            .resCode(lpbResCode)
            .build();
        return ResponseEntity.ok(responseModel);
    }
}
