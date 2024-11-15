package dev.mahfuj.kafka_started.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class TaskListener {

    private final ObjectMapper objectMapper;

    public TaskListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

//    @KafkaListener(topics = "task-executor", groupId = "hum", containerFactory = "kafkaListenerContainerFactory")
//    public void taskExecutorListener(AsyncProcessTask task) {
//        System.out.println(task.toString());
//    }

//    @KafkaListener(topics = "task-result", containerFactory = "kafkaListenerContainerFactory")
//    public void taskResultListener(LinkedHashMap<String, Object> recordValue) {
//        AsyncProcessTask task = objectMapper.convertValue(recordValue, AsyncProcessTask.class);
//        System.out.println(task);
//    }

    @KafkaListener(topics = "task-result", containerFactory = "asyncProcessTaskListener")
    public void taskResultListener(AsyncProcessTask task) {
        System.out.println(task);
    }
}
