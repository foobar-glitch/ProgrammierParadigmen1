import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Database {

    private static final String folderRoot = "src/ressources";
    private static final String filenameCatastrophes = "Catastrophes.csv";
    private static final String filenameBuildingMaterials = "BuildingMaterials.csv";

    private static String[][] readCsv(String path, String[] headers) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            ArrayList<String[]> entries = new ArrayList<String[]>();
            String fieldNames = bufferedReader.readLine();
            if (fieldNames != null) {
                String[] names = fieldNames.split(", ");
                if (names.length != headers.length) {
                    throw new UnsupportedEncodingException(path + " in wrong format (wrong amount of headers)");
                }
                for (int i = 0; i < names.length; i++) {
                    if (!names[i].equals(headers[i])) {
                        throw new UnsupportedEncodingException(path + " in wrong format (wrong headers)");
                    }
                }
                for (String line; (line = bufferedReader.readLine()) != null; ) {
                    String[] values = line.split(", ");
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
            throw new RuntimeException("File at: " + path + " does not exist");
        }
    }

    public static Material[] readOutAllMaterials() {
        String path = folderRoot + "/" + filenameBuildingMaterials;
        String[] headers = {"(String) materialName", "(double) money", "(double) co2",  "(double) waste"};
        String[][] entries = readCsv(path, headers);
        ArrayList<Material> buildingMaterials = new ArrayList<Material>();
        for (String[] entry : entries) {
            String name = entry[0];
            double money = Double.parseDouble(entry[1]);
            double co2 = Double.parseDouble(entry[2]);
            double waste = Double.parseDouble(entry[3]);
            buildingMaterials.add(new Material(name, new CostContainer(money, co2, waste)));
        }
        return buildingMaterials.toArray(new Material[0]);
    }


    public static Catastrophe[] readOutAllCatastrophes(double eventProbability) {
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

