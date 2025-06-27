package src;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

public class Shot {

    private final GamePanel gamePanel;
    private final List<Projectile> projectile = new ArrayList<>();
    private final List<Double> deltaX = new ArrayList<>();
    private final List<Double> deltaY = new ArrayList<>();

    public Shot(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void run(Graphics g) {
        runProjectiles(g);
    }

    // Autor: Neo, Luca
    private void runProjectiles(Graphics g) {
        List<Projectile> nextProjectiles = new ArrayList<>();
        List<Double> nextDeltaX = new ArrayList<>();
        List<Double> nextDeltaY = new ArrayList<>();

        Image projectile1Image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/goatAttack.png"))).getImage();
        Image projectile2Image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/magicSpell.png"))).getImage();
        Image projectile4Image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/arrow.png"))).getImage();
        Image projectile3Image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/magicSpellHeal.png"))).getImage();

        // Logik für das Erstellen neuer Projektile für Tower1
        for (int a = 0; a < gamePanel.getTower1Arraylist().size(); a++) {
            Tower1 tower = gamePanel.getSpecificTower1(a);
            if (tower != null && getTargetedEnemy(tower) != null && !gamePanel.getPaused()) {
                if (gamePanel.getHealth() > 0 && checkEnemyInRange(tower) && !gamePanel.getWave().getEnemyArrayList().isEmpty()) {
                    if (gamePanel.getSpecificTower1(a).getTimer() % tower.getCoolDown() == 0) {
                        projectile.add(new Projectile(gamePanel.getSpecificTower1(a).getX(), gamePanel.getSpecificTower1(a).getY(), gamePanel.getSpecificTower1(a).getShotSpeed(), gamePanel.getSpecificTower1(a)));
                        deltaX.add((getTargetedEnemy(gamePanel.getSpecificTower1(a)).getX() + 0.5) - gamePanel.getSpecificTower1(a).getX());
                        deltaY.add((getTargetedEnemy(gamePanel.getSpecificTower1(a)).getY() + 0.5) - gamePanel.getSpecificTower1(a).getY());
                    }
                }
            }
            gamePanel.getSpecificTower1(a).setTimer(gamePanel.getSpecificTower1(a).getTimer() + 1);
        }

        // Logik für das Erstellen neuer Projektile für Tower2
        for (int a = 0; a < gamePanel.getTower2Arraylist().size(); a++) {
            Tower2 tower = gamePanel.getSpecificTower2(a);
            if (tower != null && getTargetedEnemy(tower) != null && !gamePanel.getPaused()) {
                if (gamePanel.getHealth() > 0 && checkEnemyInRange(tower) && !gamePanel.getWave().getEnemyArrayList().isEmpty()) {
                    if (gamePanel.getSpecificTower2(a).getTimer() % tower.getCoolDown() == 0) {
                        projectile.add(new Projectile(gamePanel.getSpecificTower2(a).getX(), gamePanel.getSpecificTower2(a).getY(), gamePanel.getSpecificTower2(a).getShotSpeed(), gamePanel.getSpecificTower2(a)));
                        deltaX.add((getTargetedEnemy(gamePanel.getSpecificTower2(a)).getX() + 0.5) - gamePanel.getSpecificTower2(a).getX());
                        deltaY.add((getTargetedEnemy(gamePanel.getSpecificTower2(a)).getY() + 0.5) - gamePanel.getSpecificTower2(a).getY());
                    }
                }
            }
            gamePanel.getSpecificTower2(a).setTimer(gamePanel.getSpecificTower2(a).getTimer() + 1);
        }

        // Logik für das Erstellen neuer Projektile für Tower3
        for (int a = 0; a < gamePanel.getTower3Arraylist().size(); a++) {
            Tower3 tower = gamePanel.getSpecificTower3(a);
            if (tower != null && !gamePanel.getPaused()) {
                if (gamePanel.getHealth() > 0 && !gamePanel.getWave().getEnemyArrayList().isEmpty()) {
                    if (gamePanel.getSpecificTower3(a).getTimer() % tower.getCoolDown() == 0) {
                        projectile.add(new Projectile(gamePanel.getSpecificTower3(a).getX(), gamePanel.getSpecificTower3(a).getY(), gamePanel.getSpecificTower3(a).getShotSpeed(), gamePanel.getSpecificTower3(a)));
                        deltaX.add(4 - gamePanel.getSpecificTower3(a).getX());
                        deltaY.add(15 - gamePanel.getSpecificTower3(a).getY());
                    }
                }
            }
            gamePanel.getSpecificTower3(a).setTimer(gamePanel.getSpecificTower3(a).getTimer() + 1);
        }

        // Logik für das Erstellen neuer Projektile für Tower4
        for (int a = 0; a < gamePanel.getTower4Arraylist().size(); a++) {
            Tower4 tower = gamePanel.getSpecificTower4(a);
            if (tower != null && getTargetedEnemy(tower) != null && !gamePanel.getPaused()) {
                if (gamePanel.getHealth() > 0 && checkEnemyInRange(tower) && !gamePanel.getWave().getEnemyArrayList().isEmpty()) {
                    if (gamePanel.getSpecificTower4(a).getTimer() % tower.getCoolDown() == 0) {
                        projectile.add(new Projectile(gamePanel.getSpecificTower4(a).getX(), gamePanel.getSpecificTower4(a).getY() , gamePanel.getSpecificTower4(a).getShotSpeed(), gamePanel.getSpecificTower4(a)));
                        deltaX.add((getTargetedEnemy(gamePanel.getSpecificTower4(a)).getX() + 0.5) - gamePanel.getSpecificTower4(a).getX());
                        deltaY.add((getTargetedEnemy(gamePanel.getSpecificTower4(a)).getY() + 0.5) - gamePanel.getSpecificTower4(a).getY());
                    }
                }
            }
            gamePanel.getSpecificTower4(a).setTimer(gamePanel.getSpecificTower4(a).getTimer() + 1);
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
                } else if (p.getOriginTower().getClass() == Tower2.class) {
                    currentProjectileImage = projectile2Image;
                } else if (p.getOriginTower().getClass() == Tower3.class) {
                   currentProjectileImage = projectile3Image;
                } else if (p.getOriginTower().getClass() == Tower4.class) {
                    currentProjectileImage = projectile4Image;
                } else {
                    currentProjectileImage = projectile4Image;
                }

                p.draw(g, getRotatedImage(currentProjectileImage, angle), gamePanel.getOffsetX(), gamePanel.getCHUNK_SIZE());
                if (!gamePanel.getPaused()) {
                    p.setX(p.getX() + currentDeltaX * p.getSpeed());
                    p.setY(p.getY() + currentDeltaY * p.getSpeed());

                    if (p.getOriginTower().getClass() != Tower3.class && !hitAndMarkForRemoval(p, p.getOriginTower())) {
                        if (p.getX() >= 0 && p.getY() >= 0 && p.getX() < gamePanel.getWidth() && p.getY() < gamePanel.getHeight()) {
                            nextProjectiles.add(p);
                            nextDeltaX.add(currentDeltaX);
                            nextDeltaY.add(currentDeltaY);
                        }
                    }

                    if (p.getOriginTower().getClass() == Tower3.class) {
                        nextProjectiles.add(p);
                        nextDeltaX.add(currentDeltaX);
                        nextDeltaY.add(currentDeltaY);
                        System.out.println("Ja");
                        if(p.getX() >= 4 * gamePanel.getCHUNK_SIZE() && p.getX() <= 7 * gamePanel.getCHUNK_SIZE() && p.getY()>=14 * gamePanel.getCHUNK_SIZE()) {
                            System.out.println("Jaja");
                            if(gamePanel.getHealth()<=140){gamePanel.heal(10);}
                            if(gamePanel.getHealth()>140&& gamePanel.getHealth()<150) {gamePanel.heal(150-gamePanel.getHealth());}
                        }

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

        Graphics g = originalImage.createGraphics();
        g.drawImage(projectile, 0, 0, null);
        g.dispose();

        return op.filter(originalImage, rotatedImage);
    }

    // Autor: Neo
    private Enemy getTargetedEnemy(Tower tower) {
        Enemy closestEnemy = null;
        double smallestDistance = Double.MAX_VALUE;

        for (int a = 0; a < gamePanel.getWave().getEnemyArrayList().size(); a++) {
            Enemy enemy = gamePanel.getWave().getSpecificEnemy(a);

            if (enemy != null) {
                double tempDeltaX = tower.getX() - gamePanel.getWave().getSpecificEnemy(a).getX();
                double tempDeltaY = tower.getY() - gamePanel.getWave().getSpecificEnemy(a).getY();
                double distance = Math.sqrt(tempDeltaX * tempDeltaX + tempDeltaY * tempDeltaY);

                if (distance <= tower.getRange()) {
                    return enemy;
                }

                if (distance < smallestDistance) {
                    smallestDistance = distance;
                    closestEnemy = enemy;
                }
            }
        }
        return closestEnemy;
    }

    // Autor: Luca
    private boolean checkEnemyInRange(Tower tower) {
        Enemy target = getTargetedEnemy(tower);

        if (target == null) {
            return false;
        }

        double tempDeltaX = tower.getX() - target.getX();
        double tempDeltaY = tower.getY() - target.getY();
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
                    gamePanel.setMoney(gamePanel.getMoney() + 30);
                    if (enemiesToProcess.size() - 1 == 0) {
                        gamePanel.getWave().setWavesCompleted(gamePanel.getWave().getWavesCompleted() + 1);
                        gamePanel.getStartButton().setEnabled(true);
                        resetShot();
                        if (gamePanel.getWave().getWavesCompleted() == 5) {
                            gamePanel.showVictoryScreen();
                        }
                    }
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
    }
}