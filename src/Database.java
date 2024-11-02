import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

// TODO maybe would be better if not static? better as a singleton?
public class Database {

    private static final String folderRoot = "src/ressources";
    private static final String filenameCatastrophes = "Catastrophes.csv";
    private static final String filenameMaterials = "Materials.csv";
    private static final String filenameBuildingMaterials = "BuildingMaterials.csv";

    private String[][] readCsv(String path, String[] headers) {
        // TODO check for correct types?
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            ArrayList<String[]> entries = new ArrayList<String[]>();
            String fieldNames = bufferedReader.readLine();
            if (fieldNames != null) {
                String[] names = fieldNames.split("; ");
                if (names.length != headers.length) {
                    throw new UnsupportedEncodingException(path + " in wrong format (wrong amount of headers)");
                }
                for (int i = 0; i < names.length; i++) {
                    if (!names[i].equals(headers[i])) {
                        throw new UnsupportedEncodingException(path + " in wrong format (wrong headers)");
                    }
                }
                for (String line; (line = bufferedReader.readLine()) != null; ) {
                    String[] values = line.split("; ");
                    if (values.length != names.length) {
                        throw new UnsupportedEncodingException(path + " in wrong format: " + line);
                    }
                    entries.add(values);
                }
            } else {
                throw new UnsupportedEncodingException(path + " headers are empty");
            }
            return entries.toArray(new String[0][0]);
        } catch (IOException e) {
            throw new RuntimeException("Filepath: " + path + " ,Error: " + e);
        }
    }

    public Material[] readOutAllMaterials() {
        String path = folderRoot + "/" + filenameMaterials;
        String[] headers = {"(String) materialName", "(double) money", "(double) co2",  "(double) waste"};
        String[][] entries = readCsv(path, headers);
        ArrayList<Material> materials = new ArrayList<Material>();
        for (String[] entry : entries) {
            String name = entry[0];
            double money = Double.parseDouble(entry[1]);
            double co2 = Double.parseDouble(entry[2]);
            double waste = Double.parseDouble(entry[3]);
            materials.add(new Material(name, new CostContainer(money, co2, waste)));
        }
        return materials.toArray(new Material[0]);
    }

    // TODO better to have DB have a state and save all materials in a HashMap?
    public Material readOutSingleMaterial(String materialName) {
        String path = folderRoot + "/" + filenameMaterials;
        String[] headers = {"(String) materialName", "(double) money", "(double) co2",  "(double) waste"};
        String[][] entries = readCsv(path, headers);
        ArrayList<Material> materials = new ArrayList<Material>();
        for (String[] entry : entries) {
            if (entry[0].equals(materialName)) {
                String name = entry[0];
                double money = Double.parseDouble(entry[1]);
                double co2 = Double.parseDouble(entry[2]);
                double waste = Double.parseDouble(entry[3]);
                return new Material(name, new CostContainer(money, co2, waste));
            }
        }
        throw new RuntimeException("Material  " + materialName + " could not be found in file " + filenameMaterials);
    }

    public MaterialBag[] readOutAllBuildingMaterials() {
        String path = folderRoot + "/" + filenameBuildingMaterials;
        String[] headers = {"(String) buildingMaterialsName", "(String[]) materials", "(double[]) amounts"};
        String[][] entries = readCsv(path, headers);
        ArrayList<MaterialBag> buildingMaterials = new ArrayList<MaterialBag>();
        for (String[] entry : entries) {
            String name = entry[0];
            // written as array with brackets
            String[] materialNames = entry[1].replaceAll("[{|}]", "").split(", ");
            ArrayList<Material> materials = new ArrayList<Material>();
            for (String materialName : materialNames) {
                materials.add(readOutSingleMaterial(materialName));
            }
            // TODO stream Strings to double???
            String[] amountStrings = entry[2].replaceAll("[{|}]", "").split(", ");
            Double[] amounts = new Double[amountStrings.length];
            for (int i = 0; i < amountStrings.length; i++) {
                amounts[i] = Double.parseDouble(amountStrings[i]);
            }
            buildingMaterials.add(new MaterialBag(materials.toArray(new Material[0]), amounts));
        }
        return buildingMaterials.toArray(new MaterialBag[0]);
    }

    // TODO better to have DB have a state and save all materials in a HashMap?
    public MaterialBag readOutSingleBuildingMaterial(String BuildingMaterialsName) {
        String path = folderRoot + "/" + filenameBuildingMaterials;
        String[] headers = {"(String) buildingMaterialsName", "(String[]) materials", "(double[]) amounts"};
        String[][] entries = readCsv(path, headers);
        for (String[] entry : entries) {
            if (entry[0].equals(BuildingMaterialsName)) {
                String name = entry[0];
                // written as array with brackets
                String[] materialNames = entry[1].replaceAll("[{|}]", "").split(", ");
                ArrayList<Material> materials = new ArrayList<Material>();
                for (String materialName : materialNames) {
                    materials.add(readOutSingleMaterial(materialName));
                }
                // TODO stream Strings to double???
                String[] amountStrings = entry[2].replaceAll("[{|}]", "").split(", ");
                Double[] amounts = new Double[amountStrings.length];
                for (int i = 0; i < amountStrings.length; i++) {
                    amounts[i] = Double.parseDouble(amountStrings[i]);
                }
                return new MaterialBag(materials.toArray(new Material[0]), amounts);
            }
        }


        throw new RuntimeException("Material  " + BuildingMaterialsName + " could not be found in file " + filenameMaterials);
    }

    public Catastrophe[] readOutAllCatastrophes(double eventProbability) {
        String path = folderRoot + "/" + filenameCatastrophes;
        String[] headers = {"(String) eventName", "(double) damage", "(double) probability"};
        String[][] entries = readCsv(path, headers);
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

