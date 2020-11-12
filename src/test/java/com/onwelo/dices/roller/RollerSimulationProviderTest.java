package com.onwelo.dices.roller;

import static com.onwelo.dices.matchers.OrderedCollection.orderedCollection;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.onwelo.dices.roller.model.TotalCounter;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class RollerSimulationProviderTest {

    @Autowired
    RollerSimulationProvider rollerSimulationProvider;

    @MockBean
    IDiceRollerProvider rollingProvider;

    private Integer counter;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenDefaultRequest_shouldReturnListOfTotals() {
        when(rollingProvider.roll(any(), any())).thenReturn(5);
        List<TotalCounter> list = rollerSimulationProvider.getTotals();
        assertThat(list, hasSize(1));
        assertThat(list.get(0).getTotal(), equalTo(5));
        assertThat(list.get(0).getCount(), equalTo(200));
    }

    @Test
    void whenRequested_shouldReturnListOfTotals() {
        counter = 10;
        when(rollingProvider.roll(any(), any())).thenAnswer(
            (invocation) -> {
                return (counter++ % 20) + 3;
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