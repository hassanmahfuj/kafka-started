package dev.mahfuj.kafka_started.stream;

import dev.mahfuj.kafka_started.config.GenericSerde;
import dev.mahfuj.kafka_started.domain.AsyncProcessTask;
import dev.mahfuj.kafka_started.processes.AsyncProcessor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.processor.api.FixedKeyProcessor;
import org.apache.kafka.streams.processor.api.FixedKeyProcessorContext;
import org.apache.kafka.streams.processor.api.FixedKeyRecord;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        StoreBuilder<KeyValueStore<Long, Long>> countStoreBuilder = Stores.keyValueStoreBuilder(
                Stores.inMemoryKeyValueStore("processIdCountStore2"), Serdes.Long(), Serdes.Long()
        );
        streamsBuilder.addStateStore(countStoreBuilder);

        KStream<String, AsyncProcessTask> stream = streamsBuilder
                .stream("task-executor", Consumed.with(Serdes.String(), asyncProcessTaskSerde));

        stream.processValues(() -> new FixedKeyProcessor<String, AsyncProcessTask, AsyncProcessTask>() {
            private KeyValueStore<Long, Long> countStore;

            @Override
            public void init(FixedKeyProcessorContext<String, AsyncProcessTask> context) {
                countStore = context.getStateStore("processIdCountStore2");
            }

            @Override
            public void process(FixedKeyRecord<String, AsyncProcessTask> record) {
                Long processId = Long.parseLong(record.key());
                Long currentCount = countStore.get(processId);
                if (currentCount == null) {
                    currentCount = 0L;
                }
                currentCount++;
                countStore.put(processId, currentCount);

                record.value().setCount(currentCount);
                asyncProcessor.execute(record.value());
            }

            @Override
            public void close() {
            }
        }, "processIdCountStore2");

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
