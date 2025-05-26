package src;

// Autor Luca
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class Enemy1 extends Enemy{
    public double x, y;
    public int health = 100;
    public Image image;

    public Enemy1(double x, double y) {   super(x,y);
        this.x = x;
        this.y = y;

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
        g.drawImage(image, (int) Math.round(x * CHUNK_SIZE), (int) Math.round(y * CHUNK_SIZE), CHUNK_SIZE, CHUNK_SIZE, null);
    }

    @Override
    public int getMaxHealth() {
        return health;
    }

    @Override
    public int getReward() {
        return 50;// geld für Kill kriegen
    }
}