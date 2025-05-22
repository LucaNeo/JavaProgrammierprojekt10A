package src;

import java.awt.*;
import java.util.List;


public abstract class Enemy {
    public final int x, y;
    private final int speed ;
    private final List<int[]> path;
    private int currentPathIndex = 0;
    private final Image image;

    public Enemy(int x, int y, int speed, List<int[]> path, Image image) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.path = path;
        this.image = image;
    }

    public void move() {
        if (currentPathIndex < path.size()) return;

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
}