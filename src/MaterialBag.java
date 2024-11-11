import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
     *
     * Constructor that initializes the material inventory with the specified materials and their corresponding amounts.
     * This approach provides a convenient way to instantiate a MaterialBag with predefined data.
     * It can be seen as a more procedural approach in the context of object initialization, where the logic of
     * adding materials to the inventory is handled within the constructor.
     *
     * GOOD: The constructor that accepts arrays of materials and amounts is a good example of high cohesion.
     * The constructor is focused on setting up the MaterialBag object without unnecessary complexity or external dependencies.
     * This keeps the class clean and easy to maintain.
     * @param materials An array of materials to be added to the inventory.
     * @param amounts An array of amounts (in tons) corresponding to each material.
     */
    public MaterialBag(Material[] materials, Double[] amounts) {
        this.materialInventory = new HashMap<>();
        IntStream.range(0, materials.length).forEach(
                i -> materialInventory.put(materials[i], amounts[i])
        );
    }

    /**
     * Sets the amount for a specific material in the inventory.
     * This method is an example of an object-oriented approach, as we are encapsulating the material inventory
     * and providing a method to modify it directly via the MaterialBag object.
     *
     * BAD: Direct usage of `materialInventory.put(material, amount)` without abstraction increases the coupling between
     * MaterialBag and the `HashMap`. This results in a strong dependency between the `MaterialBag` object and the
     * specific data structure used.
     * @param material The material we are using (!=null)
     * @param amount The amount of material we are defining in tons
     */
    public void setMaterial(Material material, double amount) {
        this.materialInventory.put(material, amount);
    }


    // GOOD: The `addMaterial()` method abstracts away the logic of adding material to the inventory,
    // ensuring low coupling.
    /**
     * Adds amount if addAmount > 0, subtracts amount if addAmount < 0
     * @param material Material where amount shall be added/subtracted(!=null)
     * @param addAmount Amount you want to add/subtract in Tons
     */
    public void addMaterial(Material material, double addAmount) {
        this.materialInventory.merge(material, addAmount, Double::sum);
    }

    /**
     * Creates a new MaterialBag where the amount of each material is scaled by a specified multiplier.
     * This is a blend of object-oriented design (creating and returning a new object) and procedural logic
     * (applying a multiplier to each material's amount).
     * The approach here focuses on returning a modified version of the original object, thus adhering to
     * the object-oriented principle of immutability.
     *
     * GOOD: The `times` method creates a new instance of MaterialBag and performs a deep copy of the material
     * inventory.
     * This ensures that the original object is not altered when performing operations on the new object.
     *
     * @param multiplier The factor by which the amount of each material is multiplied.
     * @return A new MaterialBag with the quantities of all materials multiplied by the given factor.
     */
    public MaterialBag times(double multiplier){
        MaterialBag tmp = materialInventory.keySet().stream().collect(
                MaterialBag::new,
                (bag, material) -> bag.setMaterial(
                        material,
                        materialInventory.get(material)*multiplier
                ),
                (bag1, bag2) -> {}
        );
        return tmp;
    }

    /**
     * Copies an MaterialBag-Object
     * @return Exact Copy of original Object
     */
    public MaterialBag copy(){
        MaterialBag tmp = new MaterialBag();
        materialInventory.forEach(tmp::setMaterial);
        return tmp;
    }

    /**
     * Creates a deep copy of the current MaterialBag.
     * This is an object-oriented approach because it returns a new MaterialBag instance,
     * which is a copy of the original inventory. The goal here is to avoid direct manipulation of
     * the original object’s state.
     *
     * @param bag MaterialBag which should be added (!= null)
     * @return New MaterialBag made out of combining this and bag
     */
    public MaterialBag add(MaterialBag bag){
        MaterialBag tmp = copy();
        bag.materialInventory.forEach(
                tmp::addMaterial
        );
        return tmp;
    }


    /**
     * Object-Oriented because it returns a new MaterialBag containing a subset of the original data,
     * but the calculation itself is more procedural.
     *
     * BAD: The `getWaste()` method directly accesses the `Cost` data of the `Material` object and uses the
     * `getWaste()` method of the `Cost` object. This leads to a strong coupling between `MaterialBag` and the
     * `Material` class, as well as its `Cost` objects.
     * @return New MaterialBag containing all Waste-Materials
     */
    public MaterialBag getWaste(){
        MaterialBag tmp = materialInventory.keySet().stream().collect(
                MaterialBag::new,
                (bag, material) -> bag.setMaterial(
                        material,
                        materialInventory.get(material)*material.getCost().getWaste()
                ),
                (bag1, bag2) -> {}
        );
        return tmp;
    }

    /**
     * This is an example of a procedural approach where we iterate over the inventory
     * and apply a simple counting operation.
     * @return Number of Materials inside MaterialBag
     */
    public int size(){
        return (int) materialInventory.keySet().stream().filter(
                material -> materialInventory.get(material) > 0
        ).count();
    }

    /**
     *
     * Calculates the total cost of all materials in the inventory, considering each material's cost,
     * CO2 emissions, and waste, scaled by their respective quantities.
     * This approach is a mix of object-oriented (CostContainer object to represent the total)
     * and procedural (iterating over the inventory and performing the calculations).
     *
     * GOOD: The `getTotalCost()` method uses dynamic binding to interact with the `Material` class's `getCost()`
     * method.
     * @return A CostContainer object representing the total cost of all materials in the inventory.
     */
    public CostContainer getTotalCost() {
        CostContainer totalCost = materialInventory.entrySet().stream()
                .map(entry -> entry.getKey().getCost().multiplyContainer(entry.getValue()))
                .reduce(new CostContainer(0f, 0f, 0f), CostContainer::addCostContainer);

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
        return materialInventory.keySet().toArray(Material[]::new);
    }

    /**
     * Read a MaterialBag from csv File
     * @param path Path of File (!=null)
     *
     *  Reads a MaterialBag from a CSV file. Each line in the CSV should contain the material's amount,
     *  name, cost, CO2 emissions, and waste.
     *  this is a procedural approach because it deals with reading data from an external source and
     *  parsing it into an object.
     *
     *  BAD: The `readFromFile` method directly reads and processes data from a CSV file.
     *  This logic is tightly coupled with file I/O, which makes the class less flexible and harder to maintain.
     *  A better solution would be to move the file reading logic into a separate utility class, so that `MaterialBag`
     *  can focus solely on managing materials. (Which is done by Database.java, so this method is depreciated)
     *
     *  @param path Path of File
     */
    public static MaterialBag readFromFile(String path){

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            MaterialBag newBag = new MaterialBag();

            newBag.materialInventory = new HashMap<Material, Double>
                    (
                        br.lines().map(
                            line -> {
                                String[] values = line.split(", ");
                                if(values.length != 5){throw new IllegalArgumentException();}
                                double amount = Double.parseDouble(values[0]);
                                String materialName = values[1];
                                double materialCost = Double.parseDouble(values[2]);
                                double materialCo2 = Double.parseDouble(values[3]);
                                double materialWaste = Double.parseDouble(values[4]);

                                CostContainer cost = new CostContainer(materialCost, materialCo2, materialWaste);
                                Material material = new Material(materialName, cost);

                                return new AbstractMap.SimpleEntry<Material, Double>(material, amount);
                            }
                        )
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                    );

            return newBag;

        } catch(IOException e){
            throw new RuntimeException("Help!");
        }

    }

    @Override
    public String toString(){
        return materialInventory.keySet().stream().map(
                material ->
                        "[" + material.getName() + ", " + materialInventory.get(material) + "]")
                .collect(Collectors.joining());

    }
}
