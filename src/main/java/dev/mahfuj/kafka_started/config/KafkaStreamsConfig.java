package dev.mahfuj.kafka_started.config;

import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import dev.mahfuj.kafka_started.serde.JsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Value(value = "${spring.application.name}")
    private String moduleName;

    @Bean(name = "defaultKafkaStreamsConfig")
    public StreamsConfig kafkaStreamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, moduleName.concat("-streams"));
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, AppSerdes.AsyncProcessTask().getClass().getName());
        return new StreamsConfig(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AsyncProcessTask> asyncProcessTaskListener() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, moduleName);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);


        ConcurrentKafkaListenerContainerFactory<String, AsyncProcessTask> factory = new ConcurrentKafkaListenerContainerFactory<>();

        JsonDeserializer<AsyncProcessTask> asyncProcessTaskDeserializer = new JsonDeserializer<>();
        asyncProcessTaskDeserializer.configure(Collections.singletonMap(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, AsyncProcessTask.class), false);

        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), asyncProcessTaskDeserializer));
        return factory;
    }

}
