package com.onwelo.dices.rollerv3.api.mappers;

import com.onwelo.dices.rollerv3.api.responses.distribution.DistributionResponse;
import java.math.BigDecimal;
import java.util.Map;

public class DistributionMapper {

    public static DistributionResponse getResponse(Map<Integer, BigDecimal> input) {
        return DistributionResponse.builder()
            .distribution(input)
            .build();
    }

}
