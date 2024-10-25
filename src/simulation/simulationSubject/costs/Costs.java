package simulation.simulationSubject.costs;

import java.util.HashMap;

public interface Costs {
    public void addCost(String key, double cost);
    double getCost(String key);
    HashMap<String, Double> getCosts();
    void addCosts(Costs costs);
    void subtractCosts(Costs costs);
}
