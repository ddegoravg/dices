package com.onwelo.dices.rollerv3.api.responses.totalnumbers;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Counter {

    private Integer simulationCount;
    private Integer rollsCount;

    public Counter(Integer simulationCount, Integer rollsCount) {
        this.simulationCount = simulationCount;
        this.rollsCount = rollsCount;
    }

    public void addNewSet(Integer count) {
        rollsCount += count;
        simulationCount++;
    }

    public void addCounter(Counter counter) {
        rollsCount += counter.getRollsCount();
        simulationCount += counter.getSimulationCount();
    }

    @JsonValue
    public String getCounter() {
        return "simulations:" + simulationCount + ",rolls:" + rollsCount;
    }
}
