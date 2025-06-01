package src;

import java.awt.*;

public class Tower {
    private double x, y;
    public int damage;
    public int coolDown; // in ms
    private int range;
    public double shotSpeed;

    public Tower(int x, int y) {
        this.x = x;
        this.y = y;
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
}
