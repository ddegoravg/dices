package com.onwelo.dices.rollerv3.api.mappers;

import com.onwelo.dices.roller.model.TotalCounter;
import com.onwelo.dices.rollerv3.api.responses.SimulationResponse;
import com.onwelo.dices.rollerv3.api.responses.SingleTotalResponse;
import java.util.List;
import java.util.stream.Collectors;

public class NewSimulationResponseMapper {

    public static SimulationResponse getSimulationResponse(List<TotalCounter> totals) {
        return SimulationResponse
            .builder()
            .totals(totals.stream().map(NewSimulationResponseMapper::getSingleTotal)
                .collect(Collectors.toList()))
            .build();

    }

    private static SingleTotalResponse getSingleTotal(TotalCounter tc) {
        return SingleTotalResponse.builder()
            .count(tc.getCount())
            .total(tc.getTotal())
            .build();
    }


}
