package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Autoren Neo, Luca

public class Shot {

    public GamePanel gamePanel;
    private double x;
    private double y;
    private double timer1 = 0;

    final List<Projectile> projectile = new ArrayList<>();
    private Image projectileImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/goatAttack.png"))).getImage();
    private Enemy targetedEnemy;
    List<Double> deltaX = new ArrayList<>();
    List<Double> deltaY = new ArrayList<>();

    Shot(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void run(Graphics g){
        shootTower1(g);
    }

    //TODO double arrylist wo die speeds bzw die ausrichtung für jeden schuss gespeichert ist, die dann in shoottower aufgerufen wird... (im moment wird die geschwindigkeit für den aktuellen schuss auf alle angewendet (soll so nicht)) LG

    public void shootTower1(Graphics g) {
        for (int a = 0; a < gamePanel.towers1.size(); a++) {
            if (gamePanel.towers1.get(a) != null && getTargetedEnemy(gamePanel.towers1.get(a)) != null) {

                if (timer1 % gamePanel.towers1.get(a).coolDown == 0 && gamePanel.health > 0) {
                    projectile.add(new Projectile(gamePanel.towers1.get(a).getX(), gamePanel.towers1.get(a).getY(), gamePanel.towers1.get(a).shotSpeed, g));
                    deltaX.add(getTargetedEnemy(gamePanel.towers1.get(a)).getX() - gamePanel.towers1.get(a).getX());
                    deltaY.add(getTargetedEnemy(gamePanel.towers1.get(a)).getY() - gamePanel.towers1.get(a).getY());
                }

                for (Projectile p : projectile) {
                    int index = projectile.indexOf(p);

                    p.draw(g, projectileImage, 25, 25, gamePanel.offsetX, gamePanel.CHUNK_SIZE);
                    p.move(deltaX.get(index) * gamePanel.towers1.get(a).shotSpeed, deltaY.get(index) * gamePanel.towers1.get(a).shotSpeed);
                    hit(p);
                }
            }
        }
        timer1++;
    }

    //gibt den vordersten Enemy zurück, der in der Range des Towers ist.
    private Enemy getTargetedEnemy(Tower tower) {

        double smallestdistance = tower.getRange();
        boolean targeting = true;

        for (int a = 0; a < gamePanel.wave.enemy1.size() && targeting; a++) {
            if (gamePanel.wave.enemy1.get(a) != null) {
                double tempDeltaX = tower.getX() - gamePanel.wave.enemy1.get(a).getX();
                double tempDeltaY = tower.getY() - gamePanel.wave.enemy1.get(a).getY();
                double distance = Math.sqrt(tempDeltaX * tempDeltaX + tempDeltaY * tempDeltaY);
                System.out.println(distance);
                System.out.println(tower.getRange());

                if (distance <= smallestdistance) {
                    smallestdistance = distance;
                }

                if (distance <= tower.getRange() && distance == smallestdistance) {
                    targetedEnemy = gamePanel.wave.enemy1.get(a);
                    targeting = false;
                }
            }
        }
        return targetedEnemy;
    }

    private void hit(Projectile p) {
        for (Enemy enemy : gamePanel.wave.enemy1) {
            int indexEnemy = gamePanel.wave.enemy1.indexOf(enemy);
            if (p != null && enemy != null && enemy.checkCollision(p, gamePanel.CHUNK_SIZE)) {
                projectile.remove(p);
                gamePanel.wave.enemy1.remove(enemy);
                System.out.println(enemy);
                //System.out.println("hit");
            }
        }
    }
}