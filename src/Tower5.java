package src;
//Autor Jakob
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Tower5 extends Tower {

    private final int x;
    private final int y;
    private final Image image;
    private final GamePanel gamePanel;

    public Tower5(int x, int y, GamePanel gamePanel) {
        super(x, y, 0);

        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;

        // Bild laden
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-trader.png"))); // Pfad anpassen
        this.image = icon.getImage();
        startTimer();
    }

    private void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!gamePanel.getWave().getEnemyArrayList().isEmpty() && !gamePanel.getPaused()) {
                    gamePanel.setMoney(gamePanel.getMoney() + 200);
                }
            }
        };
        timer.schedule(task, 5000, 5000);
    }

    @Override
    public void draw(Graphics g, int CHUNK_SIZE, int offsetX) {
        g.drawImage(image, x * CHUNK_SIZE + offsetX, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
    }
}