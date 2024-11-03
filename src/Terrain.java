public class Terrain implements UrbanElement{

    /* Cost of Building on terrain per m^2 */
    private CostContainer cost;
    /* Building built on terrain */
    private Building building;
    /* */
    private final int length, width;
    /* */
    private double quality;
    /* */
    private float state;
    private int age = 0;

    public Terrain(int length, int width, double quality, CostContainer cost, Building building){
        if(cost == null){ throw new IllegalArgumentException("Neither cost nor building can be null");}
        this.cost = cost;
        this.length = length;
        this.width = width;
        this.quality = quality;
        this.state = 1.0f;

        if(building != null){
            // TODO
        }
    }


    @Override
    public CostContainer demolishing() {
        return null;
    }

    @Override
    public CostContainer age() {
        if(building == null){
            age++;
            return new CostContainer();
        }

        return null;
    }

    @Override
    public double satisfaction() {
        return 0;
    }

    @Override
    public int getAge() {
        return 0;
    }

}
