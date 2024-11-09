package dev.mahfuj.kafka_started.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessIdCounterService {

    private KeyValueStore<Long, Long> processIdCountStore;

    @Autowired
    public ProcessIdCounterService(StreamsBuilder streamsBuilder) {
        StoreBuilder<KeyValueStore<Long, Long>> countStoreBuilder = Stores.keyValueStoreBuilder(
                Stores.inMemoryKeyValueStore("processIdCountStore"), Serdes.Long(), Serdes.Long()
        );
        streamsBuilder.addStateStore(countStoreBuilder);
    }

    public void init(KeyValueStore<Long, Long> processIdCountStore) {
        this.processIdCountStore = processIdCountStore;
    }

    public Long incrementCount(Long processId) {
        Long currentCount = processIdCountStore.get(processId);
        if (currentCount == null) {
            currentCount = 0L;
        }
        currentCount++;
        processIdCountStore.put(processId, currentCount);
        return currentCount;
    }

    public Long getCount(Long processId) {
        Long count = processIdCountStore.get(processId);
        return count != null ? count : 0L;
    }
}
