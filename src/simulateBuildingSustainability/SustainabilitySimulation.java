package simulateBuildingSustainability;

import simulateBuildingSustainability.simulation.DefaultSimulation;
import simulateBuildingSustainability.simulation.simulationSubject.costs.Costs;
import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultCosts;

// uses logic in parent class to run simulation
// this class only implements the specific actions that are done in each corresponding step of the simulation
public class SustainabilitySimulation extends DefaultSimulation<Building> {


    public SustainabilitySimulation(Building building) {
        super(building);
    }

    @Override
    protected boolean continueSimulation() {
        return subject.getReadyToBeDemolished();
    }

    @Override
    protected boolean exitSimulationEarly() {
        return !continueSimulation();
    }

    @Override
    protected Costs initialCosts() {
        return subject.build();
    }

    @Override
    protected Costs closingCosts() {
        return subject.demolish();
    }

    @Override
    protected Costs executeRandomEvents() {
        Costs costs = new DefaultCosts();
        for (Apartment apartment: subject.getApartments()) {
            if (Math.random() < 1.0/apartment.getLifetime()) {
                costs.addCosts(apartment.renovate());
            }
        }
        // TODO catastrophes
        return costs;
    }

    @Override
    protected Costs incrementSimulation() {
        // TODO logic for how long the simulation should run, here
        // only access state of building
        // set building ready to demolish here

        return null;
    }
}
