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
@Override //TODO Tower aktulesiern wenn fester größe sich ändert
public void mouseClicked(MouseEvent e) {
    if (!gamePanel.placingTower) return;

    int gridX = e.getX() / gamePanel.CHUNK_SIZE;
    int gridY = e.getY() / gamePanel.CHUNK_SIZE;
    // Prüfen ob das Feld gültig, frei und bezahlbar ist
    if (gridX >= 0 && gridX < gamePanel.cols &&
            gridY >= 0 && gridY < gamePanel.rows &&
            gamePanel.placeable[gridY][gridX]) {
        // Neuen Turm mit PNG erstellen
        if (gamePanel.selectedTowerType == 1 && gamePanel.money >= 250) {
            gamePanel.towers1.add(new Tower1(gridX, gridY));
            gamePanel.money -= 250;  // Geld abziehen
            gamePanel.placeable[gridY][gridX] = false;  // Feld als belegt markieren
        }
        if (gamePanel.selectedTowerType == 2 && gamePanel.money >= 350) {
            gamePanel.towers2.add(new Tower2(gridX, gridY));
            gamePanel.money -= 350; // Geld abziehen
            gamePanel.placeable[gridY][gridX] = false;
        }
        if (gamePanel.selectedTowerType == 3 && gamePanel.money >= 500) {
            gamePanel.towers3.add(new Tower3(gridX, gridY));
            gamePanel.money -= 500; // Geld abziehen
            gamePanel.placeable[gridY][gridX] = false;
        }
        if (gamePanel.selectedTowerType == 4 && gamePanel.money >= 60) {
            gamePanel.towers4.add(new Tower4(gridX, gridY));
            gamePanel.money -= 60; // Geld abziehen
            gamePanel.placeable[gridY][gridX] = false;
        }
        if (gamePanel.selectedTowerType == 5 && gamePanel.money >= 400) {
            gamePanel.towers5.add(new Tower5(gridX, gridY));
            gamePanel.money -=400; // Geld abziehen
            gamePanel.placeable[gridY][gridX] = false;
        }
        gamePanel.selectedTowerType = 0;
        gamePanel.placingTower = false;
        gamePanel.repaint();
}

}
}
