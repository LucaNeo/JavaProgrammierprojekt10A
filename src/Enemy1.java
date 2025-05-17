package src;

// Autor Luca
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Enemy1 {
    public int x, y;
    public int health = 100;
    public Image image;

    public Enemy1(int x, int y) {
        this.x = x;
        this.y = y;

        // Bild laden
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-midlander.png"))); // Pfad anpassen
        this.image = icon.getImage();
    }

    public void move() {

    }

    public void draw(Graphics g, int chunkSize) {
        g.drawImage(image, x * chunkSize, y * chunkSize, chunkSize, chunkSize, null);
    }
}