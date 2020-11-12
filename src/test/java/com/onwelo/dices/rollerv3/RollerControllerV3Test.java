package com.onwelo.dices.rollerv3;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasEntry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.onwelo.dices.roller.RollerSimulationProvider;
import com.onwelo.dices.roller.model.TotalCounter;
import com.onwelo.dices.rollerv3.application.repositories.SimulationSetRepository;
import com.onwelo.dices.rollerv3.model.SimulationDetails;
import com.onwelo.dices.rollerv3.model.SimulationSet;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RollerControllerV3Test {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RollerSimulationProvider rollerSimulationProvider;

    @MockBean
    private SimulationSetRepository simulationSetRepository;

    @Test
    void whenDefaultRequest_shouldReturnBadRequest() throws Exception {

        this.mockMvc.perform(post("/assignment3/rollingSet")).andDo(print())
            .andExpect(status().isBadRequest());

    }

    @Test
    void whenProperRequest_shouldReturnProperList() throws Exception {
        when(rollerSimulationProvider.getTotals()).thenReturn(getDefaultRollingSets());
        this.mockMvc.perform(post("/assignment3/rollingSet").content(
            "{\"rolls\":3,\"dice\":4,\"sides\":5}"
        ).contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk());
    }


    @Test
    void whenDefaultParametrizableRequest_shouldReturn400() throws Exception {
        this.mockMvc.perform(post("/assignment3/rollingSet"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("Required request body is missing")));


    }

    @Test
    void whenParametrizableRequestWithTooLowSides_shouldReturn400() throws Exception {
        this.mockMvc.perform(post("/assignment3/rollingSet").content(
            "{\"rolls\":3,\"dice\":4,\"sides\":2}"
        ).contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("sides need to be at least 4")));
    }

    @Test
    void whenParametrizableRequestWithTooLowDice_shouldReturn400() throws Exception {
        this.mockMvc.perform(post("/assignment3/rollingSet").content(
            "{\"rolls\":3,\"dice\":0,\"sides\":5}"
        ).contentType(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("dice need to be at least 1")));

    }

    @Test
    void whenParametrizableRequestWithTooLowRolls_shouldReturn400() throws Exception {
        this.mockMvc.perform(post("/assignment3/rollingSet").content(
            "{\"rolls\":0,\"dice\":4,\"sides\":5}"
        ).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("rolls need to be at least 1")));
    }


    @Test
    void whenProperRequestForTotalCounts_shouldReturnProperMap() throws Exception {
        when(simulationSetRepository.findAll()).thenReturn(getSimulationSets());
        this.mockMvc.perform(get("/assignment3/rollingSet").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.simulations", hasEntry("Dice:3, Sides:4", "simulations:3,rolls:450")));
    }


    @Test
    void whenProperRequestForDistribution_shouldReturnProperMap() throws Exception {
        when(simulationSetRepository.findAllByNumberOfDiceAndNumberOfSides(any(), any())).thenReturn(getSimulationSetsWithDdetails());
        this.mockMvc.perform(get("/assignment3/distribution?dice=3&sides=4").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.distribution", hasEntry("3", 41.38)))
            .andExpect(jsonPath("$.distribution", hasEntry("4", 44.83)))
            .andExpect(jsonPath("$.distribution", hasEntry("5", 13.79)))
        ;
    }


    private List<TotalCounter> getDefaultRollingSets() {
        return Arrays.asList(
            TotalCounter.builder()
                .total(3)
                .count(1)
                .build(),
            TotalCounter.builder()
                .total(4)
                .count(5)
                .build(),
            TotalCounter.builder()
                .total(5)
                .count(2)
                .build(),
            TotalCounter.builder()
                .total(6)
                .count(7)
                .build()
        );
    }

    private List<SimulationSet> getSimulationSets() {
        return Arrays.asList(
            SimulationSet.builder()
                .numberOfDice(3)
                .numberOfRolls(150)
                .numberOfSides(4)
                .build(),
            SimulationSet.builder()
                .numberOfDice(3)
                .numberOfRolls(150)
                .numberOfSides(4)
                .build(),
            SimulationSet.builder()
                .numberOfDice(3)
                .numberOfRolls(150)
                .numberOfSides(4)
                .build()
        );
    }

    private List<SimulationSet> getSimulationSetsWithDdetails() {
        return Arrays.asList(
            SimulationSet.builder()
                .numberOfDice(3)
                .numberOfRolls(150)
                .numberOfSides(4)
                .simulationDetails(
                    Arrays.asList(
                        SimulationDetails.builder()
                            .total(3)
                            .count(2)
                            .build(),
                        SimulationDetails.builder()
                            .total(4)
                            .count(3)
                            .build(),
                        SimulationDetails.builder()
                            .total(5)
                            .count(1)
                            .build()
                    )
                )
                .build(),
            SimulationSet.builder()
                .numberOfDice(3)
                .numberOfRolls(150)
                .numberOfSides(4)
                .simulationDetails(
                    Arrays.asList(
                        SimulationDetails.builder()
                            .total(3)
                            .count(3)
                            .build(),
                        SimulationDetails.builder()
                            .total(4)
                            .count(2)
                            .build(),
                        SimulationDetails.builder()
                            .total(5)
                            .count(1)
                            .build()
                    )
                )
                .build(),
            SimulationSet.builder()
                .numberOfDice(3)
                .numberOfRolls(150)
                .numberOfSides(4)
                .simulationDetails(
                    Arrays.asList(
                        SimulationDetails.builder()
                            .total(3)
                            .count(7)
                            .build(),
                        SimulationDetails.builder()
                            .total(4)
                            .count(8)
                            .build(),
                        SimulationDetails.builder()
                            .total(5)
                            .count(2)
                            .build()
                    )
                )
                .build()
        );
    }


}

