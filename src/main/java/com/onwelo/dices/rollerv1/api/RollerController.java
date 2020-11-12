package com.onwelo.dices.rollerv1.api;

import com.onwelo.dices.rollerv1.api.mappers.RollingTotalsMapper;
import com.onwelo.dices.rollerv1.api.responses.RollerResponse;
import com.onwelo.dices.rollerv1.application.RollerService;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("assignement1")
@RequiredArgsConstructor
@Validated
@Slf4j
public class RollerController {

    private final RollerService rollerService;

    @GetMapping("/rollingSet")
    public RollerResponse getRollingTotals() {
        log.info("Default simulation requested");
        return RollerResponse.builder()
            .totals(RollingTotalsMapper.getDtos(rollerService.getTotals()))
            .build();

    }

    @GetMapping("/rollingSet/parametrized")
    public RollerResponse getRollingTotals(@RequestParam(value = "rolls", required = true)
                                           @Min(value = 1, message = "rolls need to be at least 1") Integer rolls,
                                           @RequestParam(value = "dice", required = true)
                                           @Min(value = 1, message = "dice need to be at least 1") Integer dice,
                                           @RequestParam(value = "sides", required = true)
                                           @Min(value = 4, message = "sides need to be at least 4") Integer sides) {
        log.info("Simulation for {} roles, {} dices, {} sides requested");
        return RollerResponse.builder()
            .totals(RollingTotalsMapper.getDtos(rollerService.getTotals(rolls, dice, sides)))
            .build();

    }

}
