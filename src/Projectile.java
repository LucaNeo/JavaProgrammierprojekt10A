package src;

import java.awt.*;

public class Projectile {

    private double x;
    private double y;
    private double speed;

    public Projectile(double x, double y, double speed, Graphics g) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void draw(Graphics g, Image image, int width, int height, int offsetX, int CHUNK_SIZE) {
        g.drawImage(image, (int) Math.round(x * CHUNK_SIZE) + offsetX, (int) Math.round(y * CHUNK_SIZE), width, height, null);
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
