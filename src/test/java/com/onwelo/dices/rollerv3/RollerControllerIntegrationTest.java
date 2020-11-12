package com.onwelo.dices.rollerv3;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RollerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenDefaulSuite_shouldReturnProperResponses() throws Exception {

        this.mockMvc.perform(post("/assignment3/rollingSet").content(
            "{\"rolls\":300,\"dice\":4,\"sides\":5}"
        ).contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(get("/assignment3/rollingSet").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.simulations", hasEntry("Dice:4, Sides:5", "simulations:1,rolls:300")));


        this.mockMvc.perform(post("/assignment3/rollingSet").content(
            "{\"rolls\":200,\"dice\":4,\"sides\":5}"
        ).contentType(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(get("/assignment3/rollingSet").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.simulations", hasEntry("Dice:4, Sides:5", "simulations:2,rolls:500")));

        this.mockMvc.perform(get("/assignment3/distribution?dice=4&sides=5").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.distribution", hasKey("4")))
            .andExpect(jsonPath("$.distribution", hasKey("5")))
            .andExpect(jsonPath("$.distribution", hasKey("6")))
            .andExpect(jsonPath("$.distribution", hasKey("7")))
            .andExpect(jsonPath("$.distribution", hasKey("8")))
            .andExpect(jsonPath("$.distribution", hasKey("9")))
            .andExpect(jsonPath("$.distribution", hasKey("18")))
            .andExpect(jsonPath("$.distribution", hasKey("19")))
            .andExpect(jsonPath("$.distribution", hasKey("20")));

    }

}
