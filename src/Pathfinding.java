package src;

import java.awt.*;

public class Pathfinding {

    private final GamePanel gamePanel;

    public Pathfinding(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void run(Graphics g){
        for (int a = 0; a < gamePanel.wave.enemy.size(); a++) {
            if (gamePanel.wave.enemy.get(a) != null) {
                gamePanel.wave.enemy.get(a).draw(g, gamePanel.offsetX, gamePanel.CHUNK_SIZE);
                gamePanel.wave.enemy.get(a).move();
            }
        }
    }
}
