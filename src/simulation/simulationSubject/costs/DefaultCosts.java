package simulation.simulationSubject.costs;

import java.util.HashMap;
import java. util. Set;

// koennte einfach stattdessen von HashMap erben?
public class DefaultCosts implements Costs {
    private HashMap<String, Double> costs;

    DefaultCosts(HashMap<String, Double> costs) {
        this.costs = costs;
    }

    DefaultCosts() {
        this.costs = new HashMap<String, Double>();
    }

    public void addCost(String key, double cost) {
        costs.put(key, cost);
    }

    public double getCost(String key) {
        return costs.get(key);
    }

    public HashMap<String, Double> getCosts() {
        return costs;
    }

    @Override
    public void addCosts(Costs otherCosts) {
        // TODO check for not the same keys
        costs.forEach((k, v) -> otherCosts.getCosts().merge(k, v, Double::sum));
    }

    @Override
    public void subtractCosts(Costs otherCosts) {
        // TODO check for not the same keys
        costs.forEach((k, v) -> otherCosts.getCosts().merge(k, -v, Double::sum));
    }

    public Set<String> getKeySet() {
        return costs.keySet();
    }
}
