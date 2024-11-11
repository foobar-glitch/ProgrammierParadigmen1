/*

Aufgabe 1:
Julian - Apartments, ApartmentSpecs, MaterialBag
Marian - Main, Simulation, SimulationResult, BuildingSpecs
Hamed - CostContainer, Building, Material

Aufgabe 2:
Marian had the awesome idea of using a Kanban Board on Trello to collect and evaluate ideas of what we could implement
next. We met two times in person to talk about our ideas and possible implementations (if more than one person was affected).

Julian - UrbanElement Interface, Terrain, Architecture, (little changes in Main, Test and Simulation to adapt to records and terrain-class), (Database readOutAllTerrainBlueprints())
Marian - simplified Test/Main and console outputs, created Database, small changes Simulation , BuildingBlueprints/BuildingMaterials/Catastrophes/Materials CSV-Files, "STYLE"-Comments
Hamed - Catastrophe (probabilities, example cases and calculations)

Julian - Functional Implementations MaterialBag and Comments and Planning

 */

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Database db = new Database();
        Building.Record[] testCases = db.readOutAllBuildingBlueprints();

        // Can be changed to simulate different Terrains
        Terrain.Record[] terrainRecords = new Terrain.Record[testCases.length];
        Terrain.Record simulationTerrain = new Database().readOutAllTerrainBlueprints()[0];
        for(int i=0; i < testCases.length; i++){
            terrainRecords[i] = simulationTerrain;
        }

        double eventProbability = 0.05;
        Catastrophe[] catastrophes = db.readOutAllCatastrophes(eventProbability);

        for (int i = 0; i < testCases.length; i++) {
            System.out.printf("---TEST CASE %d %s---%n", i + 1, testCases[i].name());
            System.out.println();
            // ten simulations per case
            ArrayList<SimulationResult> results = new ArrayList<SimulationResult>();
            for (int j = 0; j < 10; j++) {
                System.out.printf("Simulation%d:%n", j + 1);
                Simulation simulation = new Simulation(testCases[i], terrainRecords[i]);
                results.add(simulation.runSimulation(catastrophes));
                System.out.printf("\tNachhaltigkeits-Score: %f%n", results.get(j).getSustainabilityScore());
                System.out.printf("\trenovationRate: %.2f%%%n", results.get(j).getRenovationRate()*100.0);
            }

            results.sort((r1, r2) -> (int) Math.signum(r1.getSustainabilityScore() - r2.getSustainabilityScore()));
            SimulationResult medianResult = results.get(results.size()/2 + 1);
            System.out.println();
            System.out.printf("Alle Kennzahlen fuer den Simulationsdurchlauf mit dem Median-Nachhaltigkeits-Score:%n");
            System.out.printf("\tNachhaltigkeits-Score: %f%n", medianResult.getSustainabilityScore());
            System.out.printf("\trenovationRate: %.2f%%%n", medianResult.getRenovationRate()*100.0);
            System.out.printf("\taverageCostOverLifetime: %f%n", medianResult.getAverageCostOverLifetime());
            System.out.printf("\taverageCo2OverLifetime: %f%n", medianResult.getAverageCo2OverLifetime());
            System.out.printf("\taverageWasteOverLifetime: %f%n", medianResult.getAverageWasteOverLifetime());
            System.out.println("\taverage cost per decade:");
            for (int j = 0; j < medianResult.getAverageCostPerDecade().size(); j++) {
                System.out.printf("\t\tDecade%d: %f%n", j + 1, medianResult.getAverageCostPerDecade().get(j));
            }
            System.out.println("\taverage happiness per decade:");
            for (int j = 0; j < medianResult.getAverageCostPerDecade().size(); j++) {
                System.out.printf("\t\tDecade%d: %f%n", j + 1, medianResult.getAverageHappinessPerDecade().get(j));
            }
            System.out.println();
        }
    }
}