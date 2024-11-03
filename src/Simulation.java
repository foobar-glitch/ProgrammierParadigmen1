import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

// contains state of the simulation and runs it
// updates state with each time step accordingly
// returns the values that we are measurement for
// nominal abstraction: simulates the specific simulation that'S specified in the assignment
// and returns measurements for a buildings sustainability
public class Simulation {

    private final Building building;
    private final ArrayList<CostContainer> costsPerYear;
    private final ArrayList<Double> happinessPerYear;
    private double totalNumberOfRenovations;

    public Simulation(Building.Record buildingBlueprint) {
        this.building = new Building(buildingBlueprint);
        this.costsPerYear = new ArrayList<CostContainer>();
        this.happinessPerYear = new ArrayList<Double>();
        this.totalNumberOfRenovations = 0;
    }

    private double renovationRate() {
        double renovationRate = 0.0;
        if (totalNumberOfRenovations > 0) {
            renovationRate = totalNumberOfRenovations / (double) building.getApartments().length;
        }
        return renovationRate;
    }

    // run the simulation with the parameters that have been specified in the objects initialization
    // nominal abstraction: behaviour of the simulation models what the assignment's text described
    public SimulationResult runSimulation(Catastrophe[] catastrophes) {
        while (building.checkAge()) {
            CostContainer costsThisYear = new CostContainer(0.0f, 0.0f, 0.0f);
            happinessPerYear.add(building.satisfaction());

            for (Apartment apartment : building.getApartments()) {
                if (Math.random() < 1.0 / apartment.getLifetime()) {
                    costsThisYear.addCostContainer(apartment.renovate());
                    totalNumberOfRenovations++;
                }
            }

            double randomVal = Math.random();
            // Sort the array by probability in ascending order
            Arrays.sort(catastrophes, Comparator.comparingDouble(Catastrophe::getProbability));
            // Checking for catastrophe occurrence and add the cost
            for (Catastrophe catastrophe : catastrophes) {
                if (randomVal < catastrophe.getProbability()) {
                    System.out.printf("\tEvent: %s happened in year %d%n", catastrophe.getEventName(), building.getAge());
                    if (catastrophe.getDamage() == 1.0) {
                        System.out.println("\tCritical Event. Demolishing.");
                        costsThisYear = costsThisYear.addCostContainer(building.demolishing());
                        costsPerYear.add(costsThisYear);
                        return new SimulationResult(costsPerYear, happinessPerYear, renovationRate());
                    }
                    for (Apartment apartment : building.getApartments()) {
                        if (Math.random() < catastrophe.getDamage()) {
                            costsThisYear.addCostContainer(apartment.renovate());
                            totalNumberOfRenovations++;
                        }
                    }
                    // There can only be one event when using break
                    // Otherwise 0.1 would always trigger both 0.2 and 0.3
                    break;
                }
            }
            costsThisYear = costsThisYear.addCostContainer(building.age());
            costsPerYear.add(costsThisYear);
        }
        return new SimulationResult(costsPerYear, happinessPerYear, renovationRate());
    }
}
