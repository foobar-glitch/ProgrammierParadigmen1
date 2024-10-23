package simulateBuildingSustainability.simulation;

import simulateBuildingSustainability.simulation.simulationSubject.SimulationSubject;

abstract class AbstractSimulation<T extends SimulationSubject> {
    private final T subject;

    AbstractSimulation(T subject) {
        this.subject = subject;
    }

    abstract protected SimulationResult runSimulation();

    protected T getSubject() {return subject;}
}
