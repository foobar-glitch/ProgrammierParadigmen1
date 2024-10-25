package simulation;

import simulation.simulationSubject.SimulationSubject;

abstract class AbstractSimulation {
    private final SimulationSubject subject;

    AbstractSimulation(SimulationSubject subject) {
        this.subject = subject;
    }

    abstract public SimulationResult runSimulation();

    protected SimulationSubject getSubject() {return subject;}
}
