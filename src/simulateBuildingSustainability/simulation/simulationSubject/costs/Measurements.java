package simulateBuildingSustainability.simulation.simulationSubject.costs;

public interface Measurements {
    void resetTempTracker();
    void ReadTempTrackerToData();
    void addInitialCosts(Costs costs);
    void addClosingCosts(Costs costs);
    void addToTempTracker(Costs costs);
    // TODO way to access data
}
