package dev.mahfuj.kafka_started.streams;

import dev.mahfuj.kafka_started.serde.AppSerdes;
import dev.mahfuj.kafka_started.serde.GenericSerde;
import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessor {

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
//        GenericSerde<AsyncProcessTask> asyncProcessTaskSerde = new GenericSerde<>(AsyncProcessTask.class);
//        MySerde.AsyncProcessTaskSerde asyncProcessTaskSerde = new MySerde.AsyncProcessTaskSerde();
//        JsonSerde<AsyncProcessTask> serde = new JsonSerde<>();
//        AppSerdes.AsyncProcessTaskSerde serde = ;

//        Map<String, Object> configs = new HashMap<>();
//        configs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, AsyncProcessTask.class);
//        serde.configure(configs, false);

        KStream<String, AsyncProcessTask> stream = streamsBuilder
                .stream("task-executor", Consumed.with(Serdes.String(), AppSerdes.AsyncProcessTask()));

        stream.mapValues(s -> s.setName("121212"))
                .to("task-result", Produced.with(Serdes.String(), AppSerdes.AsyncProcessTask()));
    }
}
