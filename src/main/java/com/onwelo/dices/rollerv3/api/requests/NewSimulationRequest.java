package com.onwelo.dices.rollerv3.api.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewSimulationRequest {

    @Min(value = 1, message = "dice need to be at least 1")
    @NotNull
    Integer dice;

    @Min(value = 1, message = "rolls need to be at least 1")
    @NotNull
    Integer rolls;

    @Min(value = 4, message = "sides need to be at least 4")
    @NotNull
    Integer sides;

}
