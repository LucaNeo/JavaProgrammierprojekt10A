package src;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JCheckBox developerModeCheckBox = new JCheckBox("Developer mode");
        developerModeCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        developerModeCheckBox.setForeground(Color.LIGHT_GRAY);
        developerModeCheckBox.setBackground(new Color(30, 30, 40));

        JFrame frame = new JFrame("Tower Defense");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setContentPane(new MainMenu(frame));
        frame.setVisible(true);
    }
}