package simulateBuildingSustainability;

import simulateBuildingSustainability.simulation.DefaultSimulation;
import simulateBuildingSustainability.simulation.simulationSubject.costs.Costs;
import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultCosts;


// TODO type of simSub???
// generic in Abstract Sim?
public class SustainabilitySimulation extends DefaultSimulation<Building> {


    public SustainabilitySimulation(Building building) {
        super(building);
    }

    @Override
    protected boolean continueSimulation() {
        return getSubject().getReadyToBeDemolished();
    }

    @Override
    protected boolean exitSimulationEarly() {
        return !continueSimulation();
    }

    @Override
    protected Costs initialCosts() {
        return getSubject().build();
    }

    @Override
    protected Costs closingCosts() {
        return getSubject().demolish();
    }

    @Override
    protected Costs executeRandomEvents() {
        // TODO placeholder from old code
        // would be prettier if Costs would not be used
        // always call renovate but sometimes with 0?
        // TODO rename?
        double amount = 0;
        double randomVal = Math.random();
        if (randomVal < 0.05) {
            if (randomVal < 0.005) {
                getSubject().setReadyToBeDemolished(true);
            }
            amount = randomVal;
        }
        return getSubject().renovate(amount);
    }

    @Override
    protected Costs incrementSimulation() {
        // TODO logic for how long the simulation should run, here
        // only access state of building
        // set building ready to demolish here

        return null;
    }
}
