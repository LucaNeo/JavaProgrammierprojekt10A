package src;

// Autor Luca
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class Enemy1 extends Enemy{
    public int x, y;
    public int health = 100;
    public Image image;

    public Enemy1(int x, int y)
    {   super(x,y);
        this.x = x;
        this.y = y;

        // Bild laden
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-midlander.png"))); // Pfad anpassen
        this.image = icon.getImage();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void move() {

    }

    public void draw(Graphics g ,int CHUNK_SIZE) {
       // Graphics2D g = (Graphics2D) image.getGraphics();
        g.drawImage(image, x * CHUNK_SIZE, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
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