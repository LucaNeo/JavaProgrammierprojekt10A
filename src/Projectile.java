package src;

import java.awt.*;

public class Projectile {

    private double x;
    private double y;

    public Projectile(double x, double y, double speed, Graphics g) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, Image image, int offsetX, int CHUNK_SIZE) {
        g.drawImage(image, (int) Math.round(x * CHUNK_SIZE) + offsetX, (int) Math.round(y * CHUNK_SIZE), null);
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
}
