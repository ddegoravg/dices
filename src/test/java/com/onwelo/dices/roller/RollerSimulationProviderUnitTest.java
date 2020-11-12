package com.onwelo.dices.roller;

import static com.onwelo.dices.matchers.OrderedCollection.orderedCollection;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import com.onwelo.dices.roller.model.TotalCounter;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class RollerSimulationProviderUnitTest {


    @Test
    void whenRequested_shouldReturnListOfTotals() {


        Integer[] counter = new Integer[] {10};
        RollerSimulationProvider rollerSimulationProvider = new RollerSimulationProvider(
            (rolls, dice) -> {
                return (counter[0]++ % 20) + 3;
            }
        );

        List<TotalCounter> list = rollerSimulationProvider.getTotals();
        assertThat(list, hasSize(20));
        assertThat(list.stream().map(TotalCounter::getTotal).collect(Collectors.toList()), is(orderedCollection()));
        assertThat(list.get(0).getTotal(), equalTo(3));
        assertThat(list.get(0).getCount(), equalTo(10));
        assertThat(list.get(19).getTotal(), equalTo(22));
        assertThat(list.get(0).getCount(), equalTo(10));

    }


}
