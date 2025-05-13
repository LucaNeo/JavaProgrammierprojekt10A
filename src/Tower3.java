package src;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tower3{
    public int x, y;
    public Image image;

    Tower3(int x, int y) {
        this.x = x;
        this.y = y;
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-priest.png"))); // Pfad anpassen
        this.image = icon.getImage();
    }
public void move() {

}

public void draw(Graphics g,int tileSize) {
    g.drawImage(image, x * tileSize, y * tileSize, tileSize, tileSize, null);
}

}
