package simulateBuildingSustainability.simulation;

import simulateBuildingSustainability.simulation.simulationSubject.SimulationSubject;

abstract class AbstractSimulation<T extends SimulationSubject> {
    protected final T subject;

    AbstractSimulation(T subject) {
        this.subject = subject;
    }

    abstract protected SimulationResult runSimulation();
}
