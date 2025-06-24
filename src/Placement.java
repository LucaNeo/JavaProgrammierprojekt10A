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
        if (!gamePanel.getPlacingTower()) return;

        int gridX = e.getX() / gamePanel.getCHUNK_SIZE() - 1;
        int gridY = e.getY() / gamePanel.getCHUNK_SIZE();
        // Prüfen ob das Feld gültig, frei und bezahlbar ist
        if (gridX >= 0 && gridX < gamePanel.getCols() &&
                gridY >= 0 && gridY < gamePanel.getRows() &&
                gamePanel.getPlaceable(gridX, gridY)) {
            // Neuen Turm mit PNG erstellen
            if (gamePanel.getSelectedTowerType() == 1 && gamePanel.getMoney() >= 250 * getCostMultiplier()) {
                gamePanel.getTower1Arraylist().add(new Tower1(gridX, gridY));
                gamePanel.setMoney((int)(gamePanel.getMoney() - 250 * getCostMultiplier()));  // Geld abziehen
                gamePanel.setPlaceable(gridX, gridY, false);  // Feld als belegt markieren
            }
            if (gamePanel.getSelectedTowerType() == 2 && gamePanel.getMoney() >= 350 * getCostMultiplier()) {
                gamePanel.getTower2Arraylist().add(new Tower2(gridX, gridY));
                gamePanel.setMoney((int)(gamePanel.getMoney() - 350 * getCostMultiplier())); // Geld abziehen
                gamePanel.setPlaceable(gridX, gridY, false);
            }
            if (gamePanel.getSelectedTowerType() == 3 && gamePanel.getMoney() >= 500 * getCostMultiplier()) {
                gamePanel.getTower3Arraylist().add(new Tower3(gridX, gridY));
                gamePanel.setMoney((int)(gamePanel.getMoney() - 500 * getCostMultiplier())); // Geld abziehen
                gamePanel.setPlaceable(gridX, gridY, false);
            }
            if (gamePanel.getSelectedTowerType() == 4 && gamePanel.getMoney() >= 60 * getCostMultiplier()) {
                gamePanel.getTower4Arraylist().add(new Tower4(gridX, gridY));
                gamePanel.setMoney((int) (gamePanel.getMoney() - 60 * getCostMultiplier())); // Geld abziehen
                gamePanel.setPlaceable(gridX, gridY, false);
            }
            if (gamePanel.getSelectedTowerType() == 5 && gamePanel.getMoney() >= 400 * getCostMultiplier()) {
                gamePanel.getTower5Arraylist().add(new Tower5(gridX, gridY, gamePanel));
                gamePanel.setMoney((int) (gamePanel.getMoney() - 400 * getCostMultiplier())); // Geld abziehen
                gamePanel.setPlaceable(gridX, gridY, false);
            }
            gamePanel.setSelectedTowerType(0);
            gamePanel.setPlacingTower(false);
            gamePanel.repaint();
        }
    }
    private double getCostMultiplier() {
        return switch (DifficultySettings.getCurrentDifficulty()){
            case HARD -> 1.2; //20%
            case MEDIUM -> 1; // default
            case EASY -> 0.8; // -20%
        };
    }
}
