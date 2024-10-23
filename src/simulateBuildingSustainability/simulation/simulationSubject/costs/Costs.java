package simulateBuildingSustainability.simulation.simulationSubject.costs;

import java.util.HashMap;
import java.util.Set;

public interface Costs<T> {
    public void addCost(String key, T cost);
    T getCost(String key);
    HashMap<String, T> getCosts();
    void addCosts(Costs<T> costs);
    void subtractCosts(Costs<T> costs);
    public Set<String> getKeySet();
}
