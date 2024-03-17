package com.n11.userreviewservice.service.impl;

import com.n11.userreviewservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
/**
 * @author BeytullahBilek
 */
@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<String,String>kafkaTemplate;
    @Override
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic,message);
    }
}
