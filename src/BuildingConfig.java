public class BuildingConfig {
    public int lifetime;
    public int age;
    public MaterialBag shellConstruct;
    public ApartmentConfig apartmentConfig;

    /**
     *
     * @param lifetime the general lifetime of the building
     * @param shellConstruct the shell construction of the building
     * @param apartmentConfig config for the buildings apartments
     * @param recycleRate Recycle rate [0,1] when building is demolished
     */
    public BuildingConfig(
            int lifetime,
            MaterialBag shellConstruct,
            ApartmentConfig apartmentConfig,
            double recycleRate
    ) {
        this.age = 0;
        this.lifetime = lifetime;
        this.shellConstruct = shellConstruct;
        this.apartmentConfig = apartmentConfig;
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

    public double recycleRate;
}
