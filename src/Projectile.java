package src;

import java.awt.*;
import java.awt.image.BufferedImage;

// Autor: Luca
public class Projectile {

    private double x;
    private double y;
    private double speed;
    private Tower originTower;

    public Projectile(double x, double y, double speed, Tower originTower) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.originTower = originTower;
    }

    public void draw(Graphics g, BufferedImage image, int offsetX, int CHUNK_SIZE) {
        g.drawImage(image, (int) Math.round(x * CHUNK_SIZE) + offsetX, (int) Math.round(y * CHUNK_SIZE), null);
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getSpeed(){ return speed; }
    public Tower getOriginTower(){ return originTower; }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
}
