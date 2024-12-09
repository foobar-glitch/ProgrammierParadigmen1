/**
 * This class defines the material and its properties
 *
 * Nominal abstraction:
 * The class Material represents a real-world concept—a material with a name and its acquisition cost.
 * Instead of working directly with raw strings and numbers to represent a material, the concept is abstracted
 * into a single, cohesive class.
 * By giving the abstraction a name (Material), the class provides a clear and logical representation.
 *
 * Data Abstraction
 * The Material class encapsulates its key data:
 * name: the material's name.
 * acquisitionCost: the cost of acquiring the material, represented using a CostContainer object.
 * The fields name and acquisitionCost are private, hiding the internal state from external access.
 *
 *
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
