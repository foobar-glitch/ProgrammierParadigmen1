/*
hier ueber die Aufteilung der Arbeit etc. schreiben

Julian - Apartments, ApartmentSpecs, MaterialBag

Marian - Main, Simulation, SimulationResult, BuildingSpecs

Hamed - CostContainer, Building, Material

 */

import java.rmi.UnexpectedException;

public class Test {

    public static void main(String[] args) throws UnexpectedException {
        Main mainFunction = new Main();
        Material[] materials = new Material[] {
                new Material("wood", new CostContainer(125.0, 0.4, 0.01)),
                new Material("concrete", new CostContainer(250.0, 0.8, 0.15)),
                new Material("steel", new CostContainer(1200.0, 1.4, 0.007))
        };
        int numberOfApartments = 10, lifetimeBuilding = 50, residentsPApartment = 1;
        MaterialBag shellEco = new MaterialBag(materials, new Double[] {257.3, 10.0, 2.45});
        MaterialBag  interiorEco = new MaterialBag(materials, new Double[] {14.4, 2.0, 0.875});
        CostContainer heatingAndMaintenanceCostsEco = new CostContainer(0.35, 0.3, 0.05);
        ApartmentSpecs apartmentsEco = new ApartmentSpecs(interiorEco, residentsPApartment, numberOfApartments, 20,0.9);
        BuildingSpecs buildingEco = new BuildingSpecs(lifetimeBuilding, shellEco, apartmentsEco, heatingAndMaintenanceCostsEco,0.7f);

        Simulation simulation = new Simulation(buildingEco, apartmentsEco);
        SimulationResult simResult = simulation.runSimulation();


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