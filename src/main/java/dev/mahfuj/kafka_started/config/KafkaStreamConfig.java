package dev.mahfuj.kafka_started.config;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, "streams-app");
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KafkaStreams kafkaStreams(KafkaStreamsConfiguration streamsConfiguration) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        // Define the Kafka Streams topology here
        KStream<String, String> stream = streamsBuilder.stream("task", Consumed.with(Serdes.String(), Serdes.String()));

        // Process the stream
        stream.mapValues(task -> task + "-hum").to("task-completed", Produced.with(Serdes.String(), Serdes.String()));

        // Create KafkaStreams instance
        return new KafkaStreams(streamsBuilder.build(), streamsConfiguration.asProperties());
    }

}
