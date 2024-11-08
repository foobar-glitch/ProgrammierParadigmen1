import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

// contains state of the simulation and runs it
// updates state with each time step accordingly
// returns the values that we are measurement for
// STYLE: nominal abstraction
// simulates the specific simulation that's specified in the assignment
// and returns measurements for a buildings sustainability
// just by changing the names of the variable and classes used here
// this simulation might be for an entirely different experiment -> nominal
// without this class's name and the naming of variables/classes used
// it's not necessarily clear that the loops/interactions used in this class
// are meant to run a simulation over time for specific simulation subject (in our case a building) -> nominal
public class Simulation {

    private final Building building;
    private final Terrain terrain;
    private final ArrayList<CostContainer> costsPerYear;
    private final ArrayList<Double> happinessPerYear;
    private double totalNumberOfRenovations;

    public Simulation(Building.Record buildingBlueprint, Terrain.Record terrainBlueprint) {
        this.building = new Building(buildingBlueprint);
        this.terrain = new Terrain(terrainBlueprint, new UrbanElement[]{building});
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

    private CostContainer renovateApartments(Apartment[] apartmentList){
        CostContainer costsThisYear = new CostContainer(0.0f, 0.0f, 0.0f);
        happinessPerYear.add(terrain.satisfaction());

        for (Apartment apartment : apartmentList){
            if (Math.random() < 1.0 / apartment.getLifetime()){
                costsThisYear.addCostContainer(apartment.renovate());
                totalNumberOfRenovations++;
            }
        }
        return costsThisYear;
    }

    private static Apartment[][] splitArray(Apartment[] original, int numParts) {
        int length = original.length;
        int partSize = length / numParts; // Base size of each part
        int remainder = length % numParts; // Extra elements that will go into the first few parts

        Apartment[][] subarrays = new Apartment[numParts][];

        int startIndex = 0;

        for (int i = 0; i < numParts; i++) {
            // If there are extra elements, add one more to the current subarray
            int currentPartSize = partSize + (i < remainder ? 1 : 0);

            // Create the subarray and copy elements
            subarrays[i] = new Apartment[currentPartSize];
            System.arraycopy(original, startIndex, subarrays[i], 0, currentPartSize);

            // Update the starting index for the next subarray
            startIndex += currentPartSize;
        }

        return subarrays;
    }

    // run the simulation with the parameters that have been specified in the objects initialization
    // nominal abstraction: behaviour of the simulation models what the assignment's text described
    public SimulationResult runSimulation(Catastrophe[] catastrophes) {
        while (building.checkAge()) {
            int numberThreads = 5;
            Apartment[] apartments = building.getApartments();
            int apartmentsPerThread = (int) Math.floor((double) apartments.length / numberThreads);
            // distribute all apartments on the different threads
            Apartment[][] apartmentsForAllThreads = splitArray(apartments, apartmentsPerThread);
            ExecutorService executor = Executors.newFixedThreadPool(apartmentsForAllThreads.length);

            // List to hold Future objects for each task
            List<Callable<CostContainer>> tasks = new ArrayList<>();

            // Loop through each set of apartments for each thread and create Callable tasks
            for (Apartment[] apartmentsOfThread : apartmentsForAllThreads) {
                tasks.add(() -> renovateApartments(apartmentsOfThread));
            }

            CostContainer costsThisYear = new CostContainer(0.0f, 0.0f, 0.0f);
            try {
                List<Future<CostContainer>> futures = executor.invokeAll(tasks);
                for (Future<CostContainer> future : futures) {
                    try {
                        costsThisYear.addCostContainer(future.get());  // This will block until the result is available
                    } catch (ExecutionException e) {
                        // Handle the exception if the task execution failed
                        System.err.println("Task execution failed: " + e.getCause().getMessage());
                    } catch (InterruptedException e) {
                        // Handle interruption (this may occur if the waiting thread is interrupted)
                        System.err.println("Thread was interrupted: " + e.getMessage());
                        Thread.currentThread().interrupt();  // Restore interrupt status
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            executor.shutdown();

            double randomVal = Math.random();
            // Sort the array by probability in ascending order
            Arrays.sort(catastrophes, Comparator.comparingDouble(Catastrophe::getProbability));
            // Checking for catastrophe occurrence and add the cost
            for (Catastrophe catastrophe : catastrophes) {
                if (randomVal < catastrophe.getProbability()) {
                    System.out.printf("\tEvent: %s happened in year %d%n", catastrophe.getEventName(), building.getAge());
                    if (catastrophe.getDamage() == 1.0) {
                        System.out.println("\tCritical Event. Demolishing.");
                        costsThisYear = costsThisYear.addCostContainer(terrain.demolishing());
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
            costsThisYear = costsThisYear.addCostContainer(terrain.age());
            costsPerYear.add(costsThisYear);
        }
        return new SimulationResult(costsPerYear, happinessPerYear, renovationRate());
    }
}
