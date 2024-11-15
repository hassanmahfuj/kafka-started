package dev.mahfuj.kafka_started.controller;

import dev.mahfuj.kafka_started.domain.AsyncProcess;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping(value = "/task-executor", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> task(@RequestBody AsyncProcess process) {
        process.convertToTasks().forEach(task -> {
            kafkaTemplate.send("task-executor", process.getId().toString(), task);
        });
        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "/task-result", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> result(@RequestBody AsyncProcess process) {
        process.convertToTasks().forEach(task -> {
            kafkaTemplate.send("task-result", process.getId().toString(), task.getName());
        });
        return ResponseEntity.accepted().build();
    }
}
