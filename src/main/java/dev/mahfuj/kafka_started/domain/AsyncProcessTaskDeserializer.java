package dev.mahfuj.kafka_started.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class AsyncProcessTaskDeserializer implements Deserializer<AsyncProcessTask> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public AsyncProcessTask deserialize(String s, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, AsyncProcessTask.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing object from JSON", e);
        }
    }

    @Override
    public void close() {

    }
}
