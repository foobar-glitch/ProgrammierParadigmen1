import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * The Catastrophe class represents a catastrophic event characterized by its name,
 * the potential damage it may cause, and the probability of its occurrence.
 * This class uses object-oriented principles to encapsulate event data and ensure
 * that values are validated upon creation.
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
     * @param eventName   the name of the catastrophic event
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
