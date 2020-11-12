package com.onwelo.dices.rollerv3.api.responses;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimulationResponse {

    private List<SingleTotalResponse> totals;

}
