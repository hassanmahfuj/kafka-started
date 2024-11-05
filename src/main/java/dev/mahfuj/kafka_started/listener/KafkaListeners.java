package dev.mahfuj.kafka_started.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "hello", groupId = "hum")
    void listener(String data) {
        System.out.println(data);
    }

}
