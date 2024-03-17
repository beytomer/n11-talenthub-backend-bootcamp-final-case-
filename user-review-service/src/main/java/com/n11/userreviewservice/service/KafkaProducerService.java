package com.n11.userreviewservice.service;
/**
 * @author BeytullahBilek
 */
public interface KafkaProducerService {
    void sendMessage(String topic, String message);
}
