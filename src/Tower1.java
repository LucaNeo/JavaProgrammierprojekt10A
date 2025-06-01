package src;
// Autor Titus
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tower1 {

    public double x, y;
    public Image image;
    public int damage = 3;
    public int coolDown = 30; // in ms
    public int range = 10;
    public double shotSpeed = 0.2;
    public Enemy1[] enemy1 = new Enemy1[10];

    public Tower1(int x, int y) {

        this.x = x;
        this.y = y;

        // Bild laden
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-guard.png"))); // Pfad anpassen
        this.image = icon.getImage();
    }

    public void draw(Graphics g, int CHUNK_SIZE, int offsetX) {
        g.drawImage(image, (int) (x * CHUNK_SIZE + offsetX), (int) (y * CHUNK_SIZE), CHUNK_SIZE, CHUNK_SIZE, null);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }


}
