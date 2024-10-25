package simulation.simulationSubject.costs;

import java.util.ArrayList;

public class CostArray extends AbstractCost{

    ArrayList<Cost> cost;

    CostArray(String name, CostType type, ArrayList<Cost> cost) {
        super(name, type);
        this.cost = cost;
    }

    public ArrayList<Cost> getCost() {
        return cost;
    }
}
