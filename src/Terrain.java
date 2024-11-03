public class Terrain implements UrbanElement{

    /* Cost of Building on terrain per m^2 */
    private CostContainer buildingCost, maintainingCost;
    /* Building built on terrain */
    private Building building;
    /* */
    private final int length, width;
    /* */
    private float quality;
    /* */
    private float state;
    private int age = 0;

    public Terrain(int length, int width, float quality, CostContainer buildCost, CostContainer maintainingCost, Building building){
        if(buildCost == null || maintainingCost == null){ throw new IllegalArgumentException("Neither cost nor building can be null");}
        this.buildingCost = buildCost;
        this.maintainingCost = maintainingCost;
        this.length = length;
        this.width = width;
        this.quality = quality;
        this.state = 1.0f;

        if(building != null){
            // TODO
            int buildingWidth = building.architecture().getDimensions()[0];
            int buildingLength = building.architecture().getDimensions()[1];

            // check if Building size <= Terrain size
            if(buildingLength > length || buildingWidth > width){
                throw new IllegalArgumentException("Building bigger than Terrain");
            }

            // Calculate percentage of preserved terrain
            this.state = 1 - (float) (buildingLength * buildingWidth) / length*width;
        }
    }


    @Override
    public CostContainer demolishing() {
        if(building == null){return new CostContainer();}
        CostContainer tmp = building.demolishing();
        building = null;
        return tmp;
    }

    @Override
    public CostContainer age() {
        age++;

        if(building == null){
            // Renaturate Terrain if building was demolished
            if(state < 1){
                state = state*(1+quality);
            }
            return maintainingCost.multiplyContainer(state);
        }

        CostContainer tmp = building.age();
        tmp = tmp.addCostContainer(calculateOwnCost());

        // Add costs of building the object on terrain
        if(age==1){
            tmp.addCostContainer(buildingCost.multiplyContainer(1-state));
        }

        return tmp;
    }

    @Override
    public double satisfaction() {
        return building.satisfaction()*quality*state;
    }

    @Override
    public int getAge() {
        return age;
    }

    private CostContainer calculateOwnCost(){
        return this.maintainingCost.multiplyContainer(state);
    }

}
