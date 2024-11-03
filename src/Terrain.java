public class Terrain {

    /* Cost of Building on terrain per m^2 */
    private CostContainer cost;
    /* Building built on terrain */
    private Building building;

    public Terrain(CostContainer cost){
        if(cost == null)
            throw new IllegalArgumentException("CostContainer can't be null");
        this.cost = cost;
    }

    public Terrain(CostContainer cost, Building building){
        if(cost == null || building == null)
            throw new IllegalArgumentException("Neither cost nor building can be null");
        this.cost = cost;
        this.building = building;
    }




}
