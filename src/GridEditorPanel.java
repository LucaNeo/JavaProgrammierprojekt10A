package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
// Work in Progress 
public class GridEditorPanel extends JPanel {
    private static final int CHUNK_SIZE = 50;
    private static final int rowsGrid = 15;
    private static final int colsGrid = 15;
    private boolean[][] placeable;
    private int currentTileType = 1; // 1 = placeable, 0 = non-placeable
    private boolean editMode = true;
    private JLabel statusLabel;

    public GridEditorPanel() {
        placeable = new boolean[rowsGrid][colsGrid];
        setPreferredSize(new Dimension(colsGrid * CHUNK_SIZE, rowsGrid * CHUNK_SIZE + 30));
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!editMode) return;
                int x = e.getX() / CHUNK_SIZE;
                int y = e.getY() / CHUNK_SIZE;
                if (x >= 0 && x < colsGrid && y >= 0 && y < rowsGrid) {
                    placeable[y][x] = (currentTileType == 1);
                    repaint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_E -> toggleEditMode();
                    case KeyEvent.VK_S -> saveGridToFile();
                    case KeyEvent.VK_L -> loadGridFromFile("grid_0.txt");
                    case KeyEvent.VK_C -> clearGrid();
                    case KeyEvent.VK_1 -> setTileType(1);
                    case KeyEvent.VK_0 -> setTileType(0);
                    case KeyEvent.VK_ESCAPE -> editMode = false;
                }
                repaint();
            }
        });

        statusLabel = new JLabel("Modus: Edit | Typ: 1");
        statusLabel.setForeground(Color.WHITE);
        setLayout(new BorderLayout());
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void toggleEditMode() { 
        editMode = !editMode;
        statusLabel.setText("Modus: " + (editMode ? "Edit" : "View") + " | Typ: " + currentTileType);
    }

    private void setTileType(int type) {
        currentTileType = type;
        statusLabel.setText("Modus: " + (editMode ? "Edit" : "View") + " | Typ: " + currentTileType);
    }

    private void clearGrid() { // stadrt grid herstellen 
        for (int y = 0; y < rowsGrid; y++) {
            for (int x = 0; x < colsGrid; x++) {
                placeable[y][x] = false;
            }
        }
    }

    private void saveGridToFile() { //speichert grid nur in txt datei try fängt Fehler ab  
        File file = new File("grid_0.txt");
        try (PrintWriter writer = new PrintWriter(file)) {
            for (int y = 0; y < rowsGrid; y++) {
                for (int x = 0; x < colsGrid; x++) {
                    writer.print(placeable[y][x] ? "1" : "0");
                }
                writer.println();
            }
            JOptionPane.showMessageDialog(this, "Grid gespeichert als grid_0.txt");
        } catch (IOException ex) { // try + IOExeption ex = Fehler abfangen und in Console ausgeben 
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler beim Speichern des Grids");
        }
    }

    private void loadGridFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (int y = 0; y < rowsGrid; y++) {
                String line = reader.readLine();
                for (int x = 0; x < colsGrid && x < line.length(); x++) {
                    placeable[y][x] = line.charAt(x) == '1';
                }
            }
            repaint();
            JOptionPane.showMessageDialog(this, "Grid geladen aus " + filename);
        } catch (IOException ex) { //Problem abfagen 
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fehler beim Laden des Grids");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < rowsGrid; y++) {
            for (int x = 0; x < colsGrid; x++) {
                g.setColor(placeable[y][x] ? Color.GREEN.darker() : Color.GRAY);
                g.fillRect(x * CHUNK_SIZE, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x * CHUNK_SIZE, y * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE);
            }
        }
    }
}
