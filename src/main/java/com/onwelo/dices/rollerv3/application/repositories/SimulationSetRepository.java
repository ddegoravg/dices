package com.onwelo.dices.rollerv3.application.repositories;

import com.onwelo.dices.rollerv3.model.SimulationSet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulationSetRepository extends JpaRepository<SimulationSet, Long> {

    List<SimulationSet> findAllByNumberOfDiceAndNumberOfSides(Integer dice, Integer side);

}
