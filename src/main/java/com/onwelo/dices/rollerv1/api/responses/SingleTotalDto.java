package com.onwelo.dices.rollerv1.api.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SingleTotalDto {

    private Integer total;
    private Integer count;
}

