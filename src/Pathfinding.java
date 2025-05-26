package src;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Pathfinding extends JPanel {

    private final List<Enemy1> enemies = new ArrayList<>();
    private final int CHUNK_SIZE = 72;
    private GamePanel gamePanel;
    private Enemy1 e1;
    public double speed = 0.1;

    public Pathfinding() {
    }
    //Neo, Luca
    public void createEnemies(Enemy1 e){ //Test Enemy
        e = new Enemy1(2,0);
        enemies.add(e);
        this.e1 = e;
        System.out.println("Created Enemies");
    }

    public void paint(Graphics g){
        e1.draw(g, CHUNK_SIZE);
    }

    public void run() {
        if (e1.x == 2 && e1.y < 7){
            e1.y = Double.parseDouble(String.format("%.2f", e1.y += speed).replace(',', '.'));
            System.out.println(e1.y);
        }
        if (e1.x < 6 && e1.y == 7){
            e1.x = Double.parseDouble(String.format("%.2f", e1.x += speed).replace(',', '.'));
            System.out.println(e1.x);
        }
        if (e1.x == 6 && e1.y > 4 && e1.y < 8){
            e1.y = Double.parseDouble(String.format("%.2f", e1.y -= speed).replace(',', '.'));
            System.out.println(e1.y);
        }
        if (e1.x < 10 && e1.x > 5 && e1.y == 4){
            e1.x = Double.parseDouble(String.format("%.2f", e1.x += speed).replace(',', '.'));
            System.out.println(e1.x);
        }
        if (e1.x == 10 && e1.y < 10){
            e1.y = Double.parseDouble(String.format("%.2f", e1.y += speed).replace(',', '.'));
            System.out.println(e1.y);
        }
        if (e1.x > 4 && e1.y == 10){
            e1.x = Double.parseDouble(String.format("%.2f", e1.x -= speed).replace(',', '.'));
            System.out.println(e1.x);
        }
        if (e1.x == 4 && e1.y > 9 && e1.y < 14){
            e1.y = Double.parseDouble(String.format("%.2f", e1.y += speed).replace(',', '.'));
            System.out.println(e1.y);
        }
    }
}
