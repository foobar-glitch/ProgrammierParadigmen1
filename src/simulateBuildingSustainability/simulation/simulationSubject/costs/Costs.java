package simulateBuildingSustainability.simulation.simulationSubject.costs;

import java.util.HashMap;
import java.util.Set;

public interface Costs {
    public void addCost(String key, Double cost);
    Double getCost(String key);
    HashMap<String, Double> getCosts();
    void addCosts(Costs costs);
    void subtractCosts(Costs costs);
    public Set<String> getKeySet();
}
