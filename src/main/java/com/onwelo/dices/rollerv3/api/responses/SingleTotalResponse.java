package com.onwelo.dices.rollerv3.api.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SingleTotalResponse {

    private Integer total;
    private Integer count;
}

