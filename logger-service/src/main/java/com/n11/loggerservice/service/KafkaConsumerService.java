package com.n11.loggerservice.service;

import com.n11.loggerservice.entity.ErrorLog;
import com.n11.loggerservice.repository.ErrorLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
/**
 * @author BeytullahBilek
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final ErrorLogRepository errorLogRepository;

    @KafkaListener(topics = "errorLog", groupId = "log-consumer-group")
    public void consumes(String message){

        ErrorLog errorLog = new ErrorLog();
        errorLog.setDate(LocalDateTime.now());
        errorLog.setMessage(message);
        errorLog.setDescription("Kafka logs");

        errorLogRepository.save(errorLog);
    }
    @KafkaListener(topics = "ReviewControllerLog", groupId = "log-consumer-group")
    public void consumeInfosReviewController(String message){

        ErrorLog errorLog = new ErrorLog();
        errorLog.setDate(LocalDateTime.now());
        errorLog.setMessage(message);
        errorLog.setDescription("ReviewController logs");

        errorLogRepository.save(errorLog);
    }
    @KafkaListener(topics = "AddressControllerLog", groupId = "log-consumer-group")
    public void consumeInfosAddressController(String message){
        ErrorLog errorLog = new ErrorLog();
        errorLog.setDate(LocalDateTime.now());
        errorLog.setMessage(message);
        errorLog.setDescription("AddressController logs");

        errorLogRepository.save(errorLog);
    }
    @KafkaListener(topics = "UserControllerLog", groupId = "log-consumer-group")
    public void consumeInfosUserController(String message){
        ErrorLog errorLog = new ErrorLog();
        errorLog.setDate(LocalDateTime.now());
        errorLog.setMessage(message);
        errorLog.setDescription("UserController logs");

        errorLogRepository.save(errorLog);
    }
    @KafkaListener(topics = "RestaurantControllerLog", groupId = "log-consumer-group")
    public void consumeInfosRestaurantController(String message){
        ErrorLog errorLog = new ErrorLog();
        errorLog.setDate(LocalDateTime.now());
        errorLog.setMessage(message);
        errorLog.setDescription("RestaurantController logs");

        errorLogRepository.save(errorLog);
    }
}
