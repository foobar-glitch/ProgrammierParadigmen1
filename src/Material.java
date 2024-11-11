/**
 * This class defines the material and its properties
 */
public class Material {
    /* Name of the Material*/
    private final String name;
    /* Cost of material normalized for a ton */
    private final CostContainer acquisitionCost;

    /**
     * @param name (!=null) Name of Material
     * @param acquisitionCost (!=null) cost of the acquisition of the material
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
