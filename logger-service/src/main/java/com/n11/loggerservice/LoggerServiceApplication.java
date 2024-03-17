package com.n11.loggerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class LoggerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoggerServiceApplication.class, args);
	}

}
