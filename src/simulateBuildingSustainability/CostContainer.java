package simulateBuildingSustainability;

import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultCosts;

public class CostContainer extends DefaultCosts {

    public CostContainer(double money, double co2, double waste) {
        super();
        super.insertCost("money", money);
        super.insertCost("co2", co2);
        super.insertCost("waste", waste);
    }

    public CostContainer() {
        this(0.0, 0.0, 0.0);
    }

    public double getMoney(){
        return super.getCost("money");
    }

    public double getCo2(){
        return super.getCost("co2");
    }

    public double getWaste(){
        return super.getCost("waste");
    }
}
