package src;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Pathfinding extends JPanel implements Runnable {

    private final List<Enemy1> enemies = new ArrayList<>();
    private final int CHUNK_SIZE = 72;
    GamePanel gamePanel;
    private Enemy1 e1;
    public int speed = 100;

    public Pathfinding() {
    }

    public void createEnemies(Enemy1 a){ //Test Enemy
         a = new Enemy1(2,0);
        enemies.add(a);
        this.e1 = a;
        System.out.println("Created Enemies");
    }

    public void paint(Graphics g){
        e1.draw(g, CHUNK_SIZE);
    }

    @Override
    public void run() {
        if (e1.x == 2 && e1.y < 7){
            e1.y += speed/100;
            System.out.println("funzt");
        }
    }
}
