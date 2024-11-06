package dev.mahfuj.kafka_started.controller;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaStreams kafkaStreams;

    public HelloController(KafkaTemplate<String, String> kafkaTemplate, KafkaStreams kafkaStreams) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaStreams = kafkaStreams;
    }

    @GetMapping("/task")
    public ResponseEntity<?> hello(@RequestParam String message) {
        kafkaTemplate.send("task", message);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/task-completed")
    public ResponseEntity<?> taskCompleted(@RequestParam String message) {
        kafkaTemplate.send("task-completed", message);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/all")
    public Map<String, String> getAllTasks() {
        Map<String, String> taskData = new HashMap<>();

        // Query the state store
//        ReadOnlyKeyValueStore<String, String> keyValueStore = kafkaStreams.store(
//                "task-store", QueryableStoreTypes.keyValueStore());

//        keyValueStore.all().forEachRemaining(entry -> taskData.put(entry.key, entry.value));

        return taskData;
    }
}
