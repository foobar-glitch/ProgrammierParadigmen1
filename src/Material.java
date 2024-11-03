/**
 * This class defines the material and its properties
 */
public class Material {
    /* Name of the Material*/
    private String name;
    /* Cost of material normalized for a ton */
    private CostContainer acquisitionCost;

    /**
     *
     * @param acquisitionCost cost of the acquisition of the material
     */
    public Material(String name, CostContainer acquisitionCost) {
        this.name = name;
        //double cost, double co2, double deteriorationRate
        this.acquisitionCost = acquisitionCost;
    }

    public Material copy() {
        return new Material(this.name, this.acquisitionCost);
    }

    public CostContainer getCost() {
        return acquisitionCost;
    }

    public String getName() { return name;}

}
