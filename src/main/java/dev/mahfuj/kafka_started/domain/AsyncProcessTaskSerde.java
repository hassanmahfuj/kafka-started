package dev.mahfuj.kafka_started.domain;

import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class AsyncProcessTaskSerde extends Serdes.WrapperSerde<AsyncProcessTask> {

    public AsyncProcessTaskSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(AsyncProcessTask.class));
    }
}
