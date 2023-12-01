import com.eclipsesource.json.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class FileReader {
    private static String file;
    private static String extension;

    public static String openExplorer() {
        // Open in working directory
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        int response = fileChooser.showOpenDialog(null);

        // Get absolute path to chosen file
        if (response == JFileChooser.APPROVE_OPTION) {
            File path = new File(fileChooser.getSelectedFile().getAbsolutePath());
            file = String.valueOf(path);
            return file;
        }
        return null;
    }

    // Get which type of file is chosen
    public static String getExtension() {
        if (file == null)
            return null;
        else {
            int i = file.lastIndexOf(".");
            if (i > 0)
                extension = file.substring(i + 1);
            return extension;
        }
    }

    // Reads the CSV-file and returns TableModel containing header and rows from file
    public static void readCSV() {
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            ArrayList<String> list = new ArrayList<>();
            String str = "";

            while ((str = br.readLine()) != null)
                list.add(str);

            // Create 2d array representing table row and column
            int n = list.get(0).split(",").length;
            Object[][] data = new Object[list.size()-1][n];

            // Loop through list and add each row to data, ignore first row since it will be represented in header
            for (int i = 0; i < list.size() - 1; i++)
                data[i] = list.get(i+1).split(",");

            // Stop reading the file
            br.close();

            // Get the header of CSV-file
            String[] header = list.get(0).split(",");
            // Remove first row since it's getting displayed in header
            list.remove(0);

            GUI.setTableModel(data, header);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readJSON() throws IOException {
        // Reads the file and adds the value to an array
        JsonArray array = Json.parse(new java.io.FileReader(file)).asArray();

        // Create JsonObject which contains first row then add column names to header
        JsonObject jsonObject = array.get(0).asObject();

        // Header grabs the column names from jsonObject and saves it in array
        String[] header = jsonObject.names().toArray(new String[0]);

        // Create 2d array representing rows and columns
        String[][] data = new String[array.size()][header.length];
        // Loop through Array to get each row and add it to data
        for (int i = 0; i < array.size(); i++) {
            jsonObject = array.get(i).asObject();
            for (int j = 0; j < header.length; j++)
                data[i][j] = jsonObject.get(header[j]).asString();
        }
        GUI.setTableModel(data, header);
    }
}
