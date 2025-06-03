package src;
// Autor Jakob
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Placement extends MouseAdapter {

    private final GamePanel gamePanel;

    public Placement(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gamePanel.placingTower) return;

        int gridX = e.getX() / gamePanel.CHUNK_SIZE - 1;
        int gridY = e.getY() / gamePanel.CHUNK_SIZE;
        // Prüfen ob das Feld gültig, frei und bezahlbar ist
        if (gridX >= 0 && gridX < gamePanel.cols &&
                gridY >= 0 && gridY < gamePanel.rows &&
                gamePanel.placeable[gridX][gridY]) {
            // Neuen Turm mit PNG erstellen
            if (gamePanel.selectedTowerType == 1 && gamePanel.money >= 250) {
                gamePanel.towers1.add(new Tower1(gridX, gridY));
                gamePanel.money -= 250;  // Geld abziehen
                gamePanel.placeable[gridX][gridY] = false;  // Feld als belegt markieren
            }
            if (gamePanel.selectedTowerType == 2 && gamePanel.money >= 350) {
                gamePanel.towers2.add(new Tower2(gridX, gridY));
                gamePanel.money -= 350; // Geld abziehen
                gamePanel.placeable[gridX][gridY] = false;
            }
            if (gamePanel.selectedTowerType == 3 && gamePanel.money >= 500) {
                gamePanel.towers3.add(new Tower3(gridX, gridY));
                gamePanel.money -= 500; // Geld abziehen
                gamePanel.placeable[gridX][gridY] = false;
            }
            if (gamePanel.selectedTowerType == 4 && gamePanel.money >= 60) {
                gamePanel.towers4.add(new Tower4(gridX, gridY));
                gamePanel.money -= 60; // Geld abziehen
                gamePanel.placeable[gridX][gridY] = false;
            }
            if (gamePanel.selectedTowerType == 5 && gamePanel.money >= 400) {
                gamePanel.towers5.add(new Tower5(gridX, gridY));
                gamePanel.money -=400; // Geld abziehen
                gamePanel.placeable[gridX][gridY] = false;
            }
            gamePanel.selectedTowerType = 0;
            gamePanel.placingTower = false;
            gamePanel.repaint();
        }
    }
}
