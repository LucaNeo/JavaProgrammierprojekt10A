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
    final int CHUNK_SIZE = 72;
    final int rows = 15;
    final int cols = 15;

    public boolean[][] placeable;
    public boolean[][] isPathway;
    public boolean placingTower = false;  // Ist Platzierungsmodus aktiv?
    public int offsetX = 100;
   // private final boolean gridEditorMode = false;
    final List<Tower1> towers1 = new ArrayList<>();
    final List<Tower2> towers2 = new ArrayList<>();
    final List<Tower3> towers3 = new ArrayList<>();
    final List<Tower4> towers4 = new ArrayList<>();
    final List<Tower5> towers5 = new ArrayList<>();
    public Wave wave = new Wave();

    private Timer gameLoop; // aktive runde ?
    int money = 1000; // StartGeld
    int health = 100; //hp
    int selectedTowerType = 0;    // 1 = Tower1, 2 = Tower2 etc.
    boolean waveStarted = false;

    private final JFrame parentFrame;
    private final Pathfinding pathFinding = new Pathfinding(this);
    private final Shot shot = new Shot(this);
    JButton startButton;

    public GamePanel(JFrame frame) {
        this.parentFrame = frame;
        initGrid();
        setUI();
        startGameLoop();
        setFocusable(true); // Shortcuts möglich machen (Press Key Event)

    }

    private void startGameLoop() {
        gameLoop = new Timer(16, e -> {
            updateGame();
            repaint();
        });
        gameLoop.start();
    }

    private void updateGame() {
        for (int i = 0; i < wave.enemy1.size(); i++) {
            if (wave.enemy1.get(i) != null) {
                if (wave.enemy1.get(i).getX() == 4 && wave.enemy1.get(i).getY() == 13) {
                    health = 0;
                    wave.clearWave();
                    startButton.setEnabled(true);
                    i = wave.enemy1.size();
                }
            }
        }
    }

    private void pauseGame(){

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

        JButton returnButton = new JButton("Menu");
        returnButton.addActionListener(e -> returnToMenu());
        returnButton.setFocusPainted(false);
        returnButton.setForeground(Color.WHITE);
        returnButton.setBackground(new Color(30, 30, 40));
        buttonPanel.add(returnButton);

        // Tower-Auswahl-Leiste
        ImageIcon originalIcon1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-guard.png")));
        Image scaledImage1 = originalIcon1.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        JButton tower1Button = new JButton(scaledIcon1);
        tower1Button.setBounds(1380,8,64,64);
        tower1Button.setOpaque(false);
        tower1Button.setContentAreaFilled(false);
        tower1Button.setBorderPainted(false);
        tower1Button.addActionListener(e -> {
            placingTower = true;
            selectedTowerType = 1;
            repaint();
        });
        add(tower1Button);

        ImageIcon originalIcon2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-ketoon.png")));
        Image scaledImage2 = originalIcon2.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImage2);
        JButton tower2Button = new JButton(scaledIcon2);
        tower2Button.setBounds(1464,10,64,64);
        tower2Button.setOpaque(false);
        tower2Button.setContentAreaFilled(false);
        tower2Button.setBorderPainted(false);
        tower2Button.addActionListener(e -> {
            placingTower = true;
            selectedTowerType = 2;
            repaint();
        });
        add(tower2Button);

        ImageIcon originalIcon3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-priest.png")));
        Image scaledImage3 = originalIcon3.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(scaledImage3);
        JButton tower3Button = new JButton(scaledIcon3);
        tower3Button.setBounds(1545,8,64,64);
        tower3Button.setOpaque(false);
        tower3Button.setContentAreaFilled(false);
        tower3Button.setBorderPainted(false);
        tower3Button.addActionListener(e -> {
            placingTower = true;
            selectedTowerType = 3;
            repaint();
        });
        add(tower3Button);

        ImageIcon originalIcon4 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-archer.png")));
        Image scaledImage4 = originalIcon4.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4= new ImageIcon(scaledImage4);
        JButton tower4Button = new JButton(scaledIcon4);
        tower4Button.setBounds(1624,8,64,64);
        tower4Button.setOpaque(false);
        tower4Button.setContentAreaFilled(false);
        tower4Button.setBorderPainted(false);
        tower4Button.addActionListener(e -> {
            placingTower = true;
            selectedTowerType = 4;
            repaint();
        });
        add(tower4Button);

        ImageIcon originalIcon5 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-trader.png")));
        Image scaledImage5 = originalIcon5.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon5= new ImageIcon(scaledImage5);
        JButton tower5Button = new JButton(scaledIcon5);
        tower5Button.setBounds(1705,8,64,64);
        tower5Button.setOpaque(false);
        tower5Button.setContentAreaFilled(false);
        tower5Button.setBorderPainted(false);
        tower5Button.addActionListener(e -> {
            placingTower = true;
            selectedTowerType = 5;
            repaint();
        });
        add(tower5Button);

        addMouseListener(new Placement(this));

        ImageIcon returnIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/returnToMenu.png")));
        Image returnImage = returnIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledReturnIcon = new ImageIcon(returnImage);
        JButton returnToMenu = new JButton(scaledReturnIcon);
        returnToMenu.setBounds(8, 10, 64, 64);
        returnToMenu.setOpaque(false);
        returnToMenu.setContentAreaFilled(false);
        returnToMenu.setBorderPainted(false);
        returnToMenu.addActionListener(e -> {
            returnToMenu();
        });
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
        startButton.addActionListener(e -> {
            if (!waveStarted && health != 0){
                wave.createWave1();
                startButton.setEnabled(false);
            }
        });
        add(startButton);

        //pauseButton
        ImageIcon pauseIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/pauseButton.png")));
        Image pauseImage = pauseIcon.getImage().getScaledInstance(290, 100, Image.SCALE_SMOOTH);
        JButton pauseButton = new JButton(new ImageIcon(pauseImage));
        pauseButton.setBounds(1255, 960, 290, 100);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setBorderPainted(false);
        pauseButton.addActionListener(e -> {
            //pauseGame Methode einfügen
        });
        add(pauseButton);

        //restartButton
        ImageIcon restartIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/restartButton.png")));
        Image restartImage = restartIcon.getImage().getScaledInstance(290, 100, Image.SCALE_SMOOTH);
        JButton restartButton = new JButton(new ImageIcon(restartImage));
        restartButton.setBounds(1565, 960, 290, 100);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.addActionListener(e -> {
            health = 100;
            money = 1000;
            towers1.clear();
            towers2.clear();
            towers3.clear();
            towers4.clear();
            towers5.clear();
            wave.enemy1.clear();
            //wave.enemy2.clear();
            //wave.enemy3.clear();
            //wave.enemy4.clear();
            //wave.enemy5.clear();

            initGrid();
            startButton.setEnabled(true);
            repaint();
        });
        add(restartButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image grassImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/grass.png"))).getImage();
        Image pathImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/pathway.png"))).getImage();
        Image towerFrame = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/towerFrame.png"))).getImage();
        Image separator = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/separator.png"))).getImage();
        Image gateway2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/Gateway2.png"))).getImage();
        Image banner = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/banner.png"))).getImage();
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if (isPathway[x][y]) {
                    g.drawImage(pathImage, x * CHUNK_SIZE + offsetX, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
                } else {
                    g.drawImage(grassImage, x * CHUNK_SIZE + offsetX, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
                }
            }
        }
        drawGrid(g);
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

        pathFinding.run(g);
        shot.run(g, (Graphics2D) g);

        drawHUD(g,g);
        //draw ButtonBackground
        g.drawImage(towerFrame, 19 * CHUNK_SIZE, 0,null);
        g.drawImage(separator, 80, 0,null);
        g.drawImage(separator,1178,0,null);
        g.drawImage(gateway2,345,14*CHUNK_SIZE,150,70,null);
        g.drawImage(banner,5*CHUNK_SIZE,0,null);

    }

    private void drawGrid(Graphics g) { //teilt map in chunks
        Image placementImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isPlaceable.png"))).getImage();
        Image nonPlaceableImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isNotPlaceable.png"))).getImage();

        if (placingTower) {
        } else {
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
    // TODO Maybe shop hinzufügen
    private void drawHUD(Graphics g, Graphics g2) { // HP und Geld anzeige
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("$" + money, 1220, 40);
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.drawString("♥️" + health, 1220, 80);

      //  g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    }


}