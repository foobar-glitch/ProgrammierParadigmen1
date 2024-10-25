package simulation;

import simulation.simulationSubject.costs.Costs;
import simulation.simulationSubject.SimulationSubject;
import simulation.simulationSubject.costs.DefaultMeasurements;
import simulation.simulationSubject.costs.Measurements;

abstract class DefaultSimulation extends AbstractSimulation {

    Measurements measurements;

    DefaultSimulation(SimulationSubject subject) {
        super(subject);
        this.measurements = new DefaultMeasurements();
    }

    abstract boolean continueSimulation();
    abstract boolean exitSimulationEarly();

    abstract Costs initialCosts();
    abstract Costs closingCosts();
    abstract Costs executeRandomEvents();
    abstract  Costs incrementSimulation();

    @Override
    public SimulationResult runSimulation() {
        measurements.addInitialCosts(initialCosts());
        while(continueSimulation()) {
            measurements.resetTempTracker();
            measurements.addToTempTracker(executeRandomEvents());
            if (exitSimulationEarly()) {
                measurements.ReadTempTrackerToData();
                break;
            }
            measurements.addToTempTracker(incrementSimulation());
            measurements.ReadTempTrackerToData();
        };
        measurements.addClosingCosts(closingCosts());
        return new DefaultSimulationResult(measurements);
    }
}
