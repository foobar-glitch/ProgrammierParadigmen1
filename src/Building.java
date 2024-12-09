// implements Urban Element and represents a residential buildings
// keeps and updates state of a building during the course of a simulation
// returns the current average happiness accumulated from all apartments in the building
// as well as the costs incurred by building/demolishing/maintaining the building
// STYLE: nominal abstraction
// just by changing the names of the variable and classes used here
// this class might represent something completely different -> nominal
// (e.g. could easily be an office building with offices instead of apartments)
public class Building implements UrbanElement{
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
    private final Architecture architecture;

    /**
     * Create Building from Record
     * @param buildingBlueprint (!=null)
     */
    public Building(Building.Record buildingBlueprint) {
        this.age = 0;
        this.lifetime = buildingBlueprint.lifetime();
        this.shellConstruct = buildingBlueprint.shellConstruct();
        this.heatingAndMaintenanceCosts = buildingBlueprint.heatingAndMaintenanceCosts();
        this.recycleRate = buildingBlueprint.recycleRate();
        this.apartments = new Apartment[buildingBlueprint.apartmentBlueprint().numberOfApartments()];
        for (int i = 0; i < buildingBlueprint.apartmentBlueprint().numberOfApartments(); i++) {
            apartments[i] = new Apartment(
                    buildingBlueprint.apartmentBlueprint()
            );
        }
        for (Apartment apartment : apartments) {
            this.numberOfResidents += apartment.getNumberOfResidents();
        }
        this.architecture = new Architecture(new int[]{100,100,100});
    }

    public record Record(String name, int lifetime, MaterialBag shellConstruct, Apartment.Record apartmentBlueprint,
                         CostContainer heatingAndMaintenanceCosts, double recycleRate) {
    }

    /**
     * When demolishing you can recycle x percent of material and subtract that value
     * from the total costs
     */
    @Override
    public CostContainer demolishing() {
        CostContainer leftoverMaterialCost = shellConstruct.getTotalCost();
        CostContainer recycledProfit = new CostContainer(
                leftoverMaterialCost.getCost() * recycleRate,
                leftoverMaterialCost.getCo2() * recycleRate,
                leftoverMaterialCost.getWaste() * recycleRate
        );
        leftoverMaterialCost = leftoverMaterialCost.subtractCostContainer(recycledProfit);
        for (Apartment apartment : this.apartments) {
            leftoverMaterialCost = leftoverMaterialCost.addCostContainer(apartment.demolish(recycleRate));
        }

        return leftoverMaterialCost.multiplyContainer(1.0 / numberOfResidents);
    }

    /**
     * Currently renovating all apartments with the same amount of renovating material.
     * Otherwise: Specify all apartments (by index for example) and how much they'll lose for
     * the aging.
     * return: Average cost over residents
     */
    @Override
    public CostContainer age() {
        // Renovating all apartments the same amounts
        CostContainer ageingCosts = new CostContainer(0f, 0f, 0f);
        if (age == 0) {
            ageingCosts = ageingCosts.addCostContainer(shellConstruct.getTotalCost());
        }
        age++;

        for (Apartment apartment : apartments) {
            if (!apartment.update()) {
                ageingCosts = ageingCosts.addCostContainer(apartment.renovate());

            }
            ageingCosts = ageingCosts.addCostContainer(apartment.currentCost());
        }
        ageingCosts.addCostContainer(heatingAndMaintenanceCosts.multiplyContainer(apartments.length));
        return ageingCosts.multiplyContainer((double) 1 / numberOfResidents);
    }

    /**
     * Check if the age is lower than the lifetime
     */
    public boolean checkAge() {
        return age <= lifetime;
    }

    /**
     * @return Average Satisfaction of the Residents
     */
    @Override
    public double satisfaction() {

        double s = 0.0f;
        int people = 0;
        for (Apartment apartment : apartments) {
            people += apartment.getNumberOfResidents();
            s += apartment.getSatisfaction() * apartment.getNumberOfResidents();
        }
        return s / people;
    }

    public Apartment[] getApartments() {
        return apartments;
    }

    // returns the current year not how many years already passed
    @Override
    public int getAge() {
        return age + 1;
    }

    public Architecture getArchitecture(){
        return  architecture;
    }
}
