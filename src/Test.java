/*
hier ueber die Aufteilung der Arbeit etc. schreiben

Julian - Apartments, ApartmentSpecs, MaterialBag

Marian - Main, Simulation, SimulationResult, BuildingSpecs

Hamed - CostContainer, Building, Material

 */

import java.rmi.UnexpectedException;

public class Test {

    public static void main(String[] args) throws UnexpectedException {
        Material[] materials = new Material[] {
                new Material("wood", new CostContainer(125.0, 0.4, 0.01)),
                new Material("concrete", new CostContainer(250.0, 0.8, 0.15)),
                new Material("steel", new CostContainer(1200.0, 1.4, 0.007))
        };
        double eventProbability = 0.05;
        Catastrophe[] catastrophes = new Catastrophe[]{
                new Catastrophe("Tornado", 0.3, eventProbability * 0.25),
                new Catastrophe("Flood", 0.2, eventProbability * 0.15),
                new Catastrophe("Earthquake", 0.5, eventProbability * 0.10),
                new Catastrophe("Structural Collapse", 1, eventProbability * 0.05),
                new Catastrophe("Minor Event", 0.1, eventProbability * 0.45),
        };

        int numberOfApartments = 10, lifetimeBuilding = 50, residentsPApartment = 1;
        MaterialBag shellEco = new MaterialBag(materials, new Double[] {257.3, 10.0, 2.45});
        MaterialBag  interiorEco = new MaterialBag(materials, new Double[] {14.4, 2.0, 0.875});
        CostContainer heatingAndMaintenanceCostsEco = new CostContainer(0.35, 0.3, 0.05);
        Apartment.Record apartmentsEco = new Apartment.Record(interiorEco, residentsPApartment, numberOfApartments, 20,0.9);
        Building.Record buildingEco = new Building.Record("test", lifetimeBuilding, shellEco, apartmentsEco, heatingAndMaintenanceCostsEco,0.7f);

        Simulation simulation = new Simulation(buildingEco);
        SimulationResult simResult = simulation.runSimulation(catastrophes);


        /* Calculate Lower and Upper Bounds for the costs of a building in simulation */
        CostContainer shellCosts = shellEco.getTotalCost();
        CostContainer interiorCosts = interiorEco.getTotalCost();

        CostContainer costLowerBound = shellCosts.addCostContainer(
                interiorCosts.multiplyContainer(numberOfApartments)
        );
        // divide by number of residents
        costLowerBound = costLowerBound.multiplyContainer((float) 1/(numberOfApartments*residentsPApartment*lifetimeBuilding));

        CostContainer costUpperBound = shellCosts.addCostContainer(
                interiorCosts.multiplyContainer(numberOfApartments*lifetimeBuilding)
        ).multiplyContainer((float) 1/numberOfApartments*residentsPApartment*lifetimeBuilding);

        if(costLowerBound.getCost() > simResult.getAverageCostOverLifetime()
                || costLowerBound.getCo2() > simResult.getAverageCo2OverLifetime()
                || costLowerBound.getWaste() > simResult.getAverageWasteOverLifetime()
        ) {
            throw new UnexpectedException("The results are too small.");
        }

        if(costUpperBound.getCost() < simResult.getAverageCostOverLifetime()
                || costUpperBound.getCo2() < simResult.getAverageCo2OverLifetime()
                || costUpperBound.getWaste() < simResult.getAverageWasteOverLifetime()
        ){
            throw new UnexpectedException("The results are too big.");
        }

        System.out.println("Tests successful!");

    }
}