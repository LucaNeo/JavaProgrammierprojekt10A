package src;
import javax.swing.JFrame;

public class Main {

    private JFrame frame;
    public Enemy enemy;

    public static void main(String[] args) {
        new Main().go();
    }

    private void go(){
        frame = new JFrame("Tower Defense");

        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}