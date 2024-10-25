package simulation.simulationSubject.costs;

abstract class AbstractCost implements Cost {
    String name;
    // could also be enum? should be an enum?
    CostType type;

    AbstractCost(String name, CostType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public CostType getType() {
        return type;
    }

}
