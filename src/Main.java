package src;

//TODO: Placement Offset <-- Neo?
//TODO: Größe des Fensters anpassen können

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

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