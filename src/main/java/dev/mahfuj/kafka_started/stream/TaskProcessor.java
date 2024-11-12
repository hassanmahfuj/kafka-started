package dev.mahfuj.kafka_started.stream;

import dev.mahfuj.kafka_started.config.GenericSerde;
import dev.mahfuj.kafka_started.config.MySerde;
import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import dev.mahfuj.kafka_started.processes.AsyncProcessor;
import dev.mahfuj.kafka_started.serde.AppSerdes;
import dev.mahfuj.kafka_started.serde.JsonDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static dev.mahfuj.kafka_started.serde.JsonDeserializer.KEY_CLASS_NAME_CONFIG;

@Component
public class TaskProcessor {

    private final AsyncProcessor asyncProcessor;
    //    private final ProcessIdCounterService processIdCounterService;
    private final ExecutorService executorService;

    @Autowired
    public TaskProcessor(
            AsyncProcessor asyncProcessor,
//            ProcessIdCounterService processIdCounterService,
            ExecutorService executorService) {
        this.asyncProcessor = asyncProcessor;
//        this.processIdCounterService = processIdCounterService;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
        GenericSerde<AsyncProcessTask> asyncProcessTaskSerde = new GenericSerde<>(AsyncProcessTask.class);
//        MySerde.AsyncProcessTaskSerde asyncProcessTaskSerde = new MySerde.AsyncProcessTaskSerde();
//        JsonSerde<AsyncProcessTask> serde = new JsonSerde<>();
//        AppSerdes.AsyncProcessTaskSerde serde = new AppSerdes.AsyncProcessTaskSerde();

//        Map<String, Object> configs = new HashMap<>();
//        configs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, AsyncProcessTask.class);
//        serde.configure(configs, false);

        KStream<String, AsyncProcessTask> stream = streamsBuilder
                .stream("task-executor", Consumed.with(Serdes.String(), asyncProcessTaskSerde));

        stream.foreach((s, task) -> {
            System.out.println(task);
        });

        stream.to("task-result", Produced.with(Serdes.String(), asyncProcessTaskSerde));
    }

//    @Autowired
//    public void buildPipeline(StreamsBuilder streamsBuilder) {
//        GenericSerde<AsyncProcessTask> asyncProcessTaskSerde = new GenericSerde<>(AsyncProcessTask.class);
//
//        StoreBuilder<KeyValueStore<Long, Long>> countStoreBuilder = Stores.keyValueStoreBuilder(
//                Stores.inMemoryKeyValueStore("processIdCountStore2"), Serdes.Long(), Serdes.Long()
//        );
//        streamsBuilder.addStateStore(countStoreBuilder);
//
//        KStream<String, AsyncProcessTask> stream = streamsBuilder
//                .stream("task-executor", Consumed.with(Serdes.String(), asyncProcessTaskSerde));
//
//        stream.processValues(() -> new FixedKeyProcessor<String, AsyncProcessTask, AsyncProcessTask>() {
//            private KeyValueStore<Long, Long> countStore;
//
//            @Override
//            public void init(FixedKeyProcessorContext<String, AsyncProcessTask> context) {
//                countStore = context.getStateStore("processIdCountStore2");
//            }
//
//            @Override
//            public void process(FixedKeyRecord<String, AsyncProcessTask> record) {
//                Long processId = Long.parseLong(record.key());
//                Long currentCount = countStore.get(processId);
//                if (currentCount == null) {
//                    currentCount = 0L;
//                }
//                currentCount++;
//                countStore.put(processId, currentCount);
//
//                record.value().setCount(currentCount);
//                asyncProcessor.execute(record.value());
//            }
//
//            @Override
//            public void close() {
//            }
//        }, "processIdCountStore2");
//
//        stream.to("task-result", Produced.with(Serdes.String(), asyncProcessTaskSerde));
//    }


//    @Autowired
//    public void buildPipeline(StreamsBuilder streamsBuilder) {
//        GenericSerde<AsyncProcessTask> asyncProcessTaskSerde = new GenericSerde<>(AsyncProcessTask.class);
//
//        StoreBuilder<KeyValueStore<Long, Long>> countStoreBuilder = Stores.keyValueStoreBuilder(
//                Stores.inMemoryKeyValueStore("processIdCountStore2"), Serdes.Long(), Serdes.Long()
//        );
//        streamsBuilder.addStateStore(countStoreBuilder);
//
//        KStream<String, AsyncProcessTask> stream = streamsBuilder
//                .stream("task-executor", Consumed.with(Serdes.String(), asyncProcessTaskSerde));
//
//        stream.transformValues(() -> new ValueTransformerWithKey<String, AsyncProcessTask, AsyncProcessTask>() {
//            private KeyValueStore<Long, Long> countStore;
//
//            @Override
//            public void init(ProcessorContext context) {
//                countStore = context.getStateStore("processIdCountStore2");
//            }
//
//            @Override
//            public AsyncProcessTask transform(String key, AsyncProcessTask task) {
//                Long processId = Long.parseLong(key);
//                Long currentCount = countStore.get(processId);
//                if (currentCount == null) {
//                    currentCount = 0L;
//                }
//                currentCount++;
//                countStore.put(processId, currentCount);
//
//                task.setCount(currentCount);
//                return task;
//            }
//
//            @Override
//            public void close() {
//            }
//        }, "processIdCountStore2");
//
//        stream.to("task-result", Produced.with(Serdes.String(), asyncProcessTaskSerde));
//    }


//    @Autowired
//    public void buildPipeline(StreamsBuilder streamsBuilder) {
//        GenericSerde<AsyncProcessTask> asyncProcessTaskSerde = new GenericSerde<>(AsyncProcessTask.class);
//
//        KStream<String, AsyncProcessTask> messageStream = streamsBuilder
//                .stream("task-executor", Consumed.with(Serdes.String(), asyncProcessTaskSerde));
//
//        KTable<String, Long> processIdCountTable = messageStream
//                .groupBy((key, task) -> task.getProcessId().toString(), Grouped.with(Serdes.String(), asyncProcessTaskSerde))
//                .count(Materialized.as("processIdCountTable"));
//
//        KStream<String, AsyncProcessTask> enrichedStream = messageStream
//                .leftJoin(
//                        processIdCountTable,
//                        (task, count) -> {
//                            task.setCount(count != null ? count : 0L);
//                            return task;
//                        },
//                        Joined.with(Serdes.String(), asyncProcessTaskSerde, Serdes.Long())
//                );
//
//        enrichedStream.to("task-result", Produced.with(Serdes.String(), asyncProcessTaskSerde));
//    }
}
