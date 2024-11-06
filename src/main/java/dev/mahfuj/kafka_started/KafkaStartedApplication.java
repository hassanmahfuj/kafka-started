package dev.mahfuj.kafka_started;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class KafkaStartedApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStartedApplication.class, args);
    }
}
