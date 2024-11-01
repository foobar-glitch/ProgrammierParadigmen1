public class Apartment {

    /* Material used to build and renovate the apartment */
    private final MaterialBag constructionMaterial;
    /* Material currently in the Building */
    private MaterialBag material;

    /* Material which is currently wasted in the year */
    private MaterialBag wasteMaterial;

    /* Waste generated by construction work */
    private CostContainer costs;

    /* Maximum Lifetime of the apartment, age and number of residents */
    private int lifetime, age, numberOfResidents;

    /* Definition of the intervall [factor, 0] in which Satisfaction
    is measured in getSatisfaction */
    private double  satisfactionFactor = 1;


    public int getLifetime() {
        return lifetime;
    }

    /**
     * Apartment is immediately built automatically after creating the Object
     */
    public Apartment(MaterialBag constructionMaterial, int lifetime, int numberOfResidents, double maxHappiness){
        this.age = 0;
        this.lifetime = lifetime;
        this.numberOfResidents = numberOfResidents;
        this.constructionMaterial = constructionMaterial;
        this.material = constructionMaterial;
        this.costs = constructionMaterial.getTotalCost();
        this.wasteMaterial = new MaterialBag();
        this.satisfactionFactor = maxHappiness;
    }

    public record Record(MaterialBag material, int residentNumber, int numberOfApartments, int lifetimeApartment, double happinessUpperBound){

    }

    /**
     * Increments the Age (+1 year)
     * @return FALSE if the Apartment exceeded its lifetime
     * */
    public boolean update(){
        age+=1;

        // Update Waste and new Current Construction Material
        MaterialBag waste = constructionMaterial.getWaste();
        wasteMaterial = wasteMaterial.add(
                waste
        );
        material = material.add(
                waste.times(-1)
        );

        // If no Material is there anymore :C (where's my house)
        if(constructionMaterial.size() == 0) return false;

        // If lifetime is exceeded return False
        return age<=lifetime;
    }

    /**
     * Calculates the yearly costs of the Apartment
     * @return Cost of this Year
     */
    public CostContainer currentCost(){
        CostContainer costs = wasteMaterial.getTotalCost();
        wasteMaterial = new MaterialBag();
        return costs;
    }

    /**
     * Renovates the Apartment completely
     */
    public CostContainer renovate(){
        MaterialBag tmp = constructionMaterial.copy();
        tmp = tmp.add(material.times(-1));

        // Reset to Original Glory
        age = 0;
        material = constructionMaterial;

        satisfactionFactor*=0.9;

        return tmp.getTotalCost().multiplyContainer(1.0/ numberOfResidents);
    }

    /**
     * Calculates Residents Satisfaction depending on
     * the state of their apartment
     * @return Factor of residents' satisfaction
     */
    public double getSatisfaction(){
        double tmp = ((double) satisfactionFactor / Math.pow(lifetime, 2))*(-Math.pow(age, 2)) + satisfactionFactor;
        return (double) tmp;
    }

    /**
     * Returns the number of Residents living in the apartment
     */
    public int getNumberOfResidents(){ return numberOfResidents; }

    /**
     * Returns the total cost generated by the Apartment
     */
    public CostContainer getTotalCost(){
        return this.costs;
    }

    /**
     * Demolishes the Apartment and returns all costs
     */
    public CostContainer demolish(double recycleRate){
        // TODO: Change this line and add demolishing costs
        CostContainer leftoverMaterial = material.getTotalCost();
        CostContainer recycledProfit = new CostContainer(
                leftoverMaterial.getCost() * recycleRate,
                leftoverMaterial.getCo2() * recycleRate,
                leftoverMaterial.getWaste() * recycleRate
        );
        CostContainer tmp = material.getTotalCost();
        tmp = tmp.subtractCostContainer(recycledProfit);
        return tmp;
    }

}
