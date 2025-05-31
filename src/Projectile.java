package src;

import java.awt.*;

public class Projectile {

    public double x;
    public double y;
    private int speed;

    public Projectile(double x, double y, int speed, Graphics g) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
}
