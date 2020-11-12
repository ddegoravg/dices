package com.onwelo.dices.rollerv3.api;

import com.onwelo.dices.rollerv3.api.mappers.DistributionMapper;
import com.onwelo.dices.rollerv3.api.mappers.NewSimulationResponseMapper;
import com.onwelo.dices.rollerv3.api.mappers.TotalNumbersOfSimulationMapper;
import com.onwelo.dices.rollerv3.api.requests.NewSimulationRequest;
import com.onwelo.dices.rollerv3.api.responses.SimulationResponse;
import com.onwelo.dices.rollerv3.api.responses.distribution.DistributionResponse;
import com.onwelo.dices.rollerv3.api.responses.totalnumbers.TotalNumberOfSimulationsResponse;
import com.onwelo.dices.rollerv3.application.RollerSavingService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignment3")
@RequiredArgsConstructor
@Validated
@Slf4j
public class DbRollerController {

    private final RollerSavingService rollerService;


    @PostMapping("/rollingSet")
    public ResponseEntity<SimulationResponse> postSimulationResource(@Valid @RequestBody NewSimulationRequest request) {

        log.info("simulation created for {} roles. {} dice, {} sides", request.getRolls(), request.getDice(), request.getSides());
        return new ResponseEntity<>(
            NewSimulationResponseMapper
                .getSimulationResponse(rollerService
                    .getTotals(request.getRolls(),
                        request.getDice(),
                        request.getSides())), HttpStatus.OK);
    }

    @GetMapping("rollingSet")
    public ResponseEntity<TotalNumberOfSimulationsResponse> getNumberOfSimulationsAndRolls() {
        log.info("Number of simulations requested");
        return new ResponseEntity<>(TotalNumbersOfSimulationMapper.getResponse(rollerService.getAllSimulationSets()), HttpStatus.OK);
    }

    @GetMapping("distribution")
    public ResponseEntity<DistributionResponse> getDistribution(@RequestParam(value = "dice", required = true) Integer dice,
                                                                @RequestParam(value = "sides", required = true) Integer sides) {
        log.info("distribution for {} dices, {} sides requested", dice, sides);

        return new ResponseEntity<>(DistributionMapper.getResponse(rollerService.getDistribution(dice, sides)), HttpStatus.OK);
    }


}
