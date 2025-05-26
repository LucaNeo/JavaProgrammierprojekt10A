package src;

import java.awt.*;
import java.util.List;


public abstract class Enemy {
    protected int x, y;
    protected int speed ;
    protected int currentPathIndex = 0;
    protected int health;
    protected int reward;
    protected List<int[]> path;
    protected Image image;

    public Enemy(int startX, int startY){

        this.x = startX;
        this.y = startY;
    }

    public void move() {
        if (currentPathIndex >= path.size()) return; //ckeck if path is finished

        int[] currentPath = path.get(currentPathIndex);
        if (x == currentPath[0] && y == currentPath[1]) {
            currentPathIndex++;
            return; // cancel Loop
        }
    else {//TODO: pathfinding von Ckeckpoint zu Checkpoint
//        int dx = currentPath[0] - x;
//        int dy = currentPath[1] - y;
//        if (dx > 0) x++;
//        else if (dx < 0) x--;
//        if (dy > 0) y++;
//        else if (dy < 0) += speed;
    }
    }

    public void draw(Graphics g,int CHUNK_SIZE) {
    g.drawImage(image, x*CHUNK_SIZE, y*CHUNK_SIZE, CHUNK_SIZE,CHUNK_SIZE,null);
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
    public abstract int getMaxHealth();
    public abstract int getReward();
}