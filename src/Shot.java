package src;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.PrintFilesEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AreaAveragingScaleFilter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

//Autoren Neo, Luca

public class Shot {

    public GamePanel gamePanel;


    private final List<Projectile> projectile = new ArrayList<>();
    private Enemy targetedEnemy;
    private final List<Double> deltaX = new ArrayList<>();
    private final List<Double> deltaY = new ArrayList<>();
    private final Integer[] timer = new Integer[4];

    public Shot(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        Arrays.fill(timer, 0);
    }

    public void run(Graphics g) {
        shootTower1(g);
        shootTower4(g);
    }

    //Autor: Neo, Luca
    public void shootTower1(Graphics g) {
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
                        double angle = Math.toDegrees(Math.atan2(deltaY.get(index), deltaX.get(index)));

                        p.draw(g, getRotatedImage(projectileImage, angle), gamePanel.offsetX, gamePanel.CHUNK_SIZE);
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

    //Autor: Neo
    public void shootTower4(Graphics g) {
        Image projectileImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/arrow.png"))).getImage();

        for (int a = 0; a < gamePanel.towers4.size(); a++) {
            if (gamePanel.towers4.get(a) != null && getTargetedEnemy(gamePanel.towers4.get(a)) != null) {
                if (gamePanel.health > 0 && checkEnemyInRange(gamePanel.towers4.get(a)) && !gamePanel.wave.enemy1.isEmpty()) {
                    if (timer[1] % gamePanel.towers4.get(a).coolDown == 0) {
                        projectile.add(new Projectile(gamePanel.towers4.get(a).getX(), gamePanel.towers4.get(a).getY(), gamePanel.towers4.get(a).shotSpeed, g));
                        deltaX.add((getTargetedEnemy(gamePanel.towers4.get(a)).getX() + 0.5) - gamePanel.towers4.get(a).getX());
                        deltaY.add((getTargetedEnemy(gamePanel.towers4.get(a)).getY() + 0.5) - gamePanel.towers4.get(a).getY());
                    }

                    for (Projectile p : projectile) {
                        int index = projectile.indexOf(p);
                        double angle = Math.toDegrees(Math.atan2(deltaY.get(index), deltaX.get(index)));

                        p.draw(g, getRotatedImage(projectileImage, angle), gamePanel.offsetX, gamePanel.CHUNK_SIZE);
                        p.move(deltaX.get(index) * gamePanel.towers4.get(a).shotSpeed, deltaY.get(index) * gamePanel.towers4.get(a).shotSpeed);
                        p.setX(p.getX() + deltaX.get(index) * gamePanel.towers4.get(a).shotSpeed);
                        p.setY(p.getY() + deltaY.get(index) * gamePanel.towers4.get(a).shotSpeed);
                        hit(p, gamePanel.towers4.get(a), index);
                    }
                }
            }
        }
        timer[1]++;
    }

    //Autor: Neo, Luca
    private BufferedImage getRotatedImage (Image projectile, double angle) {
        int width = projectile.getWidth(null);
        int height = projectile.getHeight(null);
        int newWidth = (int) Math.abs(width * Math.cos(angle)) + (int) Math.abs(height * Math.sin(angle));
        int newHeight = (int) Math.abs(height * Math.cos(angle)) + (int) Math.abs(width * Math.sin(angle));

        BufferedImage originalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        AffineTransform transform = new AffineTransform();
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        transform.rotate(Math.toRadians(angle + 90), (double) newWidth / 2, (double) newHeight / 2);
        transform.translate((double) (newWidth - width) / 2, (double) (newHeight - height) / 2);

        Graphics2D g2d = originalImage.createGraphics();
        g2d.drawImage(projectile, 0, 0, null);
        g2d.dispose();

        return op.filter(originalImage, rotatedImage);
    }

    //gibt den vordersten Enemy zurück, der in der Range des Towers ist. Autor: Neo
    private Enemy getTargetedEnemy(Tower tower) {

        double smallestdistance = tower.getRange();
        boolean targeting = true;

        for (int a = 0; a < gamePanel.wave.enemy1.size() && targeting; a++) {
            if (gamePanel.wave.enemy1.get(a) != null) {
                double tempDeltaX = tower.getX() - gamePanel.wave.enemy1.get(a).getX();
                double tempDeltaY = tower.getY() - gamePanel.wave.enemy1.get(a).getY();
                double distance = Math.sqrt(tempDeltaX * tempDeltaX + tempDeltaY * tempDeltaY);

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

    //Autor: Luca
    private boolean checkEnemyInRange(Tower tower) {
        double tempDeltaX = tower.getX() - getTargetedEnemy(tower).getX();
        double tempDeltaY = tower.getY() - getTargetedEnemy(tower).getY();
        double distance = Math.sqrt(tempDeltaX * tempDeltaX + tempDeltaY * tempDeltaY);

        if (distance <= tower.getRange()) {
            return true;
        }
        return false;
    }

    //Autor: Luca
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