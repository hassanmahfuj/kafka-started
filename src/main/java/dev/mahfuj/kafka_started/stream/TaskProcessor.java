package dev.mahfuj.kafka_started.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessor {

    private KTable<String, String> taskTable;

    @Bean
    public KTable<String, String> kStream(StreamsBuilder streamsBuilder) {
        // Create a stream from the "task" topic
        KStream<String, String> stream = streamsBuilder.stream("task",
                Consumed.with(Serdes.String(), Serdes.String()));

        // Process the stream by appending "-hum" and group by key
        KGroupedStream<String, String> groupedStream = stream
                .mapValues(task -> task + "-hum")
                .groupByKey();

        // Aggregate the grouped stream to create a KTable
        taskTable = groupedStream.reduce(
                (oldValue, newValue) -> newValue, // Replace old value with new one
                Materialized.<String, String, KeyValueStore<Bytes, byte[]>>as("task-store")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.String()));

        return taskTable;
    }

    public KTable<String, String> getTaskTable() {
        return taskTable;
    }
}
