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

    int getX(){return dimensions[0];}
    int getY(){return dimensions[1];}
    int getZ(){return dimensions[2];}

    /**
     * @param architecture Architecture to compare
     * @return 1 if this object is bigger, 0 if both are equal, -1 if this is smaller
     */
    int compareFootprint(Architecture architecture){

        if(getX() > architecture.getX() && getY() > architecture.getY()){
            return 1;
        }

        if(getX() == architecture.getX() && getY() == architecture.getY()){
            return 0;
        }

        return -1;
    }

    int getFootprint(){
        return getX()*getY();
    }

}
