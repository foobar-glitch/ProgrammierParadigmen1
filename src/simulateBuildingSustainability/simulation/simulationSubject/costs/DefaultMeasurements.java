package simulateBuildingSustainability.simulation.simulationSubject.costs;

import java.util.ArrayList;

public class DefaultMeasurements implements Measurements<Double, DefaultCosts>{

    ArrayList<Costs<Double>> measurements;
    private Costs<Double> tracker;

    public DefaultMeasurements() {
        measurements = new ArrayList<Costs<Double>>();
        tracker = new DefaultCosts();
    }

    @Override
    public void resetTempTracker() {
        tracker = new DefaultCosts();
    }

    @Override
    public void ReadTempTrackerToData() {
        measurements.add(tracker);
    }

    @Override
    public void addToTempTracker(DefaultCosts costs) {
        if (tracker.getKeySet().isEmpty()) {
            tracker.getCosts().forEach((k, v) -> costs.getCosts().put(k, v));
        }
        tracker.getCosts().forEach((k, v) -> costs.getCosts().merge(k, v, Double::sum));
    }

    @Override
    public void addCosts(DefaultCosts costs) {
        measurements.add(costs);
    };
}
