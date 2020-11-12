package com.onwelo.dices.roller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DiceRollerProviderTest {

    @ParameterizedTest
    @CsvSource({"1,4,1,4", "1,5,1,5", "3,4,3,12", "15,6, 15,90"})
    public void whenRequestedHugeNumberOfTimes_shouldReturnvaluesBetweenBoundaries(int dice, int sides, int min, int max) {

        DiceRollerProvider diceRollerProvider = new DiceRollerProvider();

        for (int i = 0; i < 10000; i++) {
            assertThat(diceRollerProvider.roll(
                dice, sides), is(both(greaterThan(min - 1)).and(lessThan(max + 1))));
        }

    }


}