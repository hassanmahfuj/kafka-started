package dev.mahfuj.kafka_started.controller;

import dev.mahfuj.kafka_started.domain.AsyncProcess;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

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
            task.setTotal((long) process.getTasks().size());
            kafkaTemplate.send("task-executor", process.getId().toString(), task);
        });
        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "/task-result", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> result(@RequestBody AsyncProcess process) {
        process.convertToTasks().forEach(task -> {
            task.setTotal((long) process.getTasks().size());
            kafkaTemplate.send("task-result1", process.getId().toString(), task);
        });
        return ResponseEntity.accepted().build();
    }
}
