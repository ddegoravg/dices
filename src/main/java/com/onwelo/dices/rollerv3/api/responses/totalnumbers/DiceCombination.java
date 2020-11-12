package com.onwelo.dices.rollerv3.api.responses.totalnumbers;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiceCombination {

    private Integer dice;

    private Integer sides;

    @JsonValue
    public String getDiceCombination() {
        return "Dice:" + dice + ", Sides:" + sides;
    }

}
