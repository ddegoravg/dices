package com.onwelo.dices.roller;

import com.onwelo.dices.roller.model.TotalCounter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * provides logic for simulating rolls.
 */

@Service
@RequiredArgsConstructor
public class RollerSimulationProvider {

    public static final int DEFAULT_NUMBER_OF_ROLLS = 200;
    public static final int DEFAULT_NUMBER_OF_DICE = 3;
    public static final int DEFAULT_NUMBER_OF_SIDES = 6;
    private final IDiceRollerProvider diceRollerProvider;

    /**
     * default rolling.
     *
     * @return list of totals and their counts
     */
    public List<TotalCounter> getTotals() {
        return getTotals(DEFAULT_NUMBER_OF_ROLLS, DEFAULT_NUMBER_OF_DICE, DEFAULT_NUMBER_OF_SIDES);
    }

    /**
     * provides parametrized simulation.
     *
     * @param numberOfRolls number of rolls for simulation
     * @param numberOfDice number of dice in the simulation
     * @param numberOfSides number of sides of each dice
     * @return list of all totals with the number of rolls
     */

    public List<TotalCounter> getTotals(Integer numberOfRolls, Integer numberOfDice, Integer numberOfSides) {
        return IntStream.range(0, numberOfRolls).boxed().map(p -> diceRollerProvider.roll(numberOfDice, numberOfSides))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .sorted(Comparator.comparing(Map.Entry::getKey))
            .map(s -> new TotalCounter(s.getKey(), Math.toIntExact(s.getValue())))
            .collect(Collectors.toList());

    }

}
