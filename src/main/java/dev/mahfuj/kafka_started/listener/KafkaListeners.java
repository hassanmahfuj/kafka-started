package dev.mahfuj.kafka_started.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "task", groupId = "hum")
    void taskListener(String data) {
        System.out.println(data);
    }

    @KafkaListener(topics = "task-completed", groupId = "hum")
    void taskCompletedListener(String data) {
        System.out.println(data);
    }

}
