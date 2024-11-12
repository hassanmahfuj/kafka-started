package dev.mahfuj.kafka_started.listener;

import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

//    @KafkaListener(topics = "task-executor", groupId = "hum", containerFactory = "kafkaListenerContainerFactory")
//    public void taskExecutorListener(AsyncProcessTask task) {
//        System.out.println(task.toString());
//    }

    @KafkaListener(topics = "task-result", groupId = "hum", containerFactory = "kafkaListenerContainerFactory")
    public void taskResultListener(@Payload AsyncProcessTask task) {
        System.out.println(task.toString());
    }
}
