package simulateBuildingSustainability;

import simulateBuildingSustainability.simulation.simulationSubject.costs.Costs;
import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultCosts;




// make test cases class of their own and give them the materials as arguments??

// how to copy buildings? -> method required by  interface? (how does that behave with inheritance?)
public class Building implements simulateBuildingSustainability.simulation.simulationSubject.SimulationSubject {

    private boolean readyToBeDemolished;
    private final Apartment[] apartments;

    Building(Apartment[] apartments) {
        readyToBeDemolished = false;
        this.apartments = apartments;
    }

    @Override
    public boolean continueSimulation() {
        return !readyToBeDemolished;
    }

    @Override
    public boolean stopSimulationEarly() {
        return !continueSimulation();
    }

    // Build building
    @Override
    public Costs<Double> executeInitialEvents() {
        return null;
    }

    // demolish building
    @Override
    public Costs<Double> executeFinalEvents() {
        return null;
    }

    @Override
    public Costs<Double> executeRandomEvents() {

        DefaultCosts randomEventsCosts = new DefaultCosts();

        // e.g. for 20 years 1/20th -> on average renovated every 20 years
        for (Apartment apartment : apartments)
            if (Math.random() < 1.0 / apartment.getLifetime()) {
                randomEventsCosts.addCosts(apartment.renovateApartment());
            }

        return randomEventsCosts;
    }

    @Override
    public Costs<Double> nextStep() {
        return null;
    }




}
