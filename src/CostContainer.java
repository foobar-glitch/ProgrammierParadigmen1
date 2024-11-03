public class CostContainer {
    /**
     * This is a container for all the costs both financial
     * and environmental.
     * */
    /* Costs of material*/
    private double cost;
    /* Usage of co2*/
    private double co2;
    /* Amount of waste created */
    private double waste;

    public CostContainer(double cost, double co2, double waste) {
        this.cost = cost;
        this.co2 = co2;
        this.waste = waste;
    }

    public CostContainer(){
        this.cost = 0;
        this.co2 = 0;
        this.waste = 0;
    }

    /**
     * This adds another CostContainer on the current one
     * @param other The other CostContainer that it is summing with
     */
    public CostContainer addCostContainer(CostContainer other){
        CostContainer tmp = new CostContainer(cost, co2, waste);

        tmp.cost += other.getCost();
        tmp.co2 += other.getCo2();
        tmp.waste += other.getWaste();
        return tmp;
    }

    public CostContainer subtractCostContainer(CostContainer other){
        CostContainer tmp = new CostContainer(cost, co2, waste);

        tmp.cost -= other.getCost();
        tmp.co2 -= other.getCo2();
        tmp.waste -= other.getWaste();
        return tmp;

    }

    /**
     * Multiplying all costs by the amount of material
     * @param amount of the material
     */
    public CostContainer multiplyContainer(double amount){
        CostContainer tmp = new CostContainer(cost, co2, waste);

        tmp.cost *= amount;
        tmp.co2 *= amount;
        tmp.waste *= amount;
        return tmp;

    }

    public double getCost(){
        return cost;
    }

    public double getCo2(){
        return co2;
    }

    public double getWaste(){
        return waste;
    }
}
