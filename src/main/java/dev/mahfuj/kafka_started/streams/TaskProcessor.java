package dev.mahfuj.kafka_started.streams;

import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TaskProcessor {

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
        JsonSerde<AsyncProcessTask> asyncProcessTaskSerde = new JsonSerde<>(AsyncProcessTask.class);

//        Map<String, Object> serdeConfigs = new HashMap<>();
//        serdeConfigs.put("spring.json.add.type.headers", true);
//        serdeConfigs.put("spring.json.type.mapping", "AsyncProcessTask:" + AsyncProcessTask.class.getName());
//        asyncProcessTaskSerde.configure(serdeConfigs, false);

        KStream<String, AsyncProcessTask> stream = streamsBuilder
                .stream("task-executor", Consumed.with(Serdes.String(), asyncProcessTaskSerde));

        stream.mapValues(s -> s.setStatus("Completed"))
//                .map((key, value) -> {
//                    Headers headers = new RecordHeaders();
//                    headers.add("__TypeId__", AsyncProcessTask.class.getName().getBytes(StandardCharsets.UTF_8));
//                    return new KeyValue<>(key, value);
//                })
                .to("task-result", Produced.with(Serdes.String(), asyncProcessTaskSerde));
    }
}
