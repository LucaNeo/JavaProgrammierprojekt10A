package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

//Autoren Neo, Luca

public class Shot {

    private GamePanel gamePanel;
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
        for (int a = 0; a < gamePanel.towers1.size(); a++) {
            Tower1 tower = gamePanel.towers1.get(a);
            if (tower != null && getTargetedEnemy(tower) != null) {
                if (gamePanel.health > 0 && checkEnemyInRange(tower) && !gamePanel.wave.enemy.isEmpty()) {
                    if (timer[0] % tower.coolDown == 0) {
                        projectile.add(new Projectile(gamePanel.towers1.get(a).getX(), gamePanel.towers1.get(a).getY(), gamePanel.towers1.get(a).getShotSpeed(), g, gamePanel.towers1.get(a)));
                        deltaX.add((getTargetedEnemy(gamePanel.towers1.get(a)).getX() + 0.5) - gamePanel.towers1.get(a).getX());
                        deltaY.add((getTargetedEnemy(gamePanel.towers1.get(a)).getY() + 0.5) - gamePanel.towers1.get(a).getY());
                    }
                }
            }
        }

        // Logik für das Erstellen neuer Projektile für Tower4
        for (int a = 0; a < gamePanel.towers4.size(); a++) {
            Tower4 tower = gamePanel.towers4.get(a);
            if (tower != null && getTargetedEnemy(tower) != null) {
                if (gamePanel.health > 0 && checkEnemyInRange(tower) && !gamePanel.wave.enemy.isEmpty()) {
                    if (timer[1] % tower.coolDown == 0) {
                        projectile.add(new Projectile(gamePanel.towers4.get(a).getX(), gamePanel.towers4.get(a).getY(), gamePanel.towers4.get(a).getShotSpeed(), g, gamePanel.towers4.get(a)));
                        deltaX.add((getTargetedEnemy(gamePanel.towers4.get(a)).getX() + 0.5) - gamePanel.towers4.get(a).getX());
                        deltaY.add((getTargetedEnemy(gamePanel.towers4.get(a)).getY() + 0.5) - gamePanel.towers4.get(a).getY());
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

    //Autor: Neo, Luca
//    public void shootTower1(Graphics g) {
//        Image projectileImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/goatAttack.png"))).getImage();
//
//        for (int a = 0; a < gamePanel.towers1.size(); a++) {
//            if (gamePanel.towers1.get(a) != null && getTargetedEnemy(gamePanel.towers1.get(a)) != null) {
//                if (gamePanel.health > 0 && checkEnemyInRange(gamePanel.towers1.get(a)) && !gamePanel.wave.enemy1.isEmpty()) {
//                    if (timer[0] % gamePanel.towers1.get(a).coolDown == 0) {
//                        //projectile.add(new Projectile(gamePanel.towers1.get(a).getX(), gamePanel.towers1.get(a).getY(), gamePanel.towers1.get(a).shotSpeed, g));
//                        deltaX.add((getTargetedEnemy(gamePanel.towers1.get(a)).getX() + 0.5) - gamePanel.towers1.get(a).getX());
//                        deltaY.add((getTargetedEnemy(gamePanel.towers1.get(a)).getY() + 0.5) - gamePanel.towers1.get(a).getY());
//                    }
//
//                    for (Projectile p : projectile) {
//                        int index = projectile.indexOf(p);
//                        double angle = Math.toDegrees(Math.atan2(deltaY.get(index), deltaX.get(index)));
//
//                        p.draw(g, getRotatedImage(projectileImage, angle), gamePanel.offsetX, gamePanel.CHUNK_SIZE);
//                        p.move(deltaX.get(index) * gamePanel.towers1.get(a).shotSpeed, deltaY.get(index) * gamePanel.towers1.get(a).shotSpeed);
//                        p.setX(p.getX() + deltaX.get(index) * gamePanel.towers1.get(a).shotSpeed);
//                        p.setY(p.getY() + deltaY.get(index) * gamePanel.towers1.get(a).shotSpeed);
//                        //hit(p, gamePanel.towers1.get(a), index);
//                    }
//                }
//            }
//        }
//        timer[0]++;
//    }

//    public void shootTower1(Graphics g) {
//        Image projectileImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/goatAttack.png"))).getImage();
//
//        for (int a = 0; a < gamePanel.towers1.size(); a++) {
//            if (gamePanel.towers1.get(a) != null && getTargetedEnemy(gamePanel.towers1.get(a)) != null) {
//                if (gamePanel.health > 0 && checkEnemyInRange(gamePanel.towers1.get(a)) && !gamePanel.wave.enemy.isEmpty()) {
//                    if (timer[0] % gamePanel.towers1.get(a).coolDown == 0) {
//                        projectile.add(new Projectile(gamePanel.towers1.get(a).getX(), gamePanel.towers1.get(a).getY(), gamePanel.towers1.get(a).shotSpeed, g, gamePanel.towers1.get(a)));
//                        deltaX.add((getTargetedEnemy(gamePanel.towers1.get(a)).getX() + 0.5) - gamePanel.towers1.get(a).getX());
//                        deltaY.add((getTargetedEnemy(gamePanel.towers1.get(a)).getY() + 0.5) - gamePanel.towers1.get(a).getY());
//                    }
//                }
//            }
//        }
//
//        List<Projectile> nextProjectiles = new ArrayList<>();
//        List<Double> nextDeltaX = new ArrayList<>();
//        List<Double> nextDeltaY = new ArrayList<>();
//
//        for (int i = 0; i < projectile.size(); i++) {
//            if (projectile.get(i) != null && projectile.get(i).getOriginTower().getClass() == Tower1.class) {
//                Projectile p = projectile.get(i);
//                double currentDeltaX = deltaX.get(i);
//                double currentDeltaY = deltaY.get(i);
//                double angle = Math.toDegrees(Math.atan2(currentDeltaY, currentDeltaX));
//
//                p.draw(g, getRotatedImage(projectileImage, angle), gamePanel.offsetX, gamePanel.CHUNK_SIZE);
//                p.setX(p.getX() + currentDeltaX * p.getSpeed());
//                p.setY(p.getY() + currentDeltaY * p.getSpeed());
//
//                if (!hitAndMarkForRemoval(p, p.getOriginTower())) {
//                    nextProjectiles.add(p);
//                    nextDeltaX.add(currentDeltaX);
//                    nextDeltaY.add(currentDeltaY);
//                }
//            }
//        }
//
//        projectile.clear();
//        projectile.addAll(nextProjectiles);
//        nextProjectiles.clear();
//        deltaX.clear();
//        deltaX.addAll(nextDeltaX);
//        nextDeltaX.clear();
//        deltaY.clear();
//        deltaY.addAll(nextDeltaY);
//        nextDeltaY.clear();
//
//        timer[0]++;
//    }
//
//    //Autor: Neo
//    public void shootTower4(Graphics g) {
//        Image projectileImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/arrow.png"))).getImage();
//
//        for (int a = 0; a < gamePanel.towers4.size(); a++) {
//            if (gamePanel.towers4.get(a) != null && getTargetedEnemy(gamePanel.towers4.get(a)) != null) {
//                if (gamePanel.health > 0 && checkEnemyInRange(gamePanel.towers4.get(a)) && !gamePanel.wave.enemy.isEmpty()) {
//                    if (timer[1] % gamePanel.towers4.get(a).coolDown == 0) {
//                        projectile.add(new Projectile(gamePanel.towers4.get(a).getX(), gamePanel.towers4.get(a).getY(), gamePanel.towers4.get(a).shotSpeed, g, gamePanel.towers4.get(a)));
//                        deltaX.add((getTargetedEnemy(gamePanel.towers4.get(a)).getX() + 0.5) - gamePanel.towers4.get(a).getX());
//                        deltaY.add((getTargetedEnemy(gamePanel.towers4.get(a)).getY() + 0.5) - gamePanel.towers4.get(a).getY());
//                    }
//                }
//            }
//        }
//
//        List<Projectile> nextProjectiles = new ArrayList<>();
//        List<Double> nextDeltaX = new ArrayList<>();
//        List<Double> nextDeltaY = new ArrayList<>();
//
//        for (int i = 0; i < projectile.size(); i++) {
//            if (projectile.get(i) != null && projectile.get(i).getOriginTower().getClass() == Tower4.class) {
//                Projectile p = projectile.get(i);
//                double currentDeltaX = deltaX.get(i);
//                double currentDeltaY = deltaY.get(i);
//                double angle = Math.toDegrees(Math.atan2(currentDeltaY, currentDeltaX));
//
//                p.draw(g, getRotatedImage(projectileImage, angle), gamePanel.offsetX, gamePanel.CHUNK_SIZE);
//                p.setX(p.getX() + currentDeltaX * p.getSpeed());
//                p.setY(p.getY() + currentDeltaY * p.getSpeed());
//
//                if (!hitAndMarkForRemoval(p, p.getOriginTower())) {
//                    nextProjectiles.add(p);
//                    nextDeltaX.add(currentDeltaX);
//                    nextDeltaY.add(currentDeltaY);
//                }
//            }
//        }
//
//        projectile.clear();
//        projectile.addAll(nextProjectiles);
//        nextProjectiles.clear();
//        deltaX.clear();
//        deltaX.addAll(nextDeltaX);
//        nextDeltaX.clear();
//        deltaY.clear();
//        deltaY.addAll(nextDeltaY);
//        nextDeltaY.clear();
//
//        timer[1]++;
//    }

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

        for (int a = 0; a < gamePanel.wave.enemy.size() && targeting; a++) {
            if (gamePanel.wave.enemy.get(a) != null) {
                double tempDeltaX = tower.getX() - gamePanel.wave.enemy.get(a).getX();
                double tempDeltaY = tower.getY() - gamePanel.wave.enemy.get(a).getY();
                double distance = Math.sqrt(tempDeltaX * tempDeltaX + tempDeltaY * tempDeltaY);

                if (distance <= smallestdistance) {
                    smallestdistance = distance;
                }

                if (distance <= tower.getRange() && distance == smallestdistance) {
                    targetedEnemy = gamePanel.wave.enemy.get(a);
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
        List<Enemy> enemiesToProcess = new ArrayList<>(gamePanel.wave.enemy);

        for (Enemy enemy : enemiesToProcess) {
            if (p != null && enemy != null && enemy.checkCollision(p, gamePanel.getCHUNK_SIZE())) {
                enemy.takeDamage(tower.getDamage());
                if (enemy.getHealth() <= 0) {
                    gamePanel.wave.removeEnemy(enemy);
                    gamePanel.money += 50;
                }
                return true;
            }
        }
        return false;
    }
}