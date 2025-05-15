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


    public void draw(Graphics g, int tileSize) {
        g.drawImage(image, x * tileSize, y * tileSize, tileSize, tileSize, null);
    }
}