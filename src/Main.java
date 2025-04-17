package src;
/* Autor Luca/Jakob */
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(Main.class.getResource("/src/textures/image-9.png")); // test Bilder finden
        JFrame frame = new JFrame("Tower Defense");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new MainMenu(frame));
        frame.setVisible(true);
    }
}