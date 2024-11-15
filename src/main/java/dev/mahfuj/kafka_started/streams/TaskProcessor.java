package dev.mahfuj.kafka_started.streams;

import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TaskProcessor {

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
        JsonSerde<AsyncProcessTask> asyncProcessTaskSerde = new JsonSerde<>(AsyncProcessTask.class);
        asyncProcessTaskSerde.configure(Collections.singletonMap(JsonDeserializer.TRUSTED_PACKAGES, "*"), false);

        KStream<String, AsyncProcessTask> stream = streamsBuilder
                .stream("task-executor", Consumed.with(Serdes.String(), asyncProcessTaskSerde));

        stream.mapValues(s -> s.setStatus("Completed"))
                .to("task-result", Produced.with(Serdes.String(), asyncProcessTaskSerde));
    }
}
