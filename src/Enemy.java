package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;


public class Enemy {
    public double x, y;
    public int health;
    public double speed;
    public Image image;

    public Enemy(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }
    //public void reward(){if(health==0){}}
    public void draw(Graphics g, int offsetX, int CHUNK_SIZE) {
    }

    public void move() {
        if (x == 2 && y < 7) {
            y = Double.parseDouble(String.format("%.2f", y += speed).replace(',', '.'));
        }
        if (x < 6 && y == 7){
            x = Double.parseDouble(String.format("%.2f", x += speed).replace(',', '.'));
        }
        if (x == 6 && y > 4 && y < 8){
            y = Double.parseDouble(String.format("%.2f", y -= speed).replace(',', '.'));
        }
        if (x < 10 && x > 5 && y == 4){
            x = Double.parseDouble(String.format("%.2f", x += speed).replace(',', '.'));
        }
        if (x == 10 && y < 10){
            y = Double.parseDouble(String.format("%.2f", y += speed).replace(',', '.'));
        }
        if (x > 4 && y == 10){
            x = Double.parseDouble(String.format("%.2f", x -= speed).replace(',', '.'));
        }
        if (x == 4 && y > 9 && y < 14){
            y = Double.parseDouble(String.format("%.2f", y += speed).replace(',', '.'));
        }
    }

    public boolean checkCollision(Projectile projectile, int CHUNK_SIZE) {
        double centerPointX = x + (double) CHUNK_SIZE / 2;
        double centerPointY = y + (double) CHUNK_SIZE / 2;
        double tolerance = 0.5 * CHUNK_SIZE;

        if (centerPointX + tolerance > projectile.getX() || projectile.getX() > centerPointX - tolerance || centerPointY + tolerance > projectile.getY() || projectile.getY() > centerPointY - tolerance) {
            return true;
        }

        return false;
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public boolean isAlive(){
        return health > 0;
    }
}