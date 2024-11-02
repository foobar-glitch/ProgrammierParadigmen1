// TODO enable configuration of different kinds of Apartment in the same building?
// TODO separate recycle rates for shell and interior?

public record BuildingBlueprint(
        String name,
        MaterialBag shellMaterials,
        int lifetime,
        CostContainer heatingAndMaintenanceCost,
        double recycleRate,
        MaterialBag interiorMaterials,
        int numberOfApartments,
        int numberOfApartmentResidents,
        int lifetimeOfApartments,
        double happinessUpperBounds
) {}
