public class Apartment {

    /* Material used to build and renovate the apartment */
    private MaterialBag constructionMaterial;

    /* Waste generated by construction work */
    private CostContainer costs;

    /* Maximum Lifetime of the apartment, age and number of residents */
    private int lifetime, age, residents;

    /* Definition of the intervall [factor, 0] in which Satisfaction
    is measured in getSatisfaction */
    private static final int  satisfactionFactor = 1;


    /**
     * Apartment is immediately built automatically after creating the Object
     */
    public Apartment(MaterialBag constructionMaterial, int lifetime, int residents){
        this.age = 0;
        this.lifetime = lifetime;
        this.residents = residents;
        this.constructionMaterial = constructionMaterial;
        this.costs = constructionMaterial.getTotalCost();
    }

    /**
     * Increments the Age (+1 year)
     * @return FALSE if the Apartment exceeded its lifetime
     * */
    public int update(){
        // TODO: Calculate annual costs to cost vector
        // TODO: Perhaps automatically demolish Apartment if exceeds lifetime
        age+=1;
        return age<=lifetime;
    }

    /**
     * Calculates the yearly costs of the Apartment
     * @return Cost of this Year
     */
    public CostContainer currentCost(){
        CostContainer tmp = new CostContainer(0,0, constructionMaterial.getTotalCost().getWaste());
        return tmp;
    }

    /**
     * Renovates the Apartment completely
     * @param renovationMaterial Material needed for Renovation
     */
    public void renovate(MaterialBag renovationMaterial){
        age=0;
        this.costs.addCostContainer(renovationMaterial.getTotalCost());
    }

    /**
     * Calculates Residents Satisfaction depending on
     * the state of their apartment
     * @return Factor of residents' satisfaction
     */
    public float getSatisfaction(){
        double tmp = ((double) satisfactionFactor / lifetime)*(-Math.pow(age, 2)) + satisfactionFactor;
        return (float) tmp;
    }

    /**
     * Returns the number of Residents living in the apartment
     */
    public int getNumberOfResidents(){ return residents; }

    /**
     * Returns the total cost generated by the Apartment
     */
    public CostContainer getTotalCost(){
        return this.costs;
    }

    /**
     * Demolishes the Apartment and returns all costs
     */
    public CostContainer demolish(){
        // TODO: Change this line and add demolishing costs
        this.costs.addCostContainer(constructionMaterial.getTotalCost());
        return this.costs;
    }


}
