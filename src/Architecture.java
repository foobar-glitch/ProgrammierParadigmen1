public class Architecture {

    private final int[] dimensions;
    public Architecture(int[] dimensions){
        // validate data
        if(dimensions.length != 3){throw new IllegalArgumentException("Dimensions must be [x,y,z]");}

        // set data
        this.dimensions = dimensions;
    }



    /**
     * @return Dimensions of the Building: [length, depth, height]
     */
    int[] getDimensions(){
        return dimensions;
    }

}
