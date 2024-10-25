package simulation.simulationSubject.costs;

import java.util.HashMap;

// koennte einfach stattdessen von HashMap erben?
public class DefaultCosts implements Costs {
    private String[] keys;
    private HashMap<String, AbstractCost> costs;

    DefaultCosts(String[] keys) {
        this.keys = keys;
        costs = new HashMap<String, AbstractCost>();
    }

    Cost getCost(String key) {
        return costs.get(key);
    }
}
