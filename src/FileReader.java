import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FileReader {

    private static String file;
    private static String extension;
    static String[] header;
    static Object[][] data;

    public FileReader() {

    }

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

    // Reads the CSV-file and returns TableModel containing header and rows from file
    public static void readCSV() {
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            ArrayList<String> list = new ArrayList<>();
            String str = "";

            while ((str = br.readLine()) != null){
                list.add(str);
                //System.out.println(str);
            }

            // Create 2d array representing table row and column
            int n = list.get(0).split(",").length;
            data = new Object[list.size()][n];

            for (int i = 0; i < list.size(); i++) {
                data[i] = list.get(i).split(",");
            }
            // Stop read the file
            br.close();

            // Get the header of CSV-file
            header = list.get(0).split(",");

            GUI.setTableModel(data, header);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get which type of file is chosen
    public static String getExtension() {
        int i = file.lastIndexOf(".");
        if (i > 0) {
            extension = file.substring(i + 1);
        }
        return extension;
    }
}
