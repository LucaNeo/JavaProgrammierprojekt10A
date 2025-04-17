package src;
//Autor Jakob
import javax.swing.*;
import java.awt.*;

public class Tower1 {
    public int x, y;
    public Image image;

    public Tower1(int x, int y) {
        this.x = x;
        this.y = y;

        // Bild laden
        ImageIcon icon = new ImageIcon(getClass().getResource("/src/textures/image-10.png")); // Pfad anpassen
        this.image = icon.getImage();
    }


    public void draw(Graphics g, int tileSize) {
        g.drawImage(image, x * tileSize, y * tileSize, tileSize, tileSize, null);
    }
}
