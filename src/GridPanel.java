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
        SpinnerNumberModel rowModel = new SpinnerNumberModel(15, 5, 50, 1);
        SpinnerNumberModel colModel = new SpinnerNumberModel(15, 5, 50, 1);
        JSpinner rowSpinner = new JSpinner(rowModel);
        JSpinner colSpinner = new JSpinner(colModel);

        JLabel rowLabel = new JLabel("Reihen:");
        JLabel colLabel = new JLabel("Spalten:");
        rowLabel.setForeground(Color.WHITE);
        colLabel.setForeground(Color.WHITE);

        JPanel rowPanel = new JPanel();
        rowPanel.setBackground(new Color(30, 30, 40));
        rowPanel.add(rowLabel);
        rowPanel.add(rowSpinner);

        JPanel colPanel = new JPanel();
        colPanel.setBackground(new Color(30, 30, 40));
        colPanel.add(colLabel);
        colPanel.add(colSpinner);

        add(rowPanel);
        add(colPanel);

        JButton startButton = new JButton("Spiel starten");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setBackground(new Color(70, 70, 90));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);

        startButton.addActionListener(e -> {
            int rows1 = (Integer) rowSpinner.getValue();
            int cols1 = (Integer) colSpinner.getValue();


            GamePanel gamePanel = new GamePanel(frame, rows1, cols1);
            frame.setContentPane(gamePanel);
            frame.revalidate();
            frame.repaint();
        });
        add(Box.createRigidArea(new Dimension(20, 10)));
        add(startButton);

    }
}
