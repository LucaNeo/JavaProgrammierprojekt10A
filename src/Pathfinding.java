package src;

import java.awt.*;

public class Pathfinding {

    private final GamePanel gamePanel;

    public Pathfinding(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void run(Graphics2D g2d){
        for (int a = 0; a < gamePanel.getWave().getEnemyArrayList().size(); a++) {
            if (gamePanel.getWave().getSpecificEnemy(a) != null) {
                gamePanel.getWave().getSpecificEnemy(a).draw(g2d, gamePanel.getOffsetX(), gamePanel.getCHUNK_SIZE());
                if (!gamePanel.getPaused()) {
                    gamePanel.getWave().getSpecificEnemy(a).move();
                }
            }
        }
    }
}