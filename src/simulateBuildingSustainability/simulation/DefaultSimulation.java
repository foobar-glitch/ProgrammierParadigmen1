package simulateBuildingSustainability.simulation;

import simulateBuildingSustainability.simulation.simulationSubject.SimulationSubject;
import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultCosts;
import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultMeasurements;
import simulateBuildingSustainability.simulation.simulationSubject.costs.Measurements;

public abstract class DefaultSimulation extends AbstractSimulation<SimulationSubject> {

    Measurements<Double, DefaultCosts> measurements;

    protected DefaultSimulation(SimulationSubject<Double, DefaultCosts> subject) {
        super(subject);
        this.measurements = new DefaultMeasurements();
    }

    @Override
    public SimulationResult runSimulation() {
        measurements.addToTempTracker(getSubject().executeInitialEvents());
        while(getSubject().continueSimulation()) {
            measurements.addToTempTracker(getSubject().executeRandomEvents());
            if (getSubject().stopSimulationEarly()) {
                measurements.ReadTempTrackerToData();
                break;
            }
            measurements.addToTempTracker(getSubject().nextStep());
            measurements.ReadTempTrackerToData();
            measurements.resetTempTracker();
        };
        measurements.addClosingCosts(getSubject().executeFinalEvents());
        return new DefaultSimulationResult(measurements);
    }
}
