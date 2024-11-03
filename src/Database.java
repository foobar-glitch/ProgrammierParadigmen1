import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// TODO map headers to indices and access data that way?
// TODO e.g. entry[map("name")] instead of entry[0]
public class Database {

    private static final String folderRoot = "src/ressources";
    private static final String filenameMaterials = "Materials.csv";
    private static final String[] materialsHeaders =
            {"(String) materialName", "(double) money", "(double) co2", "(double) waste"};
    private static final String filenameBuildingMaterials = "BuildingMaterials.csv";
    private static final String[] buildingMaterialsHeaders =
            {"(String) buildingMaterialsName", "(String[]) materials", "(double[]) amounts"};
    private static final String filenameBuildingBlueprints = "BuildingBlueprints.csv";
    private static final String[] buildingBlueprintsHeaders =
            {"(String) buildingName", "(String) terrain", "(String) architecture", "(int) buildingLifetime",
                    "(MaterialBag) shellMaterials", "(CostContainer) heatingAndMaintenanceCosts", "(double) recycleRate",
                    "(int) numberOfApartments", "(MaterialBag) interiorMaterials", "(int) lifetimeApartment",
                    "(int) residentNumberApartment", "(double) happinessUpperBound"};
    private static final String filenameCatastrophes = "Catastrophes.csv";
    private static final String[] catastrophesHeaders =
            {"(String) eventName", "(double) damage", "(double) probability"};

    private Material[] allMaterialsArray = null;
    private final HashMap<String, Material> allMaterialsMap = new HashMap<String, Material>();
    private MaterialBag[] allBuildingMaterialsArray = null;
    private final HashMap<String, MaterialBag> allBuildingMaterialsMap = new HashMap<String, MaterialBag>();

    private String[][] readCsv(String path, String[] headers) {
        // TODO check for correct types?
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            ArrayList<String[]> entries = new ArrayList<String[]>();
            String fieldNames = bufferedReader.readLine();
            if (fieldNames != null) {
                String[] names = fieldNames.split(";");
                if (names.length != headers.length) {
                    throw new UnsupportedEncodingException("wrong format (wrong amount of headers, expected "
                            + String.valueOf(headers.length) + " got, " + String.valueOf(names.length) + ")");
                }
                for (int i = 0; i < names.length; i++) {
                    if (!names[i].equals(headers[i])) {
                        throw new UnsupportedEncodingException("wrong format (wrong headers)");
                    }
                }
                for (String line; (line = bufferedReader.readLine()) != null; ) {
                    String[] values = line.split(";");
                    if (values.length != names.length) {
                        throw new UnsupportedEncodingException("wrong format: " + line);
                    }
                    entries.add(values);
                }
            } else {
                throw new UnsupportedEncodingException("headers are empty");
            }
            return entries.toArray(new String[0][0]);
        } catch (IOException e) {
            throw new RuntimeException("Filepath: " + path + ", Error: " + e);
        }
    }

    private Double[] parseDoubleArray(String arrayAsString) {
        return Arrays.stream(arrayAsString.replaceAll("[{|}]", "").split(", "))
                .map(Double::parseDouble)
                .toArray(Double[]::new);
    }

    public Material[] readOutAllMaterials() {
        if (allMaterialsArray == null) {
            String path = folderRoot + "/" + filenameMaterials;
            String[][] entries = readCsv(path, materialsHeaders);
            ArrayList<Material> materials = new ArrayList<Material>();
            for (String[] entry : entries) {
                String name = entry[0];
                double money = Double.parseDouble(entry[1]);
                double co2 = Double.parseDouble(entry[2]);
                double waste = Double.parseDouble(entry[3]);
                Material material = new Material(name, new CostContainer(money, co2, waste));
                allMaterialsMap.put(name, material);
                materials.add(material);
            }
            allMaterialsArray = materials.toArray(new Material[0]);
        }
        // return copy
        return Arrays.stream(allMaterialsArray)
                .map(material -> material == null ? null : material.copy())
                .toArray(Material[]::new);
    }

    public Material readOutSingleMaterial(String materialName) {
        if (allMaterialsMap.isEmpty()) {
            readOutAllMaterials();
        }
        if (allMaterialsMap.get(materialName) != null) {
            // return copy
            return allMaterialsMap.get(materialName);
        } else {
            throw new RuntimeException("Material  " + materialName + " could not be found in file " + filenameMaterials);
        }
    }

    public MaterialBag[] readOutAllBuildingMaterials() {
        if (allBuildingMaterialsArray == null) {
            String path = folderRoot + "/" + filenameBuildingMaterials;
            String[][] entries = readCsv(path, buildingMaterialsHeaders);
            ArrayList<MaterialBag> buildingMaterials = new ArrayList<MaterialBag>();
            for (String[] entry : entries) {
                String name = entry[0];
                // written as array with brackets
                Material[] materials = Arrays.stream(entry[1].replaceAll("[{|}]", "").split(", "))
                        .map(this::readOutSingleMaterial)
                        .toArray(Material[]::new);
                // written as array with brackets
                Double[] amounts = parseDoubleArray(entry[2]);
                MaterialBag buildingMaterial = new MaterialBag(materials, amounts);
                allBuildingMaterialsMap.put(name, buildingMaterial);
                buildingMaterials.add(buildingMaterial);
            }
            allBuildingMaterialsArray = buildingMaterials.toArray(new MaterialBag[0]);
        }
        // return copy
        return Arrays.stream(allBuildingMaterialsArray)
                .map(buildingMaterial -> buildingMaterial == null ? null : buildingMaterial.copy())
                .toArray(MaterialBag[]::new);
    }

    public MaterialBag readOutSingleBuildingMaterial(String BuildingMaterialsName) {
        if (allBuildingMaterialsMap.isEmpty()) {
            readOutAllBuildingMaterials();
        }
        if (allBuildingMaterialsMap.get(BuildingMaterialsName) != null) {
            // return copy
            return allBuildingMaterialsMap.get(BuildingMaterialsName).copy();
        } else {
            throw new RuntimeException("Material  " + BuildingMaterialsName + " could not be found in file " + filenameBuildingMaterials);
        }
    }

    public Building.Record[] readOutAllBuildingBlueprints() {
        String path = folderRoot + "/" + filenameBuildingBlueprints;
        String[][] entries = readCsv(path, buildingBlueprintsHeaders);
        ArrayList<Building.Record> buildingBlueprints = new ArrayList<Building.Record>();
        for (String[] entry : entries) {
            String name = entry[0];
            String terrain = entry[1];
            String architecture = entry[2];
            int buildingLifetime = Integer.parseInt(entry[3]);
            MaterialBag shellMaterials = readOutSingleBuildingMaterial(entry[4]);
            // TODO better way to split up array?
            CostContainer heatingAndMaintenanceCosts = new CostContainer(
                    parseDoubleArray(entry[5])[0],
                    parseDoubleArray(entry[5])[1],
                    parseDoubleArray(entry[5])[2]);
            double recycleRate = Double.parseDouble(entry[6]);
            int numberOfApartments = Integer.parseInt(entry[7]);
            MaterialBag interiorMaterials = readOutSingleBuildingMaterial(entry[8]);
            int lifetimeApartment = Integer.parseInt(entry[9]);
            int residentNumberApartment = Integer.parseInt(entry[10]);
            double happinessUpperBound = Double.parseDouble(entry[11]);
            Building.Record buildingBlueprint = new Building.Record(
                    name,
                    buildingLifetime,
                    shellMaterials,
                    new Apartment.Record(
                            interiorMaterials,
                            residentNumberApartment,
                            numberOfApartments,
                            lifetimeApartment,
                            happinessUpperBound
                    ),
                    heatingAndMaintenanceCosts,
                    recycleRate
            );
            buildingBlueprints.add(buildingBlueprint);
        }
        return buildingBlueprints.toArray(new Building.Record[0]);
    }

    public Catastrophe[] readOutAllCatastrophes(double eventProbability) {
        String path = folderRoot + "/" + filenameCatastrophes;
        String[][] entries = readCsv(path, catastrophesHeaders);
        ArrayList<Catastrophe> catastrophes = new ArrayList<Catastrophe>();
        for (String[] entry : entries) {
            String name = entry[0];
            double damage = Double.parseDouble(entry[1]);
            double probability = Double.parseDouble(entry[2]) * eventProbability;
            catastrophes.add(new Catastrophe(name, damage, probability));
        }
        return catastrophes.toArray(new Catastrophe[0]);
    }
}

