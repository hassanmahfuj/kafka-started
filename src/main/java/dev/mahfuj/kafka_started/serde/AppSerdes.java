package dev.mahfuj.kafka_started.serde;

import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AppSerdes extends Serdes {

    static public final class AsyncProcessTaskSerde extends WrapperSerde<AsyncProcessTask> {
        public AsyncProcessTaskSerde() {
            super(new JsonSerializer<>(), new JsonDeserializer<>());
        }
    }

    static public Serde<AsyncProcessTask> AsyncProcessTask1() {
        AsyncProcessTaskSerde serde = new AsyncProcessTaskSerde();

        Map<String, Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, AsyncProcessTask.class);
        serde.configure(serdeConfigs, false);

        return serde;
    }

    public static Serde<AsyncProcessTask> AsyncProcessTask() {
        JsonSerde<AsyncProcessTask> serde = new JsonSerde<>(AsyncProcessTask.class);
        serde.configure(Collections.singletonMap(org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES, "*"), false);
        return serde;
    }
}
