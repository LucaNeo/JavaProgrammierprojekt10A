package src;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Pathfinding extends JPanel implements Runnable {

    private final List<Enemy1> enemies = new ArrayList<>();
    private final int CHUNK_SIZE = 72;
    GamePanel gamePanel;
    Enemy1 e1;
    public int speed = 100;

    public Pathfinding() {
        e1 = new Enemy1(2, 0);
        this.e1 = e1;
    }

    private void createEnemies(Enemy1 a){ //Test Enemy
         a = new Enemy1(2,0);
        enemies.add(a);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        e1.draw(g,CHUNK_SIZE);
    }

    @Override
    public void run() {
    e1.x += speed/100;
    if (gamePanel.isPathway[e1.getX()][e1.getY()] = true;){

        }else

    }
}
