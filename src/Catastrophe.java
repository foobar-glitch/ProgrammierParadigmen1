import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Catastrophe {
    String eventName;
    double damage;
    double probability;

    /**
     *
     * @param eventName The name of the catastrophic event
     * @param damage The amount of damage that is created by the event in percentage [0,1]
     * @param probability The absolute probability that the event happens
     */
    Catastrophe(String eventName, double damage, double probability) throws IllegalArgumentException {
        if(damage>1 || damage<0){
            throw new IllegalArgumentException("Damage must be between 1 and 0");
        }
        if(probability>1 || probability<0){
            throw new IllegalArgumentException("Probability must be between 1 and 1");
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
