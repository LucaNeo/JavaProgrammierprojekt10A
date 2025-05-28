package src;
//Autor Jakob
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tower5 {
    public int x, y;
    public Image image;

    public Tower5(int x, int y) {
        this.x = x;
        this.y = y;

        // Bild laden
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-trader.png"))); // Pfad anpassen
        this.image = icon.getImage();
    }

    public void draw(Graphics g, int CHUNK_SIZE) {
        g.drawImage(image, x * CHUNK_SIZE, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
    }
}