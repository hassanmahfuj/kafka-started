package dev.mahfuj.kafka_started.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public HelloController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(@RequestParam String message) {
        kafkaTemplate.send("hello", message);
        return ResponseEntity.accepted().build();
    }
}
