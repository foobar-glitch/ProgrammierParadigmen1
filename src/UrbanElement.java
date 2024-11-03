/**
 * This Interface is for every part of an urban space
 * It includes Artifacts, Buildings, Terrains, possibly Cities and Countries
 */
public interface UrbanElement {

    /**
     * Demolishes the whole Urban Element
     * @return the cost of demolishing this
     */
    CostContainer demolishing();

    /**
     * Increases the age of this +1
     * @return Annual cost of this element and its sub-elements
     */
    CostContainer age();

    /**
     * @return Satisfaction of the people living/visiting in the urban element
     */
    double satisfaction();

    /**
     * @return Age of this element
     */
    int getAge();

    /**
     * @return Specific Architecture of this
     */
    Architecture getArchitecture();

}
