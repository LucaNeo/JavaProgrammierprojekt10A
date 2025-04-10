package src;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Enemy extends JFrame {

    Image image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("\\textures\\image-5.png"))).getImage();
    JLabel label;

    Enemy(){
        label = new JLabel(new ImageIcon(image.getScaledInstance(231, 324, Image.SCALE_SMOOTH)));
    }
}