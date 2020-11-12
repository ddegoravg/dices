package com.onwelo.dices.rollerv3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import com.onwelo.dices.rollerv3.api.mappers.TotalNumbersOfSimulationMapper;
import com.onwelo.dices.rollerv3.api.responses.totalnumbers.Counter;
import com.onwelo.dices.rollerv3.api.responses.totalnumbers.DiceCombination;
import com.onwelo.dices.rollerv3.api.responses.totalnumbers.TotalNumberOfSimulationsResponse;
import com.onwelo.dices.rollerv3.application.RollerSavingService;
import com.onwelo.dices.rollerv3.application.repositories.SimulationSetRepository;
import com.onwelo.dices.rollerv3.model.SimulationSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class RollerSavingServiceTest {

    @Autowired
    RollerSavingService rollerSavingService;

    @MockBean
    SimulationSetRepository simulationSetRepository;

    @Test
    void whenListOfSimulationSetsIsProvided_shouldReturnProperMap() {

        when(simulationSetRepository.findAll()).thenReturn(getSimulationDetails());


        Map<DiceCombination, Counter> input = rollerSavingService.getAllSimulationSets();


        assertThat(input.entrySet(), hasSize(2));
        assertThat(input.get(DiceCombination.builder().dice(5).sides(4).build()).getSimulationCount(), is(3));
        assertThat(input.get(DiceCombination.builder().dice(5).sides(4).build()).getRollsCount(), is(600));
        assertThat(input.get(DiceCombination.builder().dice(7).sides(7).build()).getSimulationCount(), is(4));
        assertThat(input.get(DiceCombination.builder().dice(7).sides(7).build()).getRollsCount(), is(920));


    }


    @Test
    void whenListOfSimulationSetsIsProvided_shouldReturnProperResponse() {

        when(simulationSetRepository.findAll()).thenReturn(getSimulationDetails());

        TotalNumberOfSimulationsResponse response = TotalNumbersOfSimulationMapper.getResponse(
            rollerSavingService.getAllSimulationSets()
        );

        assertThat(response.getSimulations().entrySet(), hasSize(2));
        assertThat(response.getSimulations().get(DiceCombination.builder().dice(5).sides(4).build()).getRollsCount(), is(600));
        assertThat(response.getSimulations().get(DiceCombination.builder().dice(5).sides(4).build()).getSimulationCount(), is(3));
        assertThat(response.getSimulations().get(DiceCombination.builder().dice(7).sides(7).build()).getRollsCount(), is(920));
        assertThat(response.getSimulations().get(DiceCombination.builder().dice(7).sides(7).build()).getSimulationCount(), is(4));


    }

    private List<SimulationSet> getSimulationDetails() {
        return Arrays.asList(
            SimulationSet.builder()
                .numberOfRolls(200)
                .numberOfDice(5)
                .numberOfSides(4)
                .build(),
            SimulationSet.builder()
                .numberOfRolls(100)
                .numberOfDice(5)
                .numberOfSides(4)
                .build(),
            SimulationSet.builder()
                .numberOfRolls(300)
                .numberOfDice(5)
                .numberOfSides(4)
                .build(),
            SimulationSet.builder()
                .numberOfRolls(220)
                .numberOfDice(7)
                .numberOfSides(7)
                .build(),
            SimulationSet.builder()
                .numberOfRolls(210)
                .numberOfDice(7)
                .numberOfSides(7)
                .build(),
            SimulationSet.builder()
                .numberOfRolls(345)
                .numberOfDice(7)
                .numberOfSides(7)
                .build(),
            SimulationSet.builder()
                .numberOfRolls(145)
                .numberOfDice(7)
                .numberOfSides(7)
                .build()

        );
    }


}
