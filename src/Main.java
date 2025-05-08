package src;
/* Autor Luca/Jakob */
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JCheckBox developerModeCheckBox = new JCheckBox("Developer Mode");
        developerModeCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        developerModeCheckBox.setForeground(Color.LIGHT_GRAY);
        developerModeCheckBox.setBackground(new Color(30, 30, 40));

       

        JFrame frame = new JFrame("Tower Defense");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new MainMenu(frame));
        frame.setVisible(true);
    }
}