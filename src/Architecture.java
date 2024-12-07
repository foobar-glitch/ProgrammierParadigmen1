public class Architecture {

    private final int[] dimensions;

    /**
     * This class contains Architecture in form of width, length and height
     * @param dimensions (!=null) int[3]{x,y,z} or {width, length, height}
     */
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
     * @return =1 if this object is bigger, 0 if both are equal, -1 if this is smaller
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

    /**
     * @param architecture (!=null) Object to compare with this
     * @return >= 0 if this is bigger, 0 if it's the same, <0 if it's smaller
     */
    int compareVolume(Architecture architecture){
        return (getVolume() - architecture.getVolume());
    }

    /**
     * @param volume Integer (which is volume of sth.) to compare with this
     * @return >= 0 if this is bigger, 0 if it's the same, <0 if it's smaller
     */
    int compareVolume(int volume){
        return (getVolume() - volume);
    }

    /**
     * @return Footprint of the Object (width*length || x*y)
     */
    int getFootprint(){
        return getX()*getY();
    }

    /**
     * @return Volume of Architecture in m^3 (x*y*z)
     */
    public int getVolume(){
        return getFootprint()*getZ();
    }

}
