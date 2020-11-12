package com.onwelo.dices.roller;

import org.springframework.stereotype.Component;

@Component
public class DiceRollerProvider implements IDiceRollerProvider {

    /**
     * simulates single roll.
     *
     * @param numberOfDice number of dice in single rolling procedure
     * @param numberOfSides number of sides of each dice
     * @return single rolling:)
     */
    public Integer roll(Integer numberOfDice, Integer numberOfSides) {

        return Math.toIntExact(Math.round(Math.random() * (numberOfDice * (numberOfSides - 1))) + numberOfDice);

    }


}
