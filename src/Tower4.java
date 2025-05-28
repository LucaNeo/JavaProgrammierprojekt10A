package src;
//Autor Jakob
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tower4 {
    public int x, y;
    public Image image;

    public Tower4(int x, int y) {
        this.x = x;
        this.y = y;

        // Bild laden
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-archer.png"))); // Pfad anpassen
        this.image = icon.getImage();
    }

    public void draw(Graphics g, int CHUNK_SIZE, int offsetX) {
        g.drawImage(image, x * CHUNK_SIZE + offsetX, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
    }
}

