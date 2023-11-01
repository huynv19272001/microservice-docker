package com.lpb.esb.service.kafka.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.kafka.service.KafkaProducerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Created by tudv1 on 2021-07-23
 */
@Service
@Log4j2
public class KafkaProducerServiceImpl implements KafkaProducerService {
    @Autowired
    @Qualifier("objectMapperCustom")
    ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, String> kafkaEsbTemplate;

    @Override
    public void sendDataToKafkaEsb(String topic, String message) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        log.info("start send to kafka ESB with topic [{}] and message [{}]", topic, message);
        ListenableFuture<SendResult<String, String>> future = this.kafkaEsbTemplate.send(topic, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                try {
                    log.info("Sent message: " + message + " with info: " + objectMapper.writeValueAsString(result));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message : " + message, ex);
            }
        });
    }
}
