package simulateBuildingSustainability.simulation;

import simulateBuildingSustainability.simulation.simulationSubject.costs.Costs;
import simulateBuildingSustainability.simulation.simulationSubject.SimulationSubject;
import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultMeasurements;
import simulateBuildingSustainability.simulation.simulationSubject.costs.Measurements;

public abstract class DefaultSimulation<T1 extends SimulationSubject> extends AbstractSimulation<T1> {

    Measurements measurements;

    protected DefaultSimulation(T1 subject) {
        super(subject);
        this.measurements = new DefaultMeasurements();
    }

    protected abstract boolean continueSimulation();
    protected abstract boolean exitSimulationEarly();

    protected abstract Costs initialCosts();
    protected abstract Costs closingCosts();
    protected abstract Costs executeRandomEvents();
    protected abstract  Costs incrementSimulation();

    @Override
    public DefaultSimulationResult runSimulation() {
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
