package src;

// Autor Luca
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Enemy1 extends Enemy {
    public double x, y;
    public int health = 100;
    public double speed = 0.1;
    public Image image;
    private final GamePanel gamePanel;

    public Enemy1(double x, double y, GamePanel gamePanel) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;

        // Bild laden
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-midlander.png"))); // Pfad anpassen
        this.image = icon.getImage();
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public void draw(Graphics g, int CHUNK_SIZE) {
        g.drawImage(image, (int) Math.round(x * CHUNK_SIZE) + gamePanel.offsetX, (int) Math.round(y * CHUNK_SIZE), CHUNK_SIZE, CHUNK_SIZE, null);
    }

    public void move() {
        if (x == 2 && y < 7){
            y = Double.parseDouble(String.format("%.2f", y += speed).replace(',', '.'));
        }
        if (x < 6 && y == 7){
            x = Double.parseDouble(String.format("%.2f", x += speed).replace(',', '.'));
        }
        if (x == 6 && y > 4 && y < 8){
            y = Double.parseDouble(String.format("%.2f", y -= speed).replace(',', '.'));
        }
        if (x < 10 && x > 5 && y == 4){
            x = Double.parseDouble(String.format("%.2f", x += speed).replace(',', '.'));
        }
        if (x == 10 && y < 10){
            y = Double.parseDouble(String.format("%.2f", y += speed).replace(',', '.'));
        }
        if (x > 4 && y == 10){
            x = Double.parseDouble(String.format("%.2f", x -= speed).replace(',', '.'));
        }
        if (x == 4 && y > 9 && y < 14){
            y = Double.parseDouble(String.format("%.2f", y += speed).replace(',', '.'));
        }
    }
}