package src;
// Autor Titus
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tower1 extends Tower {

    private final double x;
    private final double y;
    public Image image;
    public int damage = 25;
    public int coolDown = 10; // in ms
    private int range = 5;
    public double shotSpeed = 0.1;

    public Tower1(int x, int y) {
        super(x, y);

        this.x = x;
        this.y = y;

        // Bild laden
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-guard.png"))); // Pfad anpassen
        this.image = icon.getImage();
    }

    @Override
    public void draw(Graphics g, int CHUNK_SIZE, int offsetX) {
        g.drawImage(image, (int) (x * CHUNK_SIZE + offsetX), (int) (y * CHUNK_SIZE), CHUNK_SIZE, CHUNK_SIZE, null);
    }

    @Override
    public double getX(){
        return x;
    }

    @Override
    public double getY(){
        return y;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public int getDamage() {
        return damage;
    }
}
