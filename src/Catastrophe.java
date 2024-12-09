/**
 * The Catastrophe class represents a catastrophic event characterized by its name,
 * the potential damage it may cause, and the probability of its occurrence.
 * This class uses object-oriented principles to encapsulate event data and ensure
 * that values are validated upon creation.
 *
 * Abstraction properties:
 *
 * The class abstracts the details of a catastrophic event by encapsulating key attributes
 * (event name, damage, and probability) and providing a simplified interface for interacting with this data.
 *
 * The internal representation (data validation and fields) is hidden, while the user interacts only through
 * the constructor and getter methods.
 *
 * nominal abstraction because it creates a named entity (Catastrophe) that encapsulates data and behavior,
 * abstracting away internal implementation details.
 */
public class Catastrophe {
    String eventName;
    double damage;
    double probability;

    /**
     * Constructs a new Catastrophe object with a specified name, damage, and probability.
     * This constructor demonstrates object-oriented encapsulation by validating input values
     * and initializing all attributes in a single step, thereby ensuring data integrity.
     *
     * @param eventName   (!=null) the name of the catastrophic event
     * @param damage      the amount of damage caused by the event as a percentage [0, 1]
     * @param probability the probability of the event occurring as a value [0, 1]
     * @throws IllegalArgumentException if damage or probability is outside the [0, 1] range
     */
    Catastrophe(String eventName, double damage, double probability) throws IllegalArgumentException {
        if(damage>1 || damage<0){
            throw new IllegalArgumentException("Damage must be between 1 and 0");
        }
        if(probability>1 || probability<0){
            throw new IllegalArgumentException("Probability must be between 1 and 0");
        }

        this.eventName = eventName;
        this.damage = damage;
        this.probability = probability;
    }

    double getDamage(){
        return damage;
    }

    double getProbability() {
        return probability;
    }

    String getEventName(){
        return eventName;
    }
}
