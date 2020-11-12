package com.onwelo.dices.roller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * general class collecting number of rolls for a given total.
 */

@Data
@AllArgsConstructor
@Builder
public class TotalCounter {

    private Integer total;
    private Integer count;

}
