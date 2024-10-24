package simulateBuildingSustainability.simulation.simulationSubject.costs;

public interface Measurements<T1, T2 extends Costs<T1>> {
    void resetTempTracker();
    void ReadTempTrackerToData();
    void addToTempTracker(T2 costs);
    void addCosts(T2 costs);
    // TODO way to access data
}
