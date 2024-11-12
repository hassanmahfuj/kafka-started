package dev.mahfuj.kafka_started.config;

import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import dev.mahfuj.kafka_started.domain.AsyncProcessTaskDeserializer;
import dev.mahfuj.kafka_started.domain.AsyncProcessTaskSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class MySerde {

    public static final class AsyncProcessTaskSerde extends WrapperSerde<AsyncProcessTask> {
        public AsyncProcessTaskSerde() {
            super(new AsyncProcessTaskSerializer(), new AsyncProcessTaskDeserializer());
        }
    }

    protected static class WrapperSerde<T> implements Serde<T> {
        private final Serializer<T> serializer;
        private final Deserializer<T> deserializer;

        public WrapperSerde(Serializer<T> serializer, Deserializer<T> deserializer) {
            this.serializer = serializer;
            this.deserializer = deserializer;
        }

        public void configure(Map<String, ?> configs, boolean isKey) {
            this.serializer.configure(configs, isKey);
            this.deserializer.configure(configs, isKey);
        }

        public void close() {
            this.serializer.close();
            this.deserializer.close();
        }

        public Serializer<T> serializer() {
            return this.serializer;
        }

        public Deserializer<T> deserializer() {
            return this.deserializer;
        }
    }
}