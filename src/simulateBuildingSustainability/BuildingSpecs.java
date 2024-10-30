package simulateBuildingSustainability;

// nominal abstraction: collects data that can be used to initialize an object of the Building class
// this data is understood to specify properties of a building and its exterior that match
// the properties of the buildings described in the assignment
public class BuildingSpecs {
    public int lifetime;
    public int age;
    public MaterialBag shellConstruct;
    public ApartmentSpecs apartmentSpecs;
    private final CostContainer heatingAndMaintenanceCosts;
    public double recycleRate;

    /**
     *
     * @param lifetime the general lifetime of the building
     * @param shellConstruct the shell construction of the building
     * @param apartmentSpecs config for the buildings apartments
     * @param recycleRate Recycle rate [0,1] when building is demolished
     */
    public BuildingSpecs(
            int lifetime,
            MaterialBag shellConstruct,
            ApartmentSpecs apartmentSpecs,
            CostContainer heatingAndMaintenanceCosts,
            double recycleRate
    ) {
        this.age = 0;
        this.lifetime = lifetime;
        this.shellConstruct = shellConstruct;
        this.apartmentSpecs = apartmentSpecs;
        this.heatingAndMaintenanceCosts = heatingAndMaintenanceCosts;
        this.recycleRate=recycleRate;
    }

    public int getLifetime() {
        return lifetime;
    }

    public int getAge() {
        return age;
    }

    public MaterialBag getShellConstruct() {
        return shellConstruct;
    }

    public double getRecycleRate() {
        return recycleRate;
    }

    public ApartmentSpecs getApartmentSpecs() {
        return apartmentSpecs;
    }

    public CostContainer getHeatingAndMaintenanceCosts() {
        return heatingAndMaintenanceCosts;
    }

}
