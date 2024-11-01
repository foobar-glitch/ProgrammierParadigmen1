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

    public static Catastrophe[] readCatastrophesFromFile(String path, double eventProbability){
        ArrayList<Catastrophe> catastrophes = new ArrayList<Catastrophe>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            String fieldNames =  bufferedReader.readLine();
            if (fieldNames != null) {
                String[] names = fieldNames.split(", ");
                if(!names[0].equals("eventName") || !names[1].equals("damage") || !names[2].equals("probability")) {
                    throw new UnsupportedEncodingException(path + " in wrong format");
                }
            }
            for(String line; (line = bufferedReader.readLine()) != null; ) {
                String[] values = line.split(", ");
                if(values.length != 3){
                    throw new UnsupportedEncodingException(path + " in wrong format: " + line);
                }

                String name = values[0];
                double damage = Double.parseDouble(values[1]);
                double probability = Double.parseDouble(values[2]) * eventProbability;
                catastrophes.add(new Catastrophe(name, damage, probability));
            }
        } catch (IOException e) {
            throw new RuntimeException("File: " + path + " does not exist");
        }
        return catastrophes.toArray(new Catastrophe[0]);
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
