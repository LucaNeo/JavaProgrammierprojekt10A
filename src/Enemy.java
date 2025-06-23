package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;


public class Enemy {
    private double x, y;
    private int health;
    private double speed;
    private Image image;

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

    public int getHealth() { return health; }
    //public void reward(){if(health==0){}}
    public void draw(Graphics2D g2d, int offsetX, int CHUNK_SIZE) {
    }

    public void move() {
    }

    public boolean checkCollision(Projectile projectile, int CHUNK_SIZE) {
        return false;
    }

    public void takeDamage(int damage){
    }
}