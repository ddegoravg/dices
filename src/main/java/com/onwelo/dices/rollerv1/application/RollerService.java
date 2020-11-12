package com.onwelo.dices.rollerv1.application;

import com.onwelo.dices.roller.RollerSimulationProvider;
import com.onwelo.dices.roller.model.TotalCounter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RollerService {

    private final RollerSimulationProvider rollerSimulationProvider;

    /**
     *  executes default simulation.
     *
     * @return default simulation parameters result
     */
    public List<TotalCounter> getTotals() {
        return rollerSimulationProvider.getTotals();
    }

    /**
     * provides the parametrized simulation details.
     *
     * @param numberOfRolls  number of rolls in the simulation
     * @param numberOfDice number of dice in the simulation
     * @param numberOfSides number of sides of each dice
     * @return returns the simulation for given parameters
     */

    public List<TotalCounter> getTotals(Integer numberOfRolls, Integer numberOfDice, Integer numberOfSides) {
        return rollerSimulationProvider.getTotals(numberOfRolls, numberOfDice, numberOfSides);
    }
}
