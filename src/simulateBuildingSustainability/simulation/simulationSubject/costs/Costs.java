package simulateBuildingSustainability.simulation.simulationSubject.costs;

import java.util.HashMap;
import java.util.Set;

// TODO rename to Container and DefaultContainer?
public interface Costs {
    boolean isEmpty();
    public void insertCost(String key, Double cost);
    Double getCost(String key);
    HashMap<String, Double> getCosts();
    // TODO make costs a record -> return new costs after adding
    // ist das sinnvoll??
    void addCosts(Costs costs);
    void subtractCosts(Costs costs);
    void multiplyCosts(double factor);
    public Set<String> getKeySet();
}
