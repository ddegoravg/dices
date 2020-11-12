package com.onwelo.dices.rollerv1.api.responses;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RollerResponse {

    private List<SingleTotalDto> totals;

}
