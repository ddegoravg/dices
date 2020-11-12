package com.onwelo.dices.rollerv3.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "simulation_set")
@Builder
public class SimulationSet {

    @OneToMany(mappedBy = "simulationSet",
        cascade = CascadeType.ALL)
    List<SimulationDetails> simulationDetails;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number_of_rolls")
    private Integer numberOfRolls;
    @Column(name = "number_of_dice")
    private Integer numberOfDice;
    @Column(name = "number_of_sides")
    private Integer numberOfSides;

    public SimulationSet(Integer numberOfRolls, Integer numberOfDice, Integer numberOfSides) {
        this.numberOfRolls = numberOfRolls;
        this.numberOfDice = numberOfDice;
        this.numberOfSides = numberOfSides;
        this.simulationDetails = new ArrayList<>();
    }


    public void addSimulationDetails(SimulationDetails sd) {
        simulationDetails.add(sd);
        sd.setSimulationSet(this);
    }

}
