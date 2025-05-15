package src;
// Autor Jakob
// TODO (drop down) menü für Maps + Vorschau
// TODO chunk system überarbeiten
// TODO Settings verbessern
// TODO Bild auschneiden für Tower
// TODO Auto speichern
// TODO Grid umbennen und löschen
// TODO  mehrarten von Chunks wasser,weg,plazierbar,unnutzbar
// TODO shortcuts,Undo/Redo
// TODO Autostart button


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamePanel extends JPanel {
    final int CHUNK_SIZE = 50;
    final int rows = 15;
    final int cols = 15;

    public boolean[][] placeable;
    public boolean[][] isPathway;
    public boolean placingTower = false;  // Ist Platzierungsmodus aktiv?
    private final boolean gridEditorMode = false;
    final List<Tower1> towers1 = new ArrayList<>();
    final List<Tower2> towers2 = new ArrayList<>();
    final List<Tower3> towers3 = new ArrayList<>();
    final List<Tower4> towers4 = new ArrayList<>();
    final List<Tower5> towers5 = new ArrayList<>();
    private Graphics graphic;

    private final List<Enemy> enemies = new ArrayList<>();
    private Timer gameLoop; // aktive runde ?
    int money = 10000; // StartGeld
    int health = 100; //hp
    int selectedTowerType = 0;     // 1 = Tower1, 2 = Tower2 etc.

    private JFrame parentFrame;

    public GamePanel(JFrame frame) {
        this.parentFrame = frame;
        initGrid();
        setUI();
        startGameLoop();
        setFocusable(true); // Shortcuts möglich machen (Press Key Event)
    }

    public GamePanel() {
        initGrid();
        setUI();
        startGameLoop();
    }

    private void startGameLoop() {
        gameLoop = new Timer(16, e -> {
            updateGame();
            repaint();
        });
        gameLoop.start();
    }

    private void updateGame() {
        for (Enemy e : enemies) {
            e.move(); // Einfache Bewegungen der Gegner
        }
    }

    // public void initGrid() {
    //    placeable = new boolean[rows][cols];
    //    for (int y = 0; y < rows; y++) {
    //        for (int x = 0; x < cols; x++) {
    //            placeable[y][x] = (y % 2 == 0); // Zeilenweise
    //        }
    //    }
    //}

    private void initGrid() {
        placeable = new boolean[cols][rows];
        isPathway = new boolean[cols][rows];
        Image grassIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/grass.png"))).getImage();
        Image pathIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/pathway.png"))).getImage();
        Graphics2D g = (Graphics2D) parentFrame.getContentPane().getGraphics();

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
        // final image assignment
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if (isPathway[x][y]) {
                    g.drawImage(pathIcon, x * CHUNK_SIZE, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
                } else {
                    g.drawImage(grassIcon, x * CHUNK_SIZE, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
                }
            }
        }
    }

    private void returnToMenu() {
        if (gameLoop != null) gameLoop.stop();
        parentFrame.setContentPane(new MainMenu(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void setUI() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(70, 70, 90));
        buttonPanel.setPreferredSize(new Dimension(getWidth()+50, getHeight()-100));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
        add(buttonPanel,BorderLayout.SOUTH);

        JButton returnButton = new JButton("Menu");
        returnButton.addActionListener(e -> returnToMenu());
        returnButton.setFocusPainted(false);
        returnButton.setForeground(Color.WHITE);
        returnButton.setBackground(new Color(30, 30, 40));
        buttonPanel.add(returnButton);

//        JButton gridEditorButton = new JButton("EditGridMode");
//        gridEditorButton.setFocusPainted(false);
//        gridEditorButton.setForeground(Color.WHITE);
//        gridEditorButton.setBackground(new Color(30, 30, 40));
//        buttonPanel.add(gridEditorButton);
//        gridEditorButton.addActionListener(e -> {
//            gridEditorMode = !gridEditorMode;
//            if (gridEditorMode) {
//                parentFrame.setContentPane(new GridEditorPanel());
//                parentFrame.revalidate();
//                parentFrame.repaint();
//            }
//        });






//        // zurück Button
//        setLayout(null);
//        JButton backButton = new JButton("← Menu");
//        backButton.setBounds(10, 10, 100, 30);
//        backButton.addActionListener(e -> returnToMenu());
//        add(backButton);
//
//        JButton gridEditorButton = new JButton("Edit Grid");
//        gridEditorButton.setBounds(150, 10, 100, 30);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gridEditorMode) {
                    int x = e.getX() / CHUNK_SIZE;
                    int y = e.getY() / CHUNK_SIZE;

                    if (x >= 0 && x < cols && y >= 0 && y < rows) {
                        placeable[y][x] = !placeable[y][x]; // toggle
                        repaint();
                    }
                }
            }
        });

//        gridEditorButton.addActionListener(e -> {
//            gridEditorMode = !gridEditorMode;
//            gridEditorButton.setText(gridEditorMode ? "Play Mode" : "Edit Grid");
//        });
//        add(gridEditorButton);




        // Tower-Auswahl-Leiste
        ImageIcon originalIcon1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-guard.png")));
        Image scaledImage1 = originalIcon1.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon1 = new ImageIcon(scaledImage1);
        JButton tower1Button = new JButton(scaledIcon1);
        tower1Button.setBounds(returnButton.getWidth() + 100,  parentFrame.getHeight() - 100, 64, 64);
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
        tower2Button.setBounds(returnButton.getWidth()+170, parentFrame.getHeight() -100, 64, 64);
        tower2Button.addActionListener(e -> {
            placingTower = true;
            selectedTowerType = 2;
            repaint();
        });
        add(tower2Button);

        ImageIcon originalIcon3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-priest.png")));
        Image scaledImage3 = originalIcon3.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon3 = new ImageIcon(scaledImage3);
        JButton tower3Button = new JButton(scaledIcon3);;
        tower3Button.setBounds(returnButton.getWidth()+240, parentFrame.getHeight() -100, 64, 64);
        tower3Button.addActionListener(e -> {
            placingTower = true;
            selectedTowerType = 3;
            repaint();
        });
        add(tower3Button);

        ImageIcon originalIcon4 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isometric-guard.png")));
        Image scaledImage4 = originalIcon4.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon4= new ImageIcon(scaledImage4);
        JButton tower4Button = new JButton(scaledIcon4);
        tower4Button.setBounds(returnButton.getWidth()+310, parentFrame.getHeight() -100, 64, 64);
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
        tower5Button.setBounds(returnButton.getWidth()+380, parentFrame.getHeight() -100, 64, 64);
        tower5Button.addActionListener(e -> {
            placingTower = true;
            selectedTowerType = 5;
            repaint();
        });
        add(tower5Button);

        // Save Grid Button
        JButton saveButton = new JButton("Save Grid");
        saveButton.setBounds(390, 10, 120, 30);
        saveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
               // saveGridToFile(chooser.getSelectedFile());
            }
        });
        add(saveButton);
        // load Grid Button
        JButton loadButton = new JButton("Load Grid");
        loadButton.setBounds(500, 10, 120, 30);
        loadButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
              //  loadGridFromFile(chooser.getSelectedFile());
            }
        });
        add(loadButton);


        addMouseListener(new Placement(this));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        //  Türme zeichnen
        for (Tower5 tower1 : towers5) {
            tower1.draw(g, CHUNK_SIZE);
        }
        for (Tower2 tower2 : towers2) {
            tower2.draw(g, CHUNK_SIZE);
        }
        for (Tower3 tower3 : towers3) {
            tower3.draw(g, CHUNK_SIZE);
        }
        for (Tower4 tower4 : towers4) {
            tower4.draw(g, CHUNK_SIZE);
        }
        for (Tower5 tower5 : towers5) {
            tower5.draw(g, CHUNK_SIZE);
        }

        // Gegner zeichnen (falls vorhanden)
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        drawHUD(g,g);
    }

    private void drawGrid(Graphics g) { //teilt map in chunks
        Image placementImage;
        ImageIcon placementIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/textures/isPlaceable.png")));
        placementImage = placementIcon.getImage();
        if (placingTower) {
        } else {
            return;
        }
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if (placeable[x][y]) {
                    g.drawImage(placementImage, x * CHUNK_SIZE, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE, null);
                }
            }
        }
    }
    // TODO Maybe shop hinzufügen
    private void drawHUD(Graphics g, Graphics g2) { // HP und Geld anzeige
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("$" + money, 20, getHeight() - 30);
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("♥️" + health, 90, getHeight() - 30);

      //  g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    }
    // TODO files sollen im Projekt gespeichrt werden und nicht im Explorer
    public static boolean saveGrid(int[][] grid, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            // Schreibe Grid-Inhalt
            for (int[] row : grid) {
                for (int cell : row) {
                    writer.write(cell + " ");
                }
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern des Grids: " + e.getMessage());
            return false;
        }
    }
    // TODO files sollen im Projekt geladen werden und nicht im Explorer

    public static int[][] loadGrid(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Lese Dimensionen
            String[] dimensions = reader.readLine().split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);

            // Erstelle Grid
            int[][] grid = new int[rows][cols];

            // Fülle Grid
            for (int i = 0; i < rows; i++) {
                String[] values = reader.readLine().trim().split(" ");
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = Integer.parseInt(values[j]);
                }
            }
            return grid;
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Fehler beim Laden des Grids: " + e.getMessage());
            return null;
        }
    }





}
