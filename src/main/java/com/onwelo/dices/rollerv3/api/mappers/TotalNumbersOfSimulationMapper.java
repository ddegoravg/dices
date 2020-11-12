package com.onwelo.dices.rollerv3.api.mappers;

import com.onwelo.dices.rollerv3.api.responses.totalnumbers.Counter;
import com.onwelo.dices.rollerv3.api.responses.totalnumbers.DiceCombination;
import com.onwelo.dices.rollerv3.api.responses.totalnumbers.TotalNumberOfSimulationsResponse;
import java.util.Map;

public class TotalNumbersOfSimulationMapper {

    public static TotalNumberOfSimulationsResponse getResponse(Map<DiceCombination, Counter> input) {
        return TotalNumberOfSimulationsResponse.builder()
            .simulations(input)
            .build();

    }


}
