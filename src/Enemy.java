package src;

import java.awt.*;
import java.util.List;


public class Enemy {
    public double x, y;
    public int speed ;
    public int currentPathIndex = 0;
    public int health;
    public int reward;
    public List<int[]> path;
    public Image image;

    public Enemy(double startX, double startY){

        this.x = startX;
        this.y = startY;
    }

    public void draw(Graphics g, int CHUNK_SIZE) {
        g.drawImage(image, (int) Math.round(x * CHUNK_SIZE), (int) Math.round(y * CHUNK_SIZE), CHUNK_SIZE, CHUNK_SIZE, null);
    }

    public boolean hasReachEnd(){
        return currentPathIndex >= path.size();
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public boolean isAlive(){
        return health > 0;
    }
}