package src;
// Autor Jakob
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Placement extends MouseAdapter {

    private final GamePanel gamePanel;

    public Placement(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


//    public void mouseClicked(MouseEvent e) { // alte Version
//        int gridX = e.getX() / gamePanel.TILE_SIZE;
//        int gridY = e.getY() / gamePanel.TILE_SIZE;
//
//
//        if (gridX >= 0 && gridX < gamePanel.cols &&
//                gridY >= 0 && gridY < gamePanel.rows &&
//                gamePanel.placeableTiles[gridY][gridX] &&
//                gamePanel.money >= 30) {
//
//
//            Tower1 tower = new Tower1(gridX, gridY);
//            gamePanel.towers.add(tower);
//
//            gamePanel.placeableTiles[gridY][gridX] = false;
//
//
//            gamePanel.money -= 30;
//
//
//            gamePanel.repaint();
//        }
//    }
@Override
public void mouseClicked(MouseEvent e) {
    if (!gamePanel.placingTower) return;

    int gridX = e.getX() / gamePanel.TILE_SIZE;
    int gridY = e.getY() / gamePanel.TILE_SIZE;
    // Prüfen ob das Feld gültig, frei und bezahlbar ist
    if (gridX >= 0 && gridX < gamePanel.cols &&
            gridY >= 0 && gridY < gamePanel.rows &&
            gamePanel.placeableTiles[gridY][gridX]) {
        // Neuen Turm mit PNG erstellen
        if (gamePanel.selectedTowerType == 1 && gamePanel.money >= 30) {
            gamePanel.towers.add(new Tower1(gridX, gridY));
            gamePanel.money -= 30;  // Geld abziehen
        }
        if (gamePanel.selectedTowerType == 2 && gamePanel.money >= 50) {
            gamePanel.towers2.add(new Tower2(gridX, gridY));
            gamePanel.money -= 50; // Geld abziehen
        }

        gamePanel.placeableTiles[gridY][gridX] = false;  // Feld als belegt markieren
        gamePanel.placingTower = false; // Platzierungsmodus beenden
        gamePanel.selectedTowerType = 0;
        gamePanel.repaint(); // Spielfeld neu zeichnen
    }
}

}
