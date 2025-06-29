package src;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

// Autor: Titus
public class Tower4 extends Tower {

    private final double x;
    private final double y;
    private final Image image;
    public int damage = 1;
    public int coolDown = 40; // in ms
    private final int range = 10;
    private final double shotSpeed = 0.1;
    private int timer;

    public Tower4(int x, int y, int timer) {
        super(x, y, timer);

        this.x = x;
        this.y = y;
        this.timer = timer;

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-archer.png")));
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

    @Override
    public int getCoolDown() { return coolDown; }

    @Override
    public double getShotSpeed() { return shotSpeed; }

    @Override
    public int getTimer() { return timer; }

    @Override
    public void setTimer(int value) { this.timer = value; }
}

