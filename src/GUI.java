import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI  extends JFrame implements ActionListener {

    JButton button;
    public GUI() {
        super("Test");
        setSize(1080, 640);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        button = new JButton("Select file");
        button.addActionListener(this);

        // tableview

        add(button);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File path = new File(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }
}
