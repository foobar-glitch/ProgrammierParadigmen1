package simulateBuildingSustainability.simulation;

import simulateBuildingSustainability.simulation.simulationSubject.SimulationSubject;

abstract class AbstractSimulation {
    private final SimulationSubject subject;

    AbstractSimulation(SimulationSubject subject) {
        this.subject = subject;
    }

    abstract protected SimulationResult runSimulation();

    protected SimulationSubject getSubject() {return subject;}
}
