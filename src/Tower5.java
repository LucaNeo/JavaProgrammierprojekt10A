package src;
//Autor Jakob
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Tower5 {
    public int x, y;
    public Image image;
    public GamePanel gamePanel;


//timer welcher alle 5 sekunden dir geld gibt wenn trader da ist
        private final Timer timer = new Timer();

        public void startIncrementing() {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                   gamePanel.money=gamePanel.money+50 ;
                     ;
                }
            };


            timer.scheduleAtFixedRate(task, 0, 5000);
        }







    public Tower5(int x, int y) {
        this.x = x;
        this.y = y;

        // Bild laden
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-trader.png"))); // Pfad anpassen
        this.image = icon.getImage();
    }

    public void draw(Graphics g, int CHUNK_SIZE, int offsetX) {
        g.drawImage(image, x * CHUNK_SIZE + offsetX, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
    }
}