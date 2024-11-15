package dev.mahfuj.kafka_started.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TaskListener {

    private final ObjectMapper objectMapper;

    public TaskListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "task-result", containerFactory = "kafkaListenerContainerFactory")
    public void taskResultListener(ConsumerRecord<String, Object> record) {
        AsyncProcessTask task = record.value() instanceof AsyncProcessTask
                ? (AsyncProcessTask) record.value()
                : objectMapper.convertValue(record.value(), AsyncProcessTask.class);

        if (task != null) {
            System.out.println(task);
        }
    }
}
