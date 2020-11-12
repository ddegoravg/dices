package com.onwelo.dices.rollerv3.application.repositories;

import com.onwelo.dices.rollerv3.model.SimulationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulationDetailsRepository extends JpaRepository<SimulationDetails, Long> {
}
