package simulateBuildingSustainability;

import simulateBuildingSustainability.simulation.simulationSubject.costs.Costs;
import simulateBuildingSustainability.simulation.simulationSubject.costs.DefaultCosts;

public class Apartment {

    private int lifeTime;

    public Costs renovate() {
        // TODO implement
        return new DefaultCosts();
    }

    public int getLifetime() {
        return lifeTime;
    }
}
