package com.lpb.esb.service.kafka.service;

import org.springframework.stereotype.Service;

/**
 * Created by tudv1 on 2021-07-23
 */
@Service
public interface KafkaProducerService {
    void sendDataToKafkaEsb(String topic, String message);
}
