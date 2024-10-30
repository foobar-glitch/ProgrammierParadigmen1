package simulateBuildingSustainability;

import simulateBuildingSustainability.simulation.DefaultSimulation;
import simulateBuildingSustainability.simulation.simulationSubject.costs.Costs;
import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultCosts;

import java.util.Arrays;
import java.util.Comparator;

// uses logic in parent class to run simulation
// this class only implements the specific actions that are done in each corresponding step of the simulation
public class SustainabilitySimulation extends DefaultSimulation<Building> {

    private Catastrophe[] catastrophes;

    public SustainabilitySimulation(Building building, Catastrophe[] catastrophes) {
        super(building);
        this.catastrophes = catastrophes;
    }

    @Override
    protected boolean continueSimulation() {
        return subject.getReadyToBeDemolished();
    }

    @Override
    protected boolean exitSimulationEarly() {
        return !continueSimulation();
    }

    @Override
    protected Costs initialCosts() {
        return subject.build();
    }

    @Override
    protected Costs closingCosts() {
        return subject.demolish();
    }

    @Override
    protected Costs executeRandomEvents() {
        Costs costs = new DefaultCosts();
        for (Apartment apartment: subject.getApartments()) {
            if (Math.random() < 1.0/apartment.getLifetime()) {
                costs.addCosts(apartment.renovate());
            }
        }
        // TODO catastrophes

        //double randomVal = Math.random();
        //// Sort the array by probability in ascending order
        //Arrays.sort(catastrophes, Comparator.comparingDouble(Catastrophe::getProbability));
        //// Checking for catastrophe occurrence and add the cost
        //for(Catastrophe catastrophy: catastrophes){
        //    if(randomVal < catastrophy.getProbability()){
        //        System.out.printf("Event: %s happened%n", catastrophy.getEventName());
        //        if(catastrophy.getDamage() == 1.0f){
        //            System.out.println("Critical Event. Demolishing.");
        //            costsThisYear = costsThisYear.addCostContainer(building.demolishing());
        //            costsPerYear.add(costsThisYear);
        //            return new SimulationResult(costsPerYear, happinessPerYear);
        //        }
        //        costsThisYear = costsThisYear.addCostContainer(building.renovate(1 - catastrophy.getDamage()));
        //        // There can only be one event when using break
        //        // Otherwise 0.1 would always trigger both 0.2 and 0.3
        //        break;
        //    }
        //}


        return costs;
    }

    @Override
    protected Costs incrementSimulation() {
        // TODO logic for how long the simulation should run, here
        // only access state of building
        // set building ready to demolish here

        return null;
    }
}
