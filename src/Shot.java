package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Shot {

    private final GamePanel gamePanel;
    private final List<Projectile> projectile = new ArrayList<>();
    private Enemy targetedEnemy;
    private final List<Double> deltaX = new ArrayList<>();
    private final List<Double> deltaY = new ArrayList<>();
    private final Integer[] timer = new Integer[4];
    Image image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/goatAttack.png"))).getImage();

    public Shot(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        Arrays.fill(timer, 0);
    }

    public void run(Graphics g) {
        runProjectiles(g);

        timer[0]++;
        timer[1]++;
    }

    // Autor: Neo, Luca
    private void runProjectiles(Graphics g) {
        List<Projectile> nextProjectiles = new ArrayList<>();
        List<Double> nextDeltaX = new ArrayList<>();
        List<Double> nextDeltaY = new ArrayList<>();

        Image projectile1Image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/goatAttack.png"))).getImage();
        Image projectile4Image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/arrow.png"))).getImage();

        // Logik für das Erstellen neuer Projektile für Tower1
        for (int a = 0; a < gamePanel.getTower1Arraylist().size(); a++) {
            Tower1 tower = gamePanel.getSpecificTower1(a);
            if (tower != null && getTargetedEnemy(tower) != null) {
                if (gamePanel.getHealth() > 0 && checkEnemyInRange(tower) && !gamePanel.getWave().getEnemyArrayList().isEmpty()) {
                    System.out.println(checkEnemyInRange(tower));
                    if (timer[0] % tower.getCoolDown() == 0) {
                        projectile.add(new Projectile(gamePanel.getSpecificTower1(a).getX(), gamePanel.getSpecificTower1(a).getY(), gamePanel.getSpecificTower1(a).getShotSpeed(), g, gamePanel.getSpecificTower1(a)));
                        deltaX.add((getTargetedEnemy(gamePanel.getSpecificTower1(a)).getX() + 0.5) - gamePanel.getSpecificTower1(a).getX());
                        deltaY.add((getTargetedEnemy(gamePanel.getSpecificTower1(a)).getY() + 0.5) - gamePanel.getSpecificTower1(a).getY());
                    }
                }
            }
        }

        // Logik für das Erstellen neuer Projektile für Tower4
        for (int a = 0; a < gamePanel.getTower4Arraylist().size(); a++) {
            Tower4 tower = gamePanel.getSpecificTower4(a);
            if (tower != null && getTargetedEnemy(tower) != null) {
                if (gamePanel.getHealth() > 0 && checkEnemyInRange(tower) && !gamePanel.getWave().getEnemyArrayList().isEmpty()) {
                    if (timer[1] % tower.getCoolDown() == 0) {
                        projectile.add(new Projectile(gamePanel.getSpecificTower4(a).getX(), gamePanel.getSpecificTower4(a).getY(), gamePanel.getSpecificTower4(a).getShotSpeed(), g, gamePanel.getSpecificTower4(a)));
                        deltaX.add((getTargetedEnemy(gamePanel.getSpecificTower4(a)).getX() + 0.5) - gamePanel.getSpecificTower4(a).getX());
                        deltaY.add((getTargetedEnemy(gamePanel.getSpecificTower4(a)).getY() + 0.5) - gamePanel.getSpecificTower4(a).getY());
                    }
                }
            }
        }

        for (int i = 0; i < projectile.size(); i++) {
            Projectile p = projectile.get(i);
            if (p != null && i < deltaX.size() && i < deltaY.size()) {
                double currentDeltaX = deltaX.get(i);
                double currentDeltaY = deltaY.get(i);
                double angle = Math.toDegrees(Math.atan2(currentDeltaY, currentDeltaX)) + 90;

                Image currentProjectileImage;
                if (p.getOriginTower().getClass() == Tower1.class) {
                    currentProjectileImage = projectile1Image;
                } else if (p.getOriginTower().getClass() == Tower4.class) {
                    currentProjectileImage = projectile4Image;
                } else {
                    currentProjectileImage = projectile4Image;
                }

                p.draw(g, getRotatedImage(currentProjectileImage, angle), gamePanel.getOffsetX(), gamePanel.getCHUNK_SIZE());
                p.setX(p.getX() + currentDeltaX * p.getSpeed());
                p.setY(p.getY() + currentDeltaY * p.getSpeed());

                if (!hitAndMarkForRemoval(p, p.getOriginTower())) {
                    if (p.getX() >= 0 && p.getY() >= 0 && p.getX() < gamePanel.getWidth() && p.getY() < gamePanel.getHeight()) {
                        nextProjectiles.add(p);
                        nextDeltaX.add(currentDeltaX);
                        nextDeltaY.add(currentDeltaY);
                    }
                }
            }
        }

        projectile.clear();
        projectile.addAll(nextProjectiles);
        nextProjectiles.clear();
        deltaX.clear();
        deltaX.addAll(nextDeltaX);
        nextDeltaX.clear();
        deltaY.clear();
        deltaY.addAll(nextDeltaY);
        nextDeltaY.clear();
    }

    // Autor: Neo, Luca
    private BufferedImage getRotatedImage (Image projectile, double angle) {
        int width = projectile.getWidth(null);
        int height = projectile.getHeight(null);
        int newWidth = (int) Math.abs(width * Math.cos(angle)) + (int) Math.abs(height * Math.sin(angle));
        int newHeight = (int) Math.abs(height * Math.cos(angle)) + (int) Math.abs(width * Math.sin(angle));

        BufferedImage originalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        AffineTransform transform = new AffineTransform();

        transform.rotate(Math.toRadians(angle), (double) newWidth / 2, (double) newHeight / 2);
        transform.translate((double) (newWidth - width) / 2, (double) (newHeight - height) / 2);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        Graphics2D g2d = originalImage.createGraphics();
        g2d.drawImage(projectile, 0, 0, null);
        g2d.dispose();

        return op.filter(originalImage, rotatedImage);
    }

    // gibt den vordersten Enemy zurück, der in der Range des Towers ist. Autor: Neo
    private Enemy getTargetedEnemy(Tower tower) {

        double smallestdistance = tower.getRange();
        boolean targeting = true;

        for (int a = 0; a < gamePanel.getWave().getEnemyArrayList().size() && targeting; a++) {
            if (gamePanel.getWave().getSpecificEnemy(a) != null) {
                double tempDeltaX = tower.getX() - gamePanel.getWave().getSpecificEnemy(a).getX();
                double tempDeltaY = tower.getY() - gamePanel.getWave().getSpecificEnemy(a).getY();
                double distance = Math.sqrt(tempDeltaX * tempDeltaX + tempDeltaY * tempDeltaY);

                if (distance <= smallestdistance) {
                    smallestdistance = distance;
                }

                if (distance <= tower.getRange() && distance == smallestdistance) {
                    targetedEnemy = gamePanel.getWave().getSpecificEnemy(a);
                    targeting = false;
                }
            }
        }
        return targetedEnemy;
    }

    // Autor: Luca
    private boolean checkEnemyInRange(Tower tower) {
        double tempDeltaX = tower.getX() - getTargetedEnemy(tower).getX();
        double tempDeltaY = tower.getY() - getTargetedEnemy(tower).getY();
        double distance = Math.sqrt(tempDeltaX * tempDeltaX + tempDeltaY * tempDeltaY);

        if (distance <= tower.getRange()) {
            return true;
        }
        return false;
    }

    // Autor: Luca
    private boolean hitAndMarkForRemoval(Projectile p, Tower tower) {
        List<Enemy> enemiesToProcess = new ArrayList<>(gamePanel.getWave().getEnemyArrayList());

        for (Enemy enemy : enemiesToProcess) {
            if (p != null && enemy != null && enemy.checkCollision(p, gamePanel.getCHUNK_SIZE())) {
                enemy.takeDamage(tower.getDamage());
                if (enemy.getHealth() <= 0) {
                    gamePanel.getWave().removeEnemy(enemy);
                    gamePanel.setMoney(gamePanel.getMoney() + 50);
                }
                return true;
            }
        }
        return false;
    }

    // Autor: Neo, Luca
    public void resetShot() {
        projectile.clear();
        deltaX.clear();
        deltaY.clear();
        targetedEnemy = null;
        Arrays.fill(timer, 0);
    }
}