package src;

import java.awt.*;

public class Tower {
    private final double x;
    private final double y;
    private int damage;
    private int coolDown; // in ms
    private int range;
    private double shotSpeed;
    private int timer;

    public Tower(int x, int y, int timer) {
        this.x = x;
        this.y = y;
        this.timer = timer;
    }

    public void draw(Graphics g, int CHUNK_SIZE, int offsetX) {
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getCost() {
        return 0;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public double getShotSpeed() {
        return shotSpeed;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int value) {
        this.timer = value;
    }
}
