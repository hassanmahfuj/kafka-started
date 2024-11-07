package dev.mahfuj.kafka_started.stream;

import dev.mahfuj.kafka_started.config.GenericSerde;
import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessor {

    private KTable<String, String> taskTable;

//    private final StreamsBuilderFactoryBean factoryBean;
//
//    public TaskProcessor(StreamsBuilderFactoryBean factoryBean) {
//        this.factoryBean = factoryBean;
//    }

//    @Bean
//    public KStream<String, String> kTable(StreamsBuilder streamsBuilder) {

//        GenericSerde<AsyncProcessTask> asyncProcessTaskSerde = new GenericSerde<>(AsyncProcessTask.class);
//        KStream<String, AsyncProcessTask> stream = streamsBuilder.stream("task-executor",
//                Consumed.with(Serdes.String(), asyncProcessTaskSerde));

//        KStream<String, String> stream = streamsBuilder.stream("task-executor");

//        stream.foreach((key, task) -> {
////            Long currentCount = getCountFromStateStore("tasks-store", key);
////            task.setCount(currentCount != null ? currentCount : 0L);
//            task.setStatus("COMPLETED");
//        });

//        stream.to("task-result");
//        return stream;
//    }

    @Bean
    public KTable<String, String> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream("task-executor",
                Consumed.with(Serdes.String(), Serdes.String()));

        KGroupedStream<String, String> groupedStream = stream
                .mapValues(task -> task + "-hum")
                .groupByKey();

        taskTable = groupedStream.reduce(
                (oldValue, newValue) -> newValue,
                Materialized.<String, String, KeyValueStore<Bytes, byte[]>>as("task-store")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.String()));
        return taskTable;
    }

//    private Long getCountFromStateStore(String storeName, String key) {
//        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
//        if (kafkaStreams == null) {
//            return null;
//        }
//
//        ReadOnlyKeyValueStore<String, Long> keyValueStore = kafkaStreams.store(
//                StoreQueryParameters.fromNameAndType(storeName, QueryableStoreTypes.keyValueStore())
//        );
//
//        return keyValueStore.get(key);
//    }
}
