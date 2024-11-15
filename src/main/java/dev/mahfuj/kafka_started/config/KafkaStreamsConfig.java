package dev.mahfuj.kafka_started.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Value(value = "${spring.application.name}")
    private String moduleName;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(APPLICATION_ID_CONFIG, moduleName.concat("-streams"));
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        return new KafkaStreamsConfiguration(props);
    }
}
