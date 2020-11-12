package com.onwelo.dices.rollerv1.api.mappers;

import com.onwelo.dices.roller.model.TotalCounter;
import com.onwelo.dices.rollerv1.api.responses.SingleTotalDto;
import java.util.List;
import java.util.stream.Collectors;

public class RollingTotalsMapper {

    public static List<SingleTotalDto> getDtos(List<TotalCounter> input) {
        return input.stream().map(RollingTotalsMapper::getDto).collect(Collectors.toList());
    }

    private static SingleTotalDto getDto(TotalCounter singleCounter) {
        return SingleTotalDto.builder().total(singleCounter.getTotal())
            .count(singleCounter.getCount())
            .build();
    }


}
