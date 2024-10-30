package simulateBuildingSustainability;

/*
hier ueber die Aufteilung der Arbeit etc. schreiben

Julian - Apartments, ApartmentSpecs, MaterialBag

Marian - Main, Simulation, SimulationResult, BuildingSpecs

Hamed - CostContainer, Building, Material

 */

import simulateBuildingSustainability.simulation.DefaultSimulation;
import simulateBuildingSustainability.simulation.DefaultSimulationResult;

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
        MaterialBag interiorEco = new MaterialBag(materials, new Double[] {14.4, 2.0, 0.875});
        CostContainer heatingAndMaintenanceCostsEco = new CostContainer(0.35, 0.3, 0.05);
        ApartmentSpecs apartmentsEco = new ApartmentSpecs(interiorEco, residentsPApartment, numberOfApartments, 20,0.9);
        BuildingSpecs buildingEco = new BuildingSpecs(lifetimeBuilding, shellEco, apartmentsEco, heatingAndMaintenanceCostsEco,0.7f);

        SustainabilitySimulation simulation = new SustainabilitySimulation(new Building(buildingEco), catastrophes);
        DefaultSimulationResult simResult = simulation.runSimulation();


        /* Calculate Lower and Upper Bounds for the costs of a building in simulation */
        CostContainer shellCosts = shellEco.getTotalCost();
        CostContainer interiorCosts = interiorEco.getTotalCost();

        // TODO could be written easier if add/multiply would return costs
        CostContainer costLowerBound = new CostContainer();
        costLowerBound.addCosts(interiorCosts);
        costLowerBound.multiplyCosts(numberOfApartments);
        costLowerBound.addCosts(shellCosts);

        // divide by number of residents
        costLowerBound.multiplyCosts(1.0/(numberOfApartments*residentsPApartment*lifetimeBuilding));

        CostContainer costUpperBound = new CostContainer();
        costUpperBound.addCosts(interiorCosts);
        costUpperBound.multiplyCosts(numberOfApartments*lifetimeBuilding);
        costUpperBound.addCosts(shellCosts);
        costUpperBound.multiplyCosts(1.0/numberOfApartments*residentsPApartment*lifetimeBuilding);

        if(costLowerBound.getMoney() > simResult.getAverageCostOverLifetime()
                || costLowerBound.getCo2() > simResult.getAverageCo2OverLifetime()
                || costLowerBound.getWaste() > simResult.getAverageWasteOverLifetime()
        ) {
            throw new UnexpectedException("The results are too small.");
        }

        if(costUpperBound.getMoney() < simResult.getAverageCostOverLifetime()
                || costUpperBound.getCo2() < simResult.getAverageCo2OverLifetime()
                || costUpperBound.getWaste() < simResult.getAverageWasteOverLifetime()
        ){
            throw new UnexpectedException("The results are too big.");
        }

        System.out.println("Tests successful!");

    }
}