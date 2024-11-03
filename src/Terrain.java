public class Terrain implements UrbanElement{

    /* Cost of Building on terrain per m^2 */
    private CostContainer buildingCost, maintainingCost, initialCost;
    /* Building built on terrain */
    private UrbanElement[] urbanElements;
    /* */
    private final Architecture architecture;
    /* */
    private float quality;
    /* */
    private float state;
    private int age = 0;

    public Terrain(Architecture architecture, float quality, CostContainer buildCost, CostContainer maintainingCost, UrbanElement[] urbanElements){
        if(buildCost == null || maintainingCost == null){ throw new IllegalArgumentException("Neither cost nor building can be null");}
        this.buildingCost = buildCost;
        this.maintainingCost = maintainingCost;
        this.architecture = architecture;
        this.quality = quality;
        this.state = 1.0f;

        this.urbanElements = urbanElements;

        if(urbanElements != null && urbanElements.length > 0){
            initialCost = new CostContainer();
            int x = architecture.getX();
            int y = architecture.getY();

            for(UrbanElement elem: urbanElements){
                Architecture tmp = elem.getArchitecture();
                if(tmp.getX() <= x && tmp.getY() <= y){
                    x-=tmp.getX();
                    y-=tmp.getY();

                    //Build Building
                    initialCost = initialCost.addCostContainer(
                            buildCost.multiplyContainer(
                                    tmp.getFootprint()
                            )
                    );
                    state-= (float) tmp.getFootprint() /architecture.getFootprint();
                }
                else{throw new IllegalArgumentException("UrbanElement too big for Terrain");}
            }
        }
    }

    public Terrain(Terrain.Record record, UrbanElement[] urbanElements){
        this.buildingCost = record.buildCost();
        this.maintainingCost = record.maintainingCost();
        this.architecture = record.architecture();
        this.quality = record.quality();
        this.state = 1.0f;

        this.urbanElements = urbanElements;

        if(urbanElements != null && urbanElements.length > 0){
            initialCost = new CostContainer();
            int x = architecture.getX();
            int y = architecture.getY();

            for(UrbanElement elem: urbanElements){
                Architecture tmp = elem.getArchitecture();
                if(tmp.getX() <= x && tmp.getY() <= y){
                    x-=tmp.getX();
                    y-=tmp.getY();

                    //Build Building
                    initialCost = initialCost.addCostContainer(
                            record.buildCost().multiplyContainer(
                                    tmp.getFootprint()
                            )
                    );
                    state-= (float) tmp.getFootprint() /architecture.getFootprint();
                }
                else{throw new IllegalArgumentException("UrbanElement too big for Terrain");}
            }
        }
    }

    public record Record(Architecture architecture, float quality, CostContainer buildCost, CostContainer maintainingCost){}


    /**
     * Every UrbanElement on this Terrain will be demolished
     * @return Demolishing costs
     */
    @Override
    public CostContainer demolishing() {
        if(urbanElements == null || urbanElements.length == 0){return new CostContainer();}

        CostContainer tmp = new CostContainer();
        for(UrbanElement elem : urbanElements){
            tmp = tmp.addCostContainer(elem.demolishing());
        }
        urbanElements = new UrbanElement[]{};
        return tmp;
    }

    @Override
    public CostContainer age() {
        // Increment age
        age++;

        //Calculate stuff (was one of the urbanElements demolished? if yes renaturate the new free space)
        int footprint = 0;
        for(UrbanElement elem : urbanElements){
            footprint+=elem.getArchitecture().getFootprint();
        }
        float newState = 1 - (float) footprint / architecture.getFootprint();
        if(state < newState){
            state*=(1+quality);
            if(state > newState) state = newState;
        }


        CostContainer tmp = new CostContainer();

        // For building UrbanElementsOnTerrain costs
        tmp = tmp.addCostContainer(initialCost);
        initialCost=new CostContainer();

        // Add all elements annual costs
        for(UrbanElement elem : urbanElements){
            tmp = tmp.addCostContainer(elem.age());
        }

        // Add Maintaining Costs (perhaps CO2 deficites if trees)
        tmp.addCostContainer(calculateOwnCost());

        return tmp;
    }

    @Override
    public double satisfaction() {

        if(urbanElements == null || urbanElements.length == 0) return 0d;

        double satisfaction = 0d;
        for(UrbanElement elem : urbanElements){
            satisfaction+=elem.satisfaction();
        }
        satisfaction/=urbanElements.length;

        // In an awesome environment, peoples happiness is boosted /-\==> <==/-\ (is this art? i doubt it)
        return satisfaction*(1+quality*state);
    }

    /**
     * Demolishes UrbanElement elem if inside This
     * @param elem UrbanElement which should be demolished
     * @return Costs of demolishing the UrbanElement
     */
    public CostContainer demolish(UrbanElement elem){
        CostContainer tmp = new CostContainer();

        // check if UrbanElement is built on This
        for(UrbanElement u : urbanElements){
            // If elem is inside
            if(u.equals(elem)){
                UrbanElement[] newElems = new UrbanElement[urbanElements.length-1];
                int index=0;
                for(UrbanElement e : urbanElements){
                    if(!e.equals(elem)) newElems[index++] = e;
                }
                urbanElements = newElems;
                return elem.demolishing();
            }
        }
        return tmp;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public Architecture getArchitecture() {
        return architecture;
    }

    private CostContainer calculateOwnCost(){
        return this.maintainingCost.multiplyContainer(state);
    }

}
