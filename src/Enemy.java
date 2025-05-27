package src;

import java.awt.*;
import java.util.List;


public class Enemy {
    public double x, y;
    public int speed;
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


    public void move() {
        if (x == 2 && y < 7){
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
}