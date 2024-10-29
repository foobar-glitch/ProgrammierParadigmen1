package simulateBuildingSustainability.simulation.simulationSubject.costs;

import java.util.ArrayList;

public class DefaultMeasurements implements Measurements{

    ArrayList<Costs> measurements;
    private Costs tracker;

    public DefaultMeasurements() {
        measurements = new ArrayList<Costs>();
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
    public void addInitialCosts(Costs costs) {
        if (measurements.isEmpty())
         measurements.add(costs);
        else {
            measurements.get(0).addCosts(costs);
        }
    }

    @Override
    public void addClosingCosts(Costs costs) {
        if (measurements.isEmpty())
            measurements.add(costs);
        else {
            measurements.get(measurements.size()-1).addCosts(costs);
        }
    }

    @Override
    public void addToTempTracker(Costs costs) {
        if (tracker.getKeySet().isEmpty()) {
            tracker.getCosts().forEach((k, v) -> costs.getCosts().put(k, v));
        }
        tracker.getCosts().forEach((k, v) -> costs.getCosts().merge(k, v, Double::sum));
    }
}
