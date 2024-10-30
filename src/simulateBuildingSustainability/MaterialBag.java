package simulateBuildingSustainability;

import java.util.HashMap;

// TODO could also inherit from DefaultCosts if DF would be generic??

/**
 * This stores all materials and their corresponding amounts
 *
 * */
public class MaterialBag {
    /* A hashmap of the material and its amount */
    private final HashMap<Material, Double> materialInventory;

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
        CostContainer totalCost = new CostContainer();
        for (Material material : materialInventory.keySet()) {
            CostContainer cost = material.getCost();
            cost = new CostContainer();
            cost.multiplyCosts(materialInventory.get(material));
            totalCost.addCosts(cost);
        }
        return totalCost;
    }

    public HashMap<Material, Double> getMaterialInventory() { return materialInventory; }
}
