package simulateBuildingSustainability.simulation.simulationSubject.costs;

import java.util.HashMap;
import java. util. Set;

public class DefaultCosts implements Costs<Double> {
    private final HashMap<String, Double> costs;

    DefaultCosts(HashMap<String, Double> costs) {
        this.costs = costs;
    }

    public DefaultCosts() {
        this.costs = new HashMap<String, Double>();
    }

    @Override
    public void addCost(String key, Double cost) {
        costs.put(key, cost);
    }

    @Override
    public Double getCost(String key) {
        return costs.get(key);
    }

    @Override
    public HashMap<String, Double> getCosts() {
        return costs;
    }

    @Override
    public void addCosts(Costs<Double> otherCosts) {
        // TODO check for not the same keys
        if (!otherCosts.getKeySet().isEmpty()) {
            costs.forEach((k, v) -> otherCosts.getCosts().merge(k, v, Double::sum));
        }
    }

    @Override
    public void subtractCosts(Costs<Double> otherCosts) {
        // TODO check for not the same keys
        if (!otherCosts.getKeySet().isEmpty()) {
            costs.forEach((k, v) -> otherCosts.getCosts().merge(k, -v, Double::sum));
        }
    }

    @Override
    public Set<String> getKeySet() {
        return costs.keySet();
    }
}
