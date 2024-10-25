package simulation.simulationSubject.costs;

import java.util.HashMap;
import java. util. Set;

public class DefaultCosts implements Costs<Double> {
    private final HashMap<String, Double> costs;

    DefaultCosts(HashMap<String, Double> costs) {
        this.costs = costs;
    }

    DefaultCosts() {
        this.costs = new HashMap<String, Double>();
    }

    public void addCost(String key, Double cost) {
        costs.put(key, cost);
    }

    public Double getCost(String key) {
        return costs.get(key);
    }

    public HashMap<String, Double> getCosts() {
        return costs;
    }

    public void addCosts(Costs<Double> otherCosts) {
        // TODO check for not the same keys
        costs.forEach((k, v) -> otherCosts.getCosts().merge(k, v, Double::sum));
    }

    public void subtractCosts(Costs<Double> otherCosts) {
        // TODO check for not the same keys
        costs.forEach((k, v) -> otherCosts.getCosts().merge(k, -v, Double::sum));
    }

    public Set<String> getKeySet() {
        return costs.keySet();
    }
}
