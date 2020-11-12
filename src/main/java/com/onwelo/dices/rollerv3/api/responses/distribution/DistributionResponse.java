package com.onwelo.dices.rollerv3.api.responses.distribution;

import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DistributionResponse {

    Map<Integer, BigDecimal> distribution;

}
