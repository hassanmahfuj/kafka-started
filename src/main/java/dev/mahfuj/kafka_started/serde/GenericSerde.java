package dev.mahfuj.kafka_started.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class GenericSerde<T> implements Serializer<T>, Deserializer<T>, Serde<T> {

    private Class<T> clazz;
    private ObjectMapper objectMapper;

    public GenericSerde(Class<T> clazz) {
        this.clazz = clazz;
        this.objectMapper = new ObjectMapper(); // Jackson ObjectMapper
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No configuration required for the generic implementation
    }

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (IOException e) {
            throw new SerializationException("Error serializing object to JSON", e);
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing object from JSON", e);
        }
    }

    @Override
    public void close() {
        // No resources to clean up
    }

    @Override
    public Serializer<T> serializer() {
        return this;
    }

    @Override
    public Deserializer<T> deserializer() {
        return this;
    }
}

