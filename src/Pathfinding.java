package src;

import java.awt.*;

public class Pathfinding {

    private final GamePanel gamePanel;

    public Pathfinding(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void run(Graphics g){
        for (int a = 0; a < gamePanel.wave.enemy1.length; a++) {
            if (gamePanel.wave.enemy1[a] != null) {
                gamePanel.wave.enemy1[a].draw(g, gamePanel.CHUNK_SIZE);
                gamePanel.wave.enemy1[a].move();
            }
        }
    }
}
