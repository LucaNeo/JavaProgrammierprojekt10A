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
    double deltaX = 0;
    double deltaY = 0;


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

                deltaX = getTargetedEnemy(gamePanel.towers1.get(a)).getX() - gamePanel.towers1.get(a).getX();
                deltaY = getTargetedEnemy(gamePanel.towers1.get(a)).getY() - gamePanel.towers1.get(a).getY();

                if (timer1 % gamePanel.towers1.get(a).coolDown == 0 && gamePanel.health > 0) {
                    projectile.add(new Projectile(gamePanel.towers1.get(a).getX(), gamePanel.towers1.get(a).getY(), gamePanel.towers1.get(a).shotSpeed, g));
                }

                for (Projectile p : projectile) {
                    p.draw(g, projectileImage, 25, 25, gamePanel.offsetX, gamePanel.CHUNK_SIZE);
                    p.move(deltaX * gamePanel.towers1.get(a).shotSpeed, deltaY * gamePanel.towers1.get(a).shotSpeed);
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
            if (p != null && enemy.checkCollision(p, gamePanel.CHUNK_SIZE)) {
                p = null;
            }
        }
    }
}







//public GamePanel gamePanel;
                    //private double x;
                    //private double y;
                    //private double timer1 = 0;

                    //Shot(GamePanel gamePanel) {
                    //this.gamePanel = gamePanel;
                    //}

                    //public void run(Graphics g){
                    //shootTower1(g);
                    //}

                    //public void shootTower1(Graphics g) {
                    //final List<Projectile> projectile = new ArrayList<>();
                    //Image projectileImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/goatAttack.png"))).getImage();
                    //Enemy1 targetedEnemy;
                    //double deltaX = 0;
                    //double deltaY = 0;

                    //for (int a = 0; a < gamePanel.towers1.size(); a++) {
                    //if (gamePanel.towers1.get(a) != null) {
                    //for (int b = 0; b < gamePanel.wave.enemy1.length; b++) {
                    //if (gamePanel.wave.enemy1[b] != null) {
                    //double tempDeltaX = gamePanel.towers1.get(a).x - gamePanel.wave.enemy1[b].x;
                    //double tempDeltaY = gamePanel.towers1.get(a).y - gamePanel.wave.enemy1[b].y;
                    //double range = Math.sqrt(tempDeltaX * tempDeltaX + tempDeltaY * tempDeltaY);
                    //if (range <= gamePanel.towers1.get(a).range) {

                    //deltaX = -tempDeltaX;
                    //deltaY = -tempDeltaY;
                    //targetedEnemy = gamePanel.wave.enemy1[b];

                    //if (timer1 % gamePanel.towers1.get(a).coolDown == 0) {
                    //projectile.add(new Projectile(gamePanel.towers1.get(a).x, gamePanel.towers1.get(a).y, gamePanel.towers1.get(a).shotSpeed, g));
                    //}

                    //for (Projectile d : projectile) {
                    //d.draw(g, projectileImage, 100, 100, gamePanel.offsetX, gamePanel.CHUNK_SIZE);
                    //d.move(deltaX * gamePanel.towers1.get(a).shotSpeed, deltaY * gamePanel.towers1.get(a).shotSpeed);
                    //}

                    //System.out.println("X: " + deltaX * gamePanel.towers1.get(a).shotSpeed);
                    //System.out.println("Y: " + deltaY * gamePanel.towers1.get(a).shotSpeed);

                    //timer1++;
                    //System.out.println(timer1);
                    //}
                    //}
                    //}
                    //}
                    //}


//    public void attack(Graphics g) {
//        for (int i = 0; i < gamePanel.towers1.size(); i++) {
//            Projectile[] shot = new Projectile[15];
//            Enemy1 targetenemy;
//            Image shotImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/goatAttack.png"))).getImage();
//            double deltaX;
//            double deltaY;
//            for (int j = 0; j < gamePanel.wave.enemy1.length; j++) {
//                if (gamePanel.wave.enemy1[j] != null) {
//                    deltaX = gamePanel.towers1.get(i).x - gamePanel.wave.enemy1[j].x;
//                    deltaY = gamePanel.towers1.get(i).y - gamePanel.wave.enemy1[j].y;
//                    double rangelength = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
//                    if (rangelength<=gamePanel.towers1.get(i).range*gamePanel.CHUNK_SIZE) {
//                        targetenemy = gamePanel.wave.enemy1[j];
//
//                        for (int a = 0; a < shot.length; a++) {
//
//                            shot[a] = new Projectile(gamePanel.towers1.get(i).x, gamePanel.towers1.get(i).y, gamePanel.towers1.get(i).shotSpeed, g);
//                            shot[a].x += (deltaX * ((double) gamePanel.towers1.get(i).shotSpeed / 5));
//                            shot[a].y += (deltaY * ((double) gamePanel.towers1.get(i).shotSpeed / 5));
//                            g.drawImage(shotImage, (int) (shot[a].x * gamePanel.CHUNK_SIZE), (int) (shot[a].y * gamePanel.CHUNK_SIZE),25, 25, null);
//                            System.out.println(shot[a].x + " " + shot[a].y);
//                        }
//                    }
//                }
//            }
//        }
//    }


