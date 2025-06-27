package src;

import java.awt.*;

// Autor: Titus
public class Enemy {

    private final double x;
    private final double y;
    private int health;
    private double speed;
    private Image image;
    private int damageToCastle;

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

    public int getHealth() {
        return health;
    }

    //public void reward(){if(health==0){}}
    public void draw(Graphics2D g2d, int offsetX, int CHUNK_SIZE) {
    }

    public void move() {
    }

    public boolean checkCollision(Projectile projectile, int CHUNK_SIZE) {
        return false;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public int getDamageToCastle() {
        return (int) damageToCastle;
    }
}