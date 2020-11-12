package com.onwelo.dices.rollerv3.application;

import com.onwelo.dices.roller.RollerSimulationProvider;
import com.onwelo.dices.roller.model.TotalCounter;
import com.onwelo.dices.rollerv3.api.responses.totalnumbers.Counter;
import com.onwelo.dices.rollerv3.api.responses.totalnumbers.DiceCombination;
import com.onwelo.dices.rollerv3.application.repositories.SimulationDetailsRepository;
import com.onwelo.dices.rollerv3.application.repositories.SimulationSetRepository;
import com.onwelo.dices.rollerv3.model.SimulationDetails;
import com.onwelo.dices.rollerv3.model.SimulationSet;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RollerSavingService {

    private final RollerSimulationProvider rollerSimulationProvider;

    private final SimulationSetRepository simulationSetRepository;
    private final SimulationDetailsRepository simulationDetailsRepository;

    private static Collector<SimulationSet, Counter, Counter> toRollingCounter() {
        return Collector.of(() -> new Counter(0, 0), (counter, set) -> counter.addNewSet(set.getNumberOfRolls()), (counter1, counter2) -> {
            counter1.addCounter(counter2);
            return counter1;
        }, Collector.Characteristics.IDENTITY_FINISH);
    }

    /**
     * executes parameterized simulation.
     *
     * @param numberOfRolls - number of rolls to be processed in the simulation
     * @param numberOfDice  - number of dice in the simulation
     * @param numberOfSides - number of sides of each dice in the simulation
     * @return list of totals with the proper counter
     */
    @Transactional
    public List<TotalCounter> getTotals(Integer numberOfRolls, Integer numberOfDice, Integer numberOfSides) {
        List<TotalCounter> simulation = rollerSimulationProvider.getTotals(numberOfRolls, numberOfDice, numberOfSides);

        SimulationSet simulationSet = new SimulationSet(numberOfRolls, numberOfDice, numberOfSides);
        simulation.stream().map(sim -> SimulationDetails.builder().total(sim.getTotal()).count(sim.getCount()).build()).forEach(
            simulationSet::addSimulationDetails);
        simulationSetRepository.save(simulationSet);
        return simulation;
    }

    /**
     * returns the number of simulations and rolls for each dice parameter set.
     *
     * @return map with simulations, the key is the dice parameters set, the value is the total number of rolls and simulations for that set.
     */
    public Map<DiceCombination, Counter> getAllSimulationSets() {

        return simulationSetRepository.findAll().stream()
            .collect(Collectors.groupingBy(g -> DiceCombination.builder().dice(g.getNumberOfDice()).sides(g.getNumberOfSides()).build(),
                toRollingCounter()));

    }

    /**
     * Returns distribution of totals of given dice parameters.
     *
     * @param dice  number of dice for which the distribution is to be returned
     * @param sides number of sides for which the distribution id to be returned
     * @return map with proper distribution - key is the total, value is the percentage of this total
     */
    @Transactional
    public Map<Integer, BigDecimal> getDistribution(Integer dice, Integer sides) {
        Map<Integer, Integer> mapOfTotals = simulationSetRepository.findAllByNumberOfDiceAndNumberOfSides(dice, sides)
            .stream().flatMap(c -> c.getSimulationDetails().stream())
            .collect(Collectors.groupingBy(SimulationDetails::getTotal, Collectors.summingInt(SimulationDetails::getCount)));

        BigDecimal totalCount = new BigDecimal(mapOfTotals.values().stream().mapToInt(Integer::intValue).sum()).divide(new BigDecimal(100));

        return mapOfTotals.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey))
            .collect(Collectors.toMap(Map.Entry::getKey, e -> new BigDecimal(e.getValue()).divide(totalCount, 2, RoundingMode.HALF_UP),
                (value1, value2) -> {
                    throw new RuntimeException(String.format("Duplicate key for values %s and %s", value1, value2));
                },
                TreeMap::new));
    }

}