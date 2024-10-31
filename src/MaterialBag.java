import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * This stores all materials and their corresponding amounts
 *
 * */
public class MaterialBag {
    /* A hashmap of the material and its amount */
    private HashMap<Material, Double> materialInventory;

    public MaterialBag() {
        this.materialInventory = new HashMap<>();
    }

    public MaterialBag(Material[] materials, Double[] amounts) {
        this.materialInventory = new HashMap<>();
        for (int i = 0; i < materials.length; i++) {
            materialInventory.put(materials[i], amounts[i]);
        }
    }

    /**
     *
     * @param material The material we are using
     * @param amount The amount of material we are defining in tons
     */
    public void setMaterial(Material material, double amount) {
        this.materialInventory.put(material, amount);
    }

    public void subtractMaterial(Material material, double subtractAmount) {
        double newAmount = this.materialInventory.get(material) - subtractAmount;
        this.materialInventory.put(material, newAmount);
    }

    public void addMaterial(Material material, double addAmount) {
        double newAmount = this.materialInventory.get(material) + addAmount;
        this.materialInventory.put(material, newAmount);
    }



    /**
     * This function makes a copy of the object in which the amount of every
     * Material is changed by the factor in param multiplier
     *
     * @param multiplier Factor with which the amount of every material is multiplied
     * @return New, copied and (amount by multiplier) changed MaterialBag-Object
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
     * @param bag MaterialBag which should be added
     * @return New MaterialBag made out of combining this and bag
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
     * @return Number of Materials inside MaterialBag
     */
    public int size(){
        int size = 0;
        for(Material m: materialInventory.keySet()){
            if(materialInventory.get(m) > 0) size++;
        }
        return size;
    }

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
     * Returns all Materials inside MaterialBag as List
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
     * Read a MaterialBag from csv File
     * @param path Path of File
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
