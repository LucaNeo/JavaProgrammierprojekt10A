package src;
import javax.swing.JFrame;

public class Main {

    private JFrame frame;
    private Enemy enemy = new Enemy();

    public static void main(String[] args) {
        new Main().go();
    }

    private void go(){
        frame = new JFrame("Tower Defense");

        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true); // review
        frame.getContentPane().add(enemy.label);
    }
}