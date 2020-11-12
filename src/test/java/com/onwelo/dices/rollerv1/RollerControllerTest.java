package com.onwelo.dices.rollerv1;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.onwelo.dices.roller.RollerSimulationProvider;
import com.onwelo.dices.roller.model.TotalCounter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RollerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RollerSimulationProvider rollerSimulationProvider;

    @Test
    void whenDefaultRequest_shouldReturnProperList() throws Exception {
        when(rollerSimulationProvider.getTotals()).thenReturn(getDefaultRollingSets());
        this.mockMvc.perform(get("/assignement1/rollingSet")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.totals", hasSize(4)))
            .andExpect(jsonPath("totals[0].total", equalTo(3)))
            .andExpect(jsonPath("totals[0].count", equalTo(1)));

    }

    @Test
    void whenProperParametrizedRequest_shouldReturnProperList() throws Exception {
        when(rollerSimulationProvider.getTotals(any(), any(), any())).thenReturn(getDefaultRollingSets());
        this.mockMvc.perform(get("/assignement1/rollingSet/parametrized?rolls=10&dice=1&sides=4")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.totals", hasSize(4)))
            .andExpect(jsonPath("totals[0].total", equalTo(3)))
            .andExpect(jsonPath("totals[0].count", equalTo(1)));

    }

    @Test
    void whenDefaultParametrizableRequest_shouldReturn400() throws Exception {
        this.mockMvc.perform(get("/assignement1/rollingSet/parametrized"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(status().reason(containsString("Required Integer parameter 'rolls' is not present")));


    }

    @Test
    void whenParametrizableRequestWithTooLowSides_shouldReturn400() throws Exception {
        this.mockMvc.perform(get("/assignement1/rollingSet/parametrized?rolls=10&dice=1&sides=2")).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("sides need to be at least 4")));
    }

    @Test
    void whenParametrizableRequestWithTooLowDice_shouldReturn400() throws Exception {
        this.mockMvc.perform(get("/assignement1/rollingSet/parametrized?rolls=10&dice=0&sides=4")).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("dice need to be at least 1")));

    }

    @Test
    void whenParametrizableRequestWithTooLowRolls_shouldReturn400() throws Exception {
        this.mockMvc.perform(get("/assignement1/rollingSet/parametrized?rolls=0&dice=2&sides=4")).andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", containsString("rolls need to be at least 1")));
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

}

