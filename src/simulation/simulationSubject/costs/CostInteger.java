package simulation.simulationSubject.costs;

public class CostInteger extends AbstractCost {

    private int cost;

    CostInteger(String name, CostType type, int cost) {
        super(name, type);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
