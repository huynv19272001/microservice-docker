package com.lpb.esb.service.kafka.controller;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.kafka.model.resquest.DataReceiveModel;
import com.lpb.esb.service.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tudv1 on 2021-07-23
 */
@RestController
@RequestMapping(value = "public/kafka", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DataReceiveController {
    @Autowired
    KafkaProducerService kafkaProducerService;

    @RequestMapping(value = "esb", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel<String>> sendMessage(@RequestBody DataReceiveModel body) {
        kafkaProducerService.sendDataToKafkaEsb(body.getTopic(), body.getMessage());

        ResponseModel<String> res = ResponseModel.<String>builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Send data success")
            .data(body.toString())
            .build();

        return ResponseEntity.ok(res);
    }
}
