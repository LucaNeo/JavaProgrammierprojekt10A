package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//Autoren Neo, Luca

public class Shot {

    public GamePanel gamePanel;


    private final List<Projectile> projectile = new ArrayList<>();
    private Enemy targetedEnemy;
    private final List<Double> deltaX = new ArrayList<>();
    private final List<Double> deltaY = new ArrayList<>();
    private final Integer[] timer = new Integer[4];

    Shot(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        Arrays.fill(timer, 0);
    }

    public void run(Graphics g, Graphics2D g2d) {
        shootTower1(g, g2d);
    }

    public void shootTower1(Graphics g, Graphics2D g2d) {
        Image projectileImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/goatAttack.png"))).getImage();

        for (int a = 0; a < gamePanel.towers1.size(); a++) {
            if (gamePanel.towers1.get(a) != null && getTargetedEnemy(gamePanel.towers1.get(a)) != null) {
                if (gamePanel.health > 0 && checkEnemyInRange(gamePanel.towers1.get(a)) && !gamePanel.wave.enemy1.isEmpty()) {
                    if (timer[0] % gamePanel.towers1.get(a).coolDown == 0) {
                        projectile.add(new Projectile(gamePanel.towers1.get(a).getX(), gamePanel.towers1.get(a).getY(), gamePanel.towers1.get(a).shotSpeed, g));
                        deltaX.add((getTargetedEnemy(gamePanel.towers1.get(a)).getX() + 0.5) - gamePanel.towers1.get(a).getX());
                        deltaY.add((getTargetedEnemy(gamePanel.towers1.get(a)).getY() + 0.5) - gamePanel.towers1.get(a).getY());
                    }

                    for (Projectile p : projectile) {
                        int index = projectile.indexOf(p);
                        int angleRad = (int) Math.toRadians(Math.atan(Math.round(deltaX.get(index)) - Math.round(deltaY.get(index))));
                        AffineTransform transform = new AffineTransform();
                        transform.rotate(angleRad);

                        g2d.transform(transform);
                        p.draw(g, projectileImage, 71, 26, gamePanel.offsetX, gamePanel.CHUNK_SIZE);
                        p.move(deltaX.get(index) * gamePanel.towers1.get(a).shotSpeed, deltaY.get(index) * gamePanel.towers1.get(a).shotSpeed);
                        p.setX(p.getX() + deltaX.get(index) * gamePanel.towers1.get(a).shotSpeed);
                        p.setY(p.getY() + deltaY.get(index) * gamePanel.towers1.get(a).shotSpeed);
                        hit(p, gamePanel.towers1.get(a), index);
                    }
                }
            }
        }
        timer[0]++;
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

    private boolean checkEnemyInRange(Tower tower) {
        double tempDeltaX = tower.getX() - getTargetedEnemy(tower).getX();
        double tempDeltaY = tower.getY() - getTargetedEnemy(tower).getY();
        double distance = Math.sqrt(tempDeltaX * tempDeltaX + tempDeltaY * tempDeltaY);

        if (distance <= tower.getRange()) {
            return true;
        }
        return false;
    }

    private void hit(Projectile p, Tower tower, int index) {
        for (Enemy enemy : gamePanel.wave.enemy1) {
            if (p != null && enemy != null && enemy.checkCollision(p, gamePanel.CHUNK_SIZE)) {
                enemy.takeDamage(tower.getDamage());
                projectile.remove(p);
                deltaX.remove(index);
                deltaY.remove(index);
                if (enemy.getHealth() <= 0) {
                    gamePanel.wave.enemy1.remove(enemy);
                    gamePanel.money +=50;
                }
            }
        }
    }
}