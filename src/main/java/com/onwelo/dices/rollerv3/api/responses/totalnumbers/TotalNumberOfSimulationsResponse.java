package com.onwelo.dices.rollerv3.api.responses.totalnumbers;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalNumberOfSimulationsResponse {

    private Map<DiceCombination, Counter> simulations = new HashMap<>();

}
