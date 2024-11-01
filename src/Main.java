import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Material[] materials = new Material[] {
                new Material("wood", new CostContainer(125.0, 0.4, 0.01)),
                new Material("concrete", new CostContainer(250.0, 0.8, 0.15)),
                new Material("steel", new CostContainer(1200.0, 1.4, 0.007))
        };

        /*
        * Assuming that we have 10 apartments with each 50 sqm.
        * */
        MaterialBag shellMinimal = new MaterialBag(materials, new Double[] {216.0, 30.0, 3.75});
        MaterialBag  interiorMinimal = new MaterialBag(materials, new Double[] {14.4, 2.0, 0.875});
        CostContainer heatingAndMaintenanceCostsMinimal = new CostContainer(0.35, 0.6, 0.05);
        Apartment.Record apartmentsMinimal = new Apartment.Record(interiorMinimal, 1, 100, 20 ,0.8);
        Building.Record buildingMinimal = new Building.Record(50, shellMinimal, apartmentsMinimal, heatingAndMaintenanceCostsMinimal, 0.5f);

        MaterialBag shellEco = new MaterialBag(materials, new Double[] {257.3, 10.0, 2.45});
        MaterialBag  interiorEco = new MaterialBag(materials, new Double[] {14.4, 2.0, 0.875});
        CostContainer heatingAndMaintenanceCostsEco = new CostContainer(0.35, 0.3, 0.05);
        Apartment.Record apartmentsEco = new Apartment.Record(interiorEco, 1, 100, 20,0.9);
        Building.Record buildingEco = new Building.Record(50, shellEco, apartmentsEco, heatingAndMaintenanceCostsEco,0.7f);

        MaterialBag shellHighEnd = new MaterialBag(materials, new Double[] {382.3, 48.6, 5.35});
        MaterialBag  interiorHighEnd = new MaterialBag(materials, new Double[] {12.3, 1.9, 1.35});
        CostContainer heatingAndMaintenanceHighEnd = new CostContainer(0.37, 0.32, 0.05);
        Apartment.Record apartmentsHighEnd = new Apartment.Record(interiorHighEnd, 1, 100, 25,1.0);
        Building.Record buildingHighEnd = new Building.Record(100, shellHighEnd, apartmentsHighEnd, heatingAndMaintenanceHighEnd,0.5f);

        String[] namesTestCases = {
                "MINIMAL",
                "OEKOLOGISCH",
                "HOCHWERTIG"};
        Building.Record[] buildingsTestConfigs = new Building.Record[] {
                buildingMinimal,
                buildingEco,
                buildingHighEnd};
        Apartment.Record[] interiorsTestConfigs = new Apartment.Record[] {
                apartmentsMinimal,
                apartmentsEco,
                apartmentsHighEnd
        };
        double eventProbability = 0.05;
        Catastrophe[] catastrophes = Catastrophe.readCatastrophesFromFile("src/ressources/Catastrophes.csv", eventProbability);

        for (int i = 0; i < buildingsTestConfigs.length; i++) {
            System.out.printf("---TEST CASE %d %s---%n", i + 1, namesTestCases[i]);
            System.out.println();
            // ten simulations per case
            ArrayList<SimulationResult> results = new ArrayList<SimulationResult>();
            for (int j = 0; j < 10; j++) {
                System.out.printf("Simulation%d:%n", j + 1);
                Simulation simulation = new Simulation(buildingsTestConfigs[i], interiorsTestConfigs[i]);
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