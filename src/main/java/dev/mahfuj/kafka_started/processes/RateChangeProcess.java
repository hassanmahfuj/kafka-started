package dev.mahfuj.kafka_started.processes;

import dev.mahfuj.kafka_started.processes.domain.RateChange;
import org.springframework.stereotype.Service;

@Service
public class RateChangeProcess {

    public void execute(RateChange rateChange) {
        System.out.println("Executing " + rateChange.toString());
    }
}
