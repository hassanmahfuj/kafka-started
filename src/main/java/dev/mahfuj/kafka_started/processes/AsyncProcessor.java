package dev.mahfuj.kafka_started.processes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import dev.mahfuj.kafka_started.processes.domain.RateChange;
import org.springframework.stereotype.Service;

@Service
public class AsyncProcessor {

    private final ObjectMapper objectMapper;

    public AsyncProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void execute(AsyncProcessTask task) {
        try {
            Class<?> clazz = Class.forName(task.getDomainType());
            Object obj = objectMapper.readValue(task.getTask(), clazz);
            execute((RateChange) obj);
        } catch (ClassNotFoundException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(RateChange rateChange) {
        System.out.println("Changing rate: " + rateChange.getAccountNumber());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
