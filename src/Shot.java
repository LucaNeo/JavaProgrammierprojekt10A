package src;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Shot {

    public GamePanel gamePanel;
    private double x;
    private double y;

    Shot(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void attack(Graphics g) {
        for (int i = 0; i < gamePanel.towers1.size(); i++) {
            Projectile[] shot = new Projectile[15];
            Enemy1 targetenemy;
            Image shotImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/goatAttack.png"))).getImage();
            double deltaX;
            double deltaY;
            for (int j = 0; j < gamePanel.wave.enemy1.length; j++) {
                if (gamePanel.wave.enemy1[j] != null) {
                    deltaX = gamePanel.towers1.get(i).x - gamePanel.wave.enemy1[j].x;
                    deltaY = gamePanel.towers1.get(i).y - gamePanel.wave.enemy1[j].y;
                    double rangelength = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    if (rangelength<=gamePanel.towers1.get(i).range*gamePanel.CHUNK_SIZE) {
                        targetenemy = gamePanel.wave.enemy1[j];

                        for (int a = 0; a < shot.length; a++) {

                            shot[a] = new Projectile(gamePanel.towers1.get(i).x, gamePanel.towers1.get(i).y, gamePanel.towers1.get(i).shotSpeed, g);
                            shot[a].x += (deltaX * ((double) gamePanel.towers1.get(i).shotSpeed / 5));
                            shot[a].y += (deltaY * ((double) gamePanel.towers1.get(i).shotSpeed / 5));
                            g.drawImage(shotImage, (int) (shot[a].x * gamePanel.CHUNK_SIZE), (int) (shot[a].y * gamePanel.CHUNK_SIZE),25, 25, null);
                            System.out.println(shot[a].x + " " + shot[a].y);
                        }
                    }
                }
            }
        }
    }
}
