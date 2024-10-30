package simulateBuildingSustainability.simulation.simulationSubject.costs;

import java.util.HashMap;
import java. util. Set;

public class DefaultCosts implements Costs {
    private final HashMap<String, Double> costs;

    DefaultCosts(HashMap<String, Double> costs) {
        this.costs = costs;
    }

    public DefaultCosts() {
        this.costs = new HashMap<String, Double>();
    }

    @Override
    public boolean isEmpty() {
        return costs.isEmpty();
    }

    @Override
    public void insertCost(String key, Double cost) {
        costs.put(key, cost);
    }

    @Override
    protected Double getCost(String key) {
        return costs.get(key);
    }

    @Override
    public HashMap<String, Double> getCosts() {
        return costs;
    }

    @Override
    public void addCosts(Costs otherCosts) {
        // TODO check for not the same keys
        if (!otherCosts.isEmpty()) {
            costs.forEach((k, v) -> otherCosts.getCosts().merge(k, v, Double::sum));
        }
    }

    @Override
    public void subtractCosts(Costs otherCosts) {
        // TODO check for not the same keys
        if (!otherCosts.isEmpty()) {
            costs.forEach((k, v) -> otherCosts.getCosts().merge(k, -v, Double::sum));
        }
    }

    @Override
    public void multiplyCosts(double factor) {
        // TODO make prettier
        costs.forEach((k, v) -> costs.put(k, v * factor));
    }

    @Override
    public Set<String> getKeySet() {
        return costs.keySet();
    }
}
