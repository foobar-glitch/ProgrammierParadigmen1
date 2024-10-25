package simulation.simulationSubject.costs;

public class CostDouble extends AbstractCost {

    private double cost;

    CostDouble(String name, CostType type, double cost) {
        super(name, type);
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
}
