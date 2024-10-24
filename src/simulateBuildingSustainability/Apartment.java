package simulateBuildingSustainability;

import simulateBuildingSustainability.simulation.simulationSubject.costs.Costs;
import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultCosts;

public class Apartment {

    double getLifetime() {return 20.0;}
    DefaultCosts renovateApartment() {return new DefaultCosts();}
}
