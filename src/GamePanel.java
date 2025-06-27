package src;
// Autor Jakob/Luca
// TODO Settings verbessern
// TODO shortcuts,Undo/Redo
// TODO pause Button
// TODO Sounds?

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamePanel extends JPanel {

    private final int CHUNK_SIZE = 72;
    private final int rows = 15;
    private final int cols = 15;
    private boolean[][] placeable;
    private boolean[][] isPathway;
    private final int offsetX = 100;
    private boolean placingTower = false;  // Ist Platzierungsmodus aktiv?
    private int money; // StartGeld
    private int health; //hp
    private int selectedTowerType = 0;    // 1 = Tower1, 2 = Tower2 etc.se;
    private boolean paused = false;

    private final List<Tower1> towers1 = new ArrayList<>();
    private final List<Tower2> towers2 = new ArrayList<>();
    private final List<Tower3> towers3 = new ArrayList<>();
    private final List<Tower4> towers4 = new ArrayList<>();
    private final List<Tower5> towers5 = new ArrayList<>();
    private final Wave wave = new Wave();
    private final Shot shot = new Shot(this);
    private final Pathfinding pathFinding = new Pathfinding(this);
    private Timer gameLoop; // aktive runde ?

    private final JFrame parentFrame;
    private JButton startButton;
    private JButton restartButton;
    private JButton pauseButton;
    private JButton returnToMenu;
    private JButton tower1Button;
    private JButton tower2Button;
    private JButton tower3Button;
    private JButton tower4Button;
    private JButton tower5Button;

    ImageIcon pauseIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/pauseButton.png")));
    Image pauseImage = pauseIcon.getImage().getScaledInstance(290, 100, Image.SCALE_SMOOTH);
    ImageIcon pauseIconPressed = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/pauseButtonPressed.png")));
    Image pauseImagePressed = pauseIconPressed.getImage().getScaledInstance(290, 100, Image.SCALE_SMOOTH);

    public GamePanel(JFrame frame) {
        this.parentFrame = frame;
        initGrid();
        setUI();
        startGameLoop();
        setFocusable(true); // Shortcuts möglich machen (Press Key Event)
        checkDifficulty();
    }
    private void checkDifficulty(){
        switch (DifficultySettings.getCurrentDifficulty()){
            case EASY:      health = 100;
                            money = 2500;
                            break;
            case MEDIUM:    health = 50;
                            money = 2000;
                            break;
            case HARD:      health = 1;
                            money = 1000;
        }
    }

    private void startGameLoop() {
        gameLoop = new Timer(16, _ -> {
            updateGame();
            repaint();
        });
        gameLoop.start();
    }

    private void updateGame() {
        for (int i = 0; i < wave.getEnemyArrayList().size(); i++) {
            if (wave.getEnemyArrayList().get(i) != null) {
                if (wave.getSpecificEnemy(i).getX() == 4 && wave.getSpecificEnemy(i).getY() >= 14) {
                    if (health - wave.getSpecificEnemy(i).getDamageToCastle() < 0) {
                        health = 0;
                    } else {
                        health -= wave.getSpecificEnemy(i).getDamageToCastle();
                    }
                    wave.clearWave();
                    startButton.setEnabled(true);
                    if (health == 0) {
                        showDefeatScreen();
                    }
                    i = wave.getEnemyArrayList().size();
                }
            }
        }
    }

    private void pauseGame() {
        if(!paused) {
            paused = true;
            startButton.setEnabled(false);
            restartButton.setEnabled(false);
            pauseButton.setIcon(new ImageIcon(pauseImagePressed));
        } else {
            paused = false;
            startButton.setEnabled(true);
            restartButton.setEnabled(true);
            pauseButton.setIcon(new ImageIcon(pauseImage));
        }
    }

    public void showVictoryScreen() {
        ImageIcon victoryIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/victoryScreen.png")));
        JButton victoryButton = new JButton(victoryIcon);
        victoryButton.setBounds(0, 0, 1920, 1080);
        victoryButton.setOpaque(false);
        victoryButton.setContentAreaFilled(false);
        victoryButton.setBorderPainted(false);
        victoryButton.addActionListener(_ -> returnToMenu());
        add(victoryButton);

        remove(startButton);
        remove(restartButton);
        remove(pauseButton);
        remove(returnToMenu);
        remove(tower1Button);
        remove(tower2Button);
        remove(tower3Button);
        remove(tower4Button);
        remove(tower5Button);

        gameLoop.stop();
    }

    public void showDefeatScreen() {
        ImageIcon defeatIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/defeatScreen.png")));
        JButton defeatButton = new JButton(defeatIcon);
        defeatButton.setBounds(0, 0, 1920, 1080);
        defeatButton.setOpaque(false);
        defeatButton.setContentAreaFilled(false);
        defeatButton.setBorderPainted(false);
        defeatButton.addActionListener(_ -> returnToMenu());
        add(defeatButton);

        remove(startButton);
        remove(restartButton);
        remove(pauseButton);
        remove(returnToMenu);
        remove(tower1Button);
        remove(tower2Button);
        remove(tower3Button);
        remove(tower4Button);
        remove(tower5Button);

        gameLoop.stop();
    }

    private void initGrid() {
        placeable = new boolean[cols][rows];
        isPathway = new boolean[cols][rows];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                placeable[x][y] = true;
                isPathway[x][y] = false;
            }
        }
        // col 2 nach unten
        for (int y = 0; y < 8; y++) {
            placeable[2][y] = false;
            isPathway[2][y] = true;
        }
        // row 7 nach rechts
        for (int x = 3; x < 7; x++) {
            placeable[x][7] = false;
            isPathway[x][7] = true;
        }
        // col 6 nach oben
        for (int y = 7; y > 3; y--) {
            placeable[6][y] = false;
            isPathway[6][y] = true;
        }
        // row 4 nach rechts
        for (int x = 6; x < 11; x++) {
            placeable[x][4] = false;
            isPathway[x][4] = true;
        }
        // col 10 nach unten
        for (int y = 4; y < 11; y++) {
            placeable[10][y] = false;
            isPathway[10][y] = true;
        }
        // row 10 nach links
        for (int x = 10; x > 3; x--) {
            placeable[x][10] = false;
            isPathway[x][10] = true;
        }
        // col 4 nach unten
        for (int y = 10; y < 15; y++) {
            placeable[4][y] = false;
            isPathway[4][y] = true;
        }
    }

    private void returnToMenu() {
        if (gameLoop != null) gameLoop.stop();
        parentFrame.setContentPane(new MainMenu(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void setUI() {
        setLayout(null); // Layout des GamePanel auf null setzen

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(70, 70, 90));
        buttonPanel.setPreferredSize(new Dimension(getWidth()+50, 80));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));

        // Position und Größe des buttonPanel festlegen
        int panelWidth = getWidth();
        int panelHeight = 80;
        int panelX = 0;
        int panelY = getHeight() - panelHeight;
        buttonPanel.setBounds(panelX, panelY, panelWidth, panelHeight);

        add(buttonPanel);

        // Tower-Auswahl-Leiste
        ImageIcon originalIcon1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-guard.png")));
        Image scaledImage1 = originalIcon1.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        tower1Button = new JButton(scaledIcon1);
        tower1Button.setBounds(1420,28,64,64);
        tower1Button.setOpaque(false);
        tower1Button.setContentAreaFilled(false);
        tower1Button.setBorderPainted(false);
        tower1Button.addActionListener(_ -> {
            placingTower = true;
            selectedTowerType = 1;
            repaint();
        });
        add(tower1Button);

        ImageIcon originalIcon2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-ketoon.png")));
        Image scaledImage2 = originalIcon2.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        tower2Button = new JButton(scaledIcon2);
        tower2Button.setBounds(1520,32,64,64);
        tower2Button.setOpaque(false);
        tower2Button.setContentAreaFilled(false);
        tower2Button.setBorderPainted(false);
        tower2Button.addActionListener(_ -> {
            placingTower = true;
            selectedTowerType = 2;
            repaint();
        });
        add(tower2Button);

        ImageIcon originalIcon3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-priest.png")));
        Image scaledImage3 = originalIcon3.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(scaledImage3);
        tower3Button = new JButton(scaledIcon3);
        tower3Button.setBounds(1602,25,64,64);
        tower3Button.setOpaque(false);
        tower3Button.setContentAreaFilled(false);
        tower3Button.setBorderPainted(false);
        tower3Button.addActionListener(_ -> {
            placingTower = true;
            selectedTowerType = 3;
            repaint();
        });
        add(tower3Button);

        ImageIcon originalIcon4 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-archer.png")));
        Image scaledImage4 = originalIcon4.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4= new ImageIcon(scaledImage4);
        tower4Button = new JButton(scaledIcon4);
        tower4Button.setBounds(1685,25,64,64);
        tower4Button.setOpaque(false);
        tower4Button.setContentAreaFilled(false);
        tower4Button.setBorderPainted(false);
        tower4Button.addActionListener(_ -> {
            placingTower = true;
            selectedTowerType = 4;
            repaint();
        });
        add(tower4Button);

        ImageIcon originalIcon5 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-trader.png")));
        Image scaledImage5 = originalIcon5.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon5= new ImageIcon(scaledImage5);
        tower5Button = new JButton(scaledIcon5);
        tower5Button.setBounds(1780,25,64,64);
        tower5Button.setOpaque(false);
        tower5Button.setContentAreaFilled(false);
        tower5Button.setBorderPainted(false);
        tower5Button.addActionListener(_ -> {
            placingTower = true;
            selectedTowerType = 5;
            repaint();
        });
        add(tower5Button);

        addMouseListener(new Placement(this));

        ImageIcon returnIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/returnToMenu.png")));
        Image returnImage = returnIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledReturnIcon = new ImageIcon(returnImage);
        returnToMenu = new JButton(scaledReturnIcon);
        returnToMenu.setBounds(8, 10, 64, 64);
        returnToMenu.setOpaque(false);
        returnToMenu.setContentAreaFilled(false);
        returnToMenu.setBorderPainted(false);
        returnToMenu.addActionListener(_ -> returnToMenu());
        add(returnToMenu);

        //startButton
        ImageIcon startIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/startButton.png")));
        Image startImage = startIcon.getImage().getScaledInstance(600, 200, Image.SCALE_SMOOTH);
        //whenPressed
        ImageIcon pressedStartIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/startButtonPressed.png")));
        Image pressedStartImage = pressedStartIcon.getImage().getScaledInstance(600, 200, Image.SCALE_SMOOTH);
        startButton = new JButton(new ImageIcon(startImage));
        startButton.setBounds(1255, 740, 600, 200);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setDisabledIcon(new ImageIcon(pressedStartImage));
        startButton.addActionListener(_ -> {
            if (startButton.isEnabled() && health != 0){
                if (wave.getWavesCompleted() == 0) {
                    wave.createWave1();
                } else if (wave.getWavesCompleted() == 1) {
                    wave.createWave2();
                } else if (wave.getWavesCompleted() == 2) {
                    wave.createWave3();
                } else if (wave.getWavesCompleted() == 3) {
                    wave.createWave4();
                } else if (wave.getWavesCompleted() == 4) {
                    wave.createWave5();
                } else if (wave.getWavesCompleted() == 5) {
                    System.out.println("Victory!");
                }

                startButton.setEnabled(false);
            }
        });
        add(startButton);

        //restartButton
        ImageIcon restartIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/restartButton.png")));
        Image restartImage = restartIcon.getImage().getScaledInstance(290, 100, Image.SCALE_SMOOTH);
        ImageIcon restartIconPressed = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/restartButtonPressed.png")));
        Image restartImagePressed = restartIconPressed.getImage().getScaledInstance(290, 100, Image.SCALE_SMOOTH);
        restartButton = new JButton(new ImageIcon(restartImage));
        restartButton.setBounds(1565, 960, 290, 100);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.setDisabledIcon(new ImageIcon(restartImagePressed));
        restartButton.addActionListener(_ -> {
            checkDifficulty();
            towers1.clear();
            towers2.clear();
            towers3.clear();
            towers4.clear();
            towers5.clear();
            wave.getEnemyArrayList().clear();
            wave.setWavesCompleted(0);
            shot.resetShot();

            initGrid();
            startButton.setEnabled(true);
            repaint();
        });
        add(restartButton);

        //pauseButton
        pauseButton = new JButton(new ImageIcon(pauseImage));
        pauseButton.setBounds(1255, 960, 290, 100);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setBorderPainted(false);
        pauseButton.addActionListener(_ -> {
            pauseGame();
            repaint();
        });
        add(pauseButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image grassImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/grass.png"))).getImage();
        Image pathImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/pathway.png"))).getImage();
        Image towerFrame = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/towerFrame.png"))).getImage();
        Image separator = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/separator.png"))).getImage();
        Image gateway = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/gateway2.png"))).getImage();
        Image banner = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/banner.png"))).getImage();
        Image background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/background.png"))).getImage();

        g.drawImage(background, 0, 0, null);

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if (isPathway[x][y]) {
                    g.drawImage(pathImage, x * CHUNK_SIZE + offsetX, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
                } else {
                    g.drawImage(grassImage, x * CHUNK_SIZE + offsetX, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
                }
            }
        }

        //  Türme zeichnen
        for (Tower1 tower1 : towers1) {
            tower1.draw(g, CHUNK_SIZE, offsetX);
        }
        for (Tower2 tower2 : towers2) {
            tower2.draw(g, CHUNK_SIZE, offsetX);
        }
        for (Tower3 tower3 : towers3) {
            tower3.draw(g, CHUNK_SIZE, offsetX);
        }
        for (Tower4 tower4 : towers4) {
            tower4.draw(g, CHUNK_SIZE, offsetX);
        }
        for (Tower5 tower5 : towers5) {
            tower5.draw(g, CHUNK_SIZE, offsetX);
        }

        //draw ButtonBackground
        g.drawImage(separator, 80, 0,null);
        g.drawImage(separator,1178,0,null);
        g.drawImage(gateway,345,14 * CHUNK_SIZE + 2,150,70,null);
        g.drawImage(banner,5 * CHUNK_SIZE,0,null);

        if (gameLoop.isRunning()) {
            g.drawImage(towerFrame, 1405, 10,null);
        }

        pathFinding.run((Graphics2D) g);
        shot.run(g);
        drawHUD(g,g);
        drawGrid(g);
    }

    private void drawGrid(Graphics g) { //teilt map in chunks
        Image placementImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isPlaceable.png"))).getImage();
        Image nonPlaceableImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isNotPlaceable.png"))).getImage();

        if (!placingTower) {
            return;
        }
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if (placeable[x][y]) {
                    g.drawImage(placementImage, x * CHUNK_SIZE + offsetX, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
                }
                if (!placeable[x][y]) {
                    g.drawImage(nonPlaceableImage, x * CHUNK_SIZE + offsetX, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
                }
            }
        }
    }

    private void drawHUD(Graphics g, Graphics g2) { // HP und Geld anzeige
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("$" + money, 1240, 60);
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("♥️" + health, 1240, 100);
    }
    private double getCostMultiplier() {
        // Multiplikator basierend auf Schwierigkeit
        return switch (DifficultySettings.getCurrentDifficulty()) {
            case EASY -> 0.8;  // Türme 20% günstiger
            case MEDIUM -> 1.0; // Standard
            case HARD -> 1.2;   // Türme 20% teurer
        };
    }
    public int getTowerCost(int towerType) {
        int baseCost = switch (towerType) {
            case 1 -> 250;
            case 2 -> 350;
            case 3 -> 500;
            case 4 -> 60;
            case 5 -> 400;
            default -> 0;
        };
        return (int)(baseCost * getCostMultiplier());
    }
    public int getMoney() { return money; }
    public int getHealth() { return health; }
    public void heal(int amount) {health += amount;}
    public int getOffsetX() { return offsetX; }
    public int getCHUNK_SIZE() { return CHUNK_SIZE; }
    public int getHeight() { return 2000; }
    public int getWidth() { return 1920; }
    public int getCols() { return cols; }
    public int getRows() { return rows; }
    public int getSelectedTowerType() { return selectedTowerType; }
    public boolean getPlaceable(int col, int row) { return placeable[col][row]; }
    public boolean getPlacingTower() { return placingTower; }
    public boolean getPaused() { return paused; }
    public List<Tower1> getTower1Arraylist() { return towers1; }
    public List<Tower2> getTower2Arraylist() { return towers2; }
    public List<Tower3> getTower3Arraylist() { return towers3; }
    public List<Tower4> getTower4Arraylist() { return towers4; }
    public List<Tower5> getTower5Arraylist() { return towers5; }
    public Tower1 getSpecificTower1(int index) { return towers1.get(index); }
    public Tower2 getSpecificTower2(int index) { return towers2.get(index); }
    public Tower3 getSpecificTower3(int index) { return towers3.get(index); }
    public Tower4 getSpecificTower4(int index) { return towers4.get(index); }
    public Wave getWave() { return wave; }
    public JButton getStartButton() { return startButton; }

    public void setMoney(int value) { money = value; }
    public void setPlaceable(int col, int row, boolean bool) { placeable[col][row] = bool; }
    public void setPlacingTower(boolean bool) { placingTower = bool; }
    public void setSelectedTowerType(int value) { selectedTowerType = value; }
}