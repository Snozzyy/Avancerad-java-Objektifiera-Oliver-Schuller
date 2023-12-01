import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GUI  extends JFrame implements ActionListener {

    private JTextField showPath;
    private JButton chooseFile;
    private JButton showFile;
    private JPanel tablePanel;
    private static JTable table;


    public GUI() {
        super("Objektivering");
        setSize(1080, 640);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel footerPanel = new JPanel();
        tablePanel = new JPanel();

        table = new JTable();
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 520));
        tablePanel.add(scrollPane);

        showPath = new JTextField(30);
        footerPanel.add(showPath);

        chooseFile = new JButton("Choose file");
        chooseFile.addActionListener(this);
        footerPanel.add(chooseFile);

        showFile = new JButton("Show in table");
        showFile.addActionListener(this);
        footerPanel.add(showFile);

        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check which button is used
        if (e.getSource() == chooseFile)
            showPath.setText(FileReader.openExplorer());
        else if (e.getSource() == showFile)
            // Checks the file type and runs correct function accordingly to the type
            if (FileReader.getExtension() == null)
                JOptionPane.showMessageDialog(null, "Please choose a file");
            else if (FileReader.getExtension().equals("csv"))
                FileReader.readCSV();
            else if (FileReader.getExtension().equals("json")) {
                try {
                    FileReader.readJSON();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            // If file type is not supported, display message
            else
                JOptionPane.showMessageDialog(null,"Unsupported file type, please choose JSON " +
                        "or CSV");

    }

    // sets TableModel
    public static void setTableModel(Object[][] data, String[] header) {
        // Set header, loop through data and add each row
        DefaultTableModel tableModel = new DefaultTableModel(header, 0);
        for (Object[] datum : data) {
            tableModel.addRow(datum);
        }
        table.setModel(tableModel);
    }
}
