package src;

import java.awt.*;

public class Pathfinding {

    private final GamePanel gamePanel;

    public Pathfinding(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void run(Graphics g){
        for (int a = 0; a < gamePanel.wave.enemy1.size(); a++) {
            if (gamePanel.wave.enemy1.get(a) != null) {
                gamePanel.wave.enemy1.get(a).draw(g, gamePanel.offsetX, gamePanel.CHUNK_SIZE);
                gamePanel.wave.enemy1.get(a).move();
            }
        }
    }
}
