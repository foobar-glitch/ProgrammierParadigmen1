import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * This class represents a collection of materials, where each material has a corresponding amount stored.
 * The design uses both procedural and object-oriented approaches for different methods.
 * - Procedural approach: For tasks like reading data from a file or performing calculations that are not tightly bound to the material objects.
 * - Object-oriented approach: For managing and manipulating the material inventory, where each material is treated as an object with its own properties and methods.
 */
public class MaterialBag {
    /* A hashmap storing the material and its corresponding amount in tons. This is a key-value store that allows
     * for quick lookups and updates of material quantities.
     * The design decision to use a HashMap here follows an object-oriented approach, encapsulating the inventory
     * and associating materials with their amounts directly.
     */
    private HashMap<Material, Double> materialInventory;

    /**
     * Default constructor that initializes an empty material inventory.
     * This is an object-oriented approach as it sets up an instance of MaterialBag with its own inventory.
     */
    public MaterialBag() {
        this.materialInventory = new HashMap<>();
    }

    /**
     * Constructor that initializes the material inventory with the specified materials and their corresponding amounts.
     * This approach provides a convenient way to instantiate a MaterialBag with predefined data.
     * It can be seen as a more procedural approach in the context of object initialization, where the logic of
     * adding materials to the inventory is handled within the constructor.
     *
     * @param materials An array of materials to be added to the inventory.
     * @param amounts An array of amounts (in tons) corresponding to each material.
     */
    public MaterialBag(Material[] materials, Double[] amounts) {
        this.materialInventory = new HashMap<>();
        for (int i = 0; i < materials.length; i++) {
            materialInventory.put(materials[i], amounts[i]);
        }
    }

    /**
     * Sets the amount for a specific material in the inventory.
     * This method is an example of an object-oriented approach, as we are encapsulating the material inventory
     * and providing a method to modify it directly via the MaterialBag object.
     *
     * @param material The material whose amount is being set.
     * @param amount The amount of the material in tons.
     */
    public void setMaterial(Material material, double amount) {
        this.materialInventory.put(material, amount);
    }

    /**
     * Subtracts/Adds a specified amount from the current amount of a material in the inventory.
     * Again, this follows the object-oriented principle of manipulating the internal state of the object.
     * The operation of subtracting is done via a method that changes the state of the `materialInventory` directly.
     *
     * @param material The material from which the amount is to be subtracted.
     * @param subtractAmount The amount to subtract in tons.
     */
    public void subtractMaterial(Material material, double subtractAmount) {
        double newAmount = this.materialInventory.get(material) - subtractAmount;
        this.materialInventory.put(material, newAmount);
    }


    public void addMaterial(Material material, double addAmount) {
        double newAmount = this.materialInventory.get(material) + addAmount;
        this.materialInventory.put(material, newAmount);
    }

    /**
     * Creates a new MaterialBag where the amount of each material is scaled by a specified multiplier.
     * This is a blend of object-oriented design (creating and returning a new object) and procedural logic
     * (applying a multiplier to each material's amount).
     * The approach here focuses on returning a modified version of the original object, thus adhering to
     * the object-oriented principle of immutability.
     *
     * @param multiplier The factor by which the amount of each material is multiplied.
     * @return A new MaterialBag with the quantities of all materials multiplied by the given factor.
     */
    public MaterialBag times(double multiplier){
        MaterialBag tmp = new MaterialBag();
        for(Material m : materialInventory.keySet()){
            tmp.setMaterial(
                    m,
                    materialInventory.get(m)*multiplier
            );
        }
        return tmp;
    }

    /**
     * Copies an MaterialBag-Object
     * @return Exact Copy of original Object
     */
    public MaterialBag copy(){
        MaterialBag tmp = new MaterialBag();
        for(Material m : materialInventory.keySet()){
            tmp.setMaterial(
                    m,
                    materialInventory.get(m)
            );
        }
        return tmp;
    }

    /**
     * Creates a deep copy of the current MaterialBag.
     * This is an object-oriented approach because it returns a new MaterialBag instance,
     * which is a copy of the original inventory. The goal here is to avoid direct manipulation of
     * the original object’s state.
     *
     * @return A new MaterialBag that is a copy of the original, with the same material quantities.
     */
    public MaterialBag add(MaterialBag bag){
        MaterialBag tmp = copy();
        for(Material m : bag.materialInventory.keySet()){
            if(tmp.materialInventory.containsKey(m))
                tmp.addMaterial(m, bag.materialInventory.get(m));
            else
                tmp.setMaterial(m, bag.materialInventory.get(m));
        }
        return tmp;
    }


    /**
     * Object-Oriented because it returns a new MaterialBag containing a subset of the original data,
     * but the calculation itself is more procedural.
     * @return New MaterialBag containing all Waste-Materials
     */
    public MaterialBag getWaste(){
        MaterialBag tmp = new MaterialBag();
        for(Material m : materialInventory.keySet()){
            tmp.setMaterial(
                    m,
                    materialInventory.get(m)*m.getCost().getWaste()
            );
        }
        return tmp;
    }

    /**
     * This is an example of a procedural approach where we iterate over the inventory
     * and apply a simple counting operation.
     * @return Number of Materials inside MaterialBag
     */
    public int size(){
        int size = 0;
        for(Material m: materialInventory.keySet()){
            if(materialInventory.get(m) > 0) size++;
        }
        return size;
    }

    /**
     * Calculates the total cost of all materials in the inventory, considering each material's cost,
     * CO2 emissions, and waste, scaled by their respective quantities.
     * This approach is a mix of object-oriented (CostContainer object to represent the total)
     * and procedural (iterating over the inventory and performing the calculations).
     *
     * @return A CostContainer object representing the total cost of all materials in the inventory.
     */
    public CostContainer getTotalCost() {
        CostContainer totalCost = new CostContainer(0f, 0f, 0f);
        for (Material material : materialInventory.keySet()) {
            CostContainer cost = material.getCost();
            cost = cost.multiplyContainer(materialInventory.get(material));
            totalCost = totalCost.addCostContainer(cost);
        }
        return totalCost;
    }

    /**
     * Returns an array of all materials in the inventory.
     * This is a straightforward procedural approach, where we simply collect the materials
     * into an array, which is then returned.
     *
     * @return An array of Material objects contained in the MaterialBag.
     */
    public Material[] materials(){
        Material[] materials = new Material[materialInventory.size()];
        int i=0;
        for(Material material : materialInventory.keySet()){
            materials[i++] = material;
        }
        return materials;
    }

    /**
     *  Reads a MaterialBag from a CSV file. Each line in the CSV should contain the material's amount,
     *  name, cost, CO2 emissions, and waste.
     *  this is a procedural approach because it deals with reading data from an external source and
     *  parsing it into an object.
     *  @param path Path of File
     */
    public static MaterialBag readFromFile(String path){
        MaterialBag materialBag = new MaterialBag();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            for(String line; (line = bufferedReader.readLine()) != null; ) {
                String[] values = line.split(", ");
                if(values.length != 5){
                    throw new UnsupportedEncodingException(path + " in wrong format: " + line);
                }

                double amount = Double.parseDouble(values[0]);
                String materialName = values[1];
                double materialCost = Double.parseDouble(values[2]);
                double materialCo2 = Double.parseDouble(values[3]);
                double materialWaste = Double.parseDouble(values[4]);

                CostContainer cost = new CostContainer(materialCost, materialCo2, materialWaste);
                Material material = new Material(materialName, cost);

                materialBag.setMaterial(material, amount);
            }
        } catch (IOException e) {
            throw new RuntimeException("File: " + path + " does not exist");
        }
        return materialBag;
    }
}
