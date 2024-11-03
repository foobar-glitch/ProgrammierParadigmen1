public class Building {
    private final int lifetime;
    private int age;
    /* The materials of the shell of the building */
    private final MaterialBag shellConstruct;
    /* The materials of the building part that has to be renovated*/
    private final Apartment[] apartments;
    private final CostContainer heatingAndMaintenanceCosts;
    private final double recycleRate;
    private int numberOfResidents = 0;
    /* Architecture of Building (e.g. Footprint, ...) */
    private Architecture architecture;

    // TODO explain param
    public Building(Building.Record buildingBlueprint)
    {
        this.age = 0;
        this.lifetime = buildingBlueprint.lifetime();
        this.shellConstruct = buildingBlueprint.shellConstruct();
        this.heatingAndMaintenanceCosts = buildingBlueprint.heatingAndMaintenanceCosts();
        this.recycleRate = buildingBlueprint.recycleRate();

        this.apartments = new Apartment[buildingBlueprint.apartmentBlueprint().numberOfApartments()];
        for(int i = 0; i < buildingBlueprint.apartmentBlueprint().numberOfApartments(); i++){
            apartments[i] = new Apartment(
                    buildingBlueprint.apartmentBlueprint().material(),
                    buildingBlueprint.apartmentBlueprint().lifetimeApartment(),
                    buildingBlueprint.apartmentBlueprint().residentNumber(),
                    buildingBlueprint.apartmentBlueprint().happinessUpperBound());
        }

        for(Apartment apartment: apartments){
            this.numberOfResidents += apartment.getNumberOfResidents();
        }
    }

    public record Record(String name, int lifetime, MaterialBag shellConstruct, Apartment.Record apartmentBlueprint, CostContainer heatingAndMaintenanceCosts, double recycleRate){}

    /**
     * When demolishing you can recycle x percent of material and subtract that value
     * from the total costs
     * */
    public CostContainer demolishing(){
        CostContainer leftoverMaterialCost = shellConstruct.getTotalCost();
        CostContainer recycledProfit = new CostContainer(
                leftoverMaterialCost.getCost() * recycleRate,
                leftoverMaterialCost.getCo2() * recycleRate,
                leftoverMaterialCost.getWaste() * recycleRate
        );
        leftoverMaterialCost = leftoverMaterialCost.subtractCostContainer(recycledProfit);
        for(Apartment apartment: this.apartments){
            leftoverMaterialCost = leftoverMaterialCost.addCostContainer(apartment.demolish(recycleRate));
        }

        return leftoverMaterialCost.multiplyContainer(1.0/numberOfResidents);
    }

    /**
     * Currently renovating all apartments with the same amount of renovating material.
     * Otherwise: Specify all apartments (by index for example) and how much they'll lose for
     * the aging.
     * return: Average cost over residents
     * */
    public CostContainer age(){
        // Renovating all apartments the same amounts
        CostContainer ageingCosts = new CostContainer(0f, 0f, 0f);
        if(age==0){
            ageingCosts = ageingCosts.addCostContainer(shellConstruct.getTotalCost());
        }
        age++;

        for (Apartment apartment: apartments) {
            if(!apartment.update()){
                ageingCosts = ageingCosts.addCostContainer(apartment.renovate());

            }
            ageingCosts = ageingCosts.addCostContainer(apartment.currentCost());
        }
        ageingCosts.addCostContainer(heatingAndMaintenanceCosts.multiplyContainer(apartments.length));
        return ageingCosts.multiplyContainer((double) 1/numberOfResidents);
    }

    /**
     * Check if the age is lower than the lifetime
     * */
    public boolean checkAge(){
        return age <= lifetime ;
    }

    /**
     * @return Average Satisfaction of the Residents
     */
    public double satisfaction(){

        double s = 0.0f;
        int people = 0;
        for(int i=0; i<apartments.length; i++){
            people+=apartments[i].getNumberOfResidents();
            s+=apartments[i].getSatisfaction()*apartments[i].getNumberOfResidents();
        }

        return s/people;
    }

    public Apartment[] getApartments() {
        return apartments;
    }

    // returns the current year not how many years already passed
    public int getAge() {return age+1;}

}
