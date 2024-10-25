package simulateBuildingSustainability;

import simulateBuildingSustainability.simulation.simulationSubject.costs.Costs;
import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultCosts;




// make test cases class of their own and give them the materials as arguments??

// how to copy buildings? -> method required by  interface? (how does that behave with inheritance?)
public class Building implements simulateBuildingSustainability.simulation.simulationSubject.SimulationSubject {

    private boolean readyToBeDemolished;


    Building() {
        readyToBeDemolished = false;
    }
    public Boolean checkRemainingLifetime(){ return null;}
    public Costs<Double> build(){return new DefaultCosts();}
    public Costs<Double> demolish(){return new DefaultCosts();}
    public  Costs<Double> renovate(double amount){return new DefaultCosts();}
    public Boolean getReadyToBeDemolished(){return readyToBeDemolished;};
    public void setReadyToBeDemolished(Boolean ReadyToBeDemolished){this.readyToBeDemolished = ReadyToBeDemolished;};


}