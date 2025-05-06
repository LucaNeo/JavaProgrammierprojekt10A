package src;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    public GridPanel(JFrame frame) {
        setLayout(new BoxLayout(this ,BoxLayout.Y_AXIS));
        setBackground(new Color(30,30,30));


        JLabel title = new JLabel("Grid Panel");
        title.setForeground(Color.white);
        title.setFont(title.getFont().deriveFont(Font.BOLD));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentY(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(30,0,20,0 ));
        add(title);

        // Spinner für Zeilen und Spalten -> Erklärung // https://docs.oracle.com/en/java/javase/24/docs/api/java.desktop/javax/swing/JSpinner.html

        SpinnerNumberModel rowModel = new SpinnerNumberModel(15,5,50,1);
        SpinnerNumberModel colModel = new SpinnerNumberModel(15,5,50,1);
        JSpinner rowSpinner = new JSpinner(rowModel);
        JSpinner colSpinner = new JSpinner(colModel);
        rowSpinner.setForeground(Color.white);
        colSpinner.setForeground(Color.white);

        JLabel rowLabel = new JLabel("Row:");
        JLabel colLabel = new JLabel("Col:");
        rowLabel.setForeground(Color.white);
        colLabel.setForeground(Color.white);

        JPanel rowPanel = new JPanel();
        JPanel colPanel = new JPanel();
        rowPanel.setBackground(new Color(30,30,30));
        colPanel.setBackground(new Color(30,30,30));
        rowPanel.add(rowSpinner);
        colPanel.add(colSpinner);
        rowPanel.add(rowLabel);
        colPanel.add(colLabel);

        add(rowPanel);
        add(colPanel);

        JButton startButton = new JButton("Start");
        startButton.setForeground(Color.white);
        startButton.setFont(startButton.getFont().deriveFont(Font.BOLD));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        startButton.setBorder(BorderFactory.createEmptyBorder(30,0,20,0 ));
        startButton.setBackground(new Color(30,30,30));
        startButton.setFocusPainted(false);


        startButton.addActionListener(e -> {
            int rows = Integer.parseInt(rowSpinner.getValue().toString());
            int cols = Integer.parseInt(colSpinner.getValue().toString());

            GridPanel gridPanel = new GridPanel(frame,rows,cols);
            frame.setContentPane(gridPanel);
            frame.revalidate();
            frame.repaint();
        });
        add(Box.createRigidArea(new Dimension(20, 10)));
    }

    public GridPanel(JFrame frame, int rows, int cols) {
    }
}
