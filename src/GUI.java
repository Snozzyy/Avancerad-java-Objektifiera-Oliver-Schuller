import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(table);
        scrollPane.setSize(1000, 520);
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
        if (e.getSource() == chooseFile)
            showPath.setText(FileReader.openExplorer());
        else if (e.getSource() == showFile);
            if (FileReader.getExtension().equals("csv"))
                FileReader.readCSV();

    }

    public static void setTableModel(Object[][] data, String[] header) {

        DefaultTableModel tableModel = new DefaultTableModel(header, 0);
        for (int i = 1; i < data.length; i++) {
            tableModel.addRow(data[i]);
        }
        table.setModel(tableModel);
        
    }
}
