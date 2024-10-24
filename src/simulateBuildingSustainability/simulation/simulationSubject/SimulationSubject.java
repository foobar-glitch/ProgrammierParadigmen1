package simulateBuildingSustainability.simulation.simulationSubject;

import simulateBuildingSustainability.simulation.simulationSubject.costs.Costs;

public interface SimulationSubject<T1, T2 extends Costs<T1>> {
    // TODO copy subject in order to be able to do multiple simulations

    boolean continueSimulation();
    boolean stopSimulationEarly();

    T2 executeInitialEvents();
    T2 executeFinalEvents();
    T2 executeRandomEvents();
    T2 nextStep();
}
