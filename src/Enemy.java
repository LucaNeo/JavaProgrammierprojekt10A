package src;
import src.textures.*;

import javax.swing.*;
import java.awt.Image;

public class Enemy {

    Image image;

    Enemy(){
        image = new ImageIcon(getClass().getResource("./textures/image-5.png")).getImage();
    }
}