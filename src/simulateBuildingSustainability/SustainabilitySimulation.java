package simulateBuildingSustainability;

import simulateBuildingSustainability.simulation.DefaultSimulation;
import simulateBuildingSustainability.simulation.simulationSubject.SimulationSubject;
import simulateBuildingSustainability.simulation.simulationSubject.costs.Costs;


// TODO type of simSub???
// generic in Abstract Sim?
public class SustainabilitySimulation extends DefaultSimulation {


    public SustainabilitySimulation(Building building) {
        super(building);
    }

    @Override
    protected boolean continueSimulation() {
        return false;
    }

    @Override
    protected boolean exitSimulationEarly() {
        return ((Building) getSubject()).wasDemolished();
    }

    @Override
    protected Costs<Double> initialCosts() {
        return ((Building) getSubject()).build();
    }

    @Override
    protected Costs<Double> closingCosts() {
        return ((Building) getSubject()).demolish();
    }

    @Override
    protected Costs<Double> executeRandomEvents() {
        return null;
    }

    @Override
    protected Costs<Double> incrementSimulation() {
        return null;
    }
}
