package simulation.simulationSubject.costs;

import java.util.ArrayList;

public class DefaultMeasurements implements Measurements<Double>{

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
    public void addInitialCosts(Costs<Double> costs) {
        if (measurements.isEmpty())
         measurements.add(costs);
        else {
            measurements.get(0).addCosts(costs);
        }
    }

    @Override
    public void addClosingCosts(Costs<Double> costs) {
        if (measurements.isEmpty())
            measurements.add(costs);
        else {
            measurements.get(measurements.size()-1).addCosts(costs);
        }
    }

    @Override
    public void addToTempTracker(Costs<Double> costs) {
        if (tracker.getKeySet().isEmpty()) {
            tracker.getCosts().forEach((k, v) -> costs.getCosts().put(k, v));
        }
        tracker.getCosts().forEach((k, v) -> costs.getCosts().merge(k, v, Double::sum));
    }
}
