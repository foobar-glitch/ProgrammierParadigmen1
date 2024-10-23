package simulateBuildingSustainability.simulation.simulationSubject.costs;

public interface Measurements<T> {
    void resetTempTracker();
    void ReadTempTrackerToData();
    void addInitialCosts(Costs<T> costs);
    void addClosingCosts(Costs<T> costs);
    void addToTempTracker(Costs<T> costs);
    // TODO way to access data
}
