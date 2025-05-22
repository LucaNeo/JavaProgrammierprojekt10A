//package src;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//// Work in Progress
//public class GridEditorPanel extends JPanel {
//    private static final int CHUNK_SIZE = 50;
//    private static final int rowsGrid = 15;
//    private static final int colsGrid = 15;
//    private boolean[][] placeable;
//    private int currentTileType = 1; // 1 = placeable, 0 = non-placeable
//    private boolean editMode = true;
//    private JLabel statusLabel;
//
//    public GridEditorPanel() {
//        placeable = new boolean[rowsGrid][colsGrid];
//        setPreferredSize(new Dimension(colsGrid * CHUNK_SIZE, rowsGrid * CHUNK_SIZE + 30));
//        setFocusable(true);
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (!editMode) return;
//                int x = e.getX() / CHUNK_SIZE;
//                int y = e.getY() / CHUNK_SIZE;
//                if (x >= 0 && x < colsGrid && y >= 0 && y < rowsGrid) {
//                    placeable[y][x] = (currentTileType == 1);
//                    repaint();
//                }
//            }
//        });
//
//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                switch (e.getKeyCode()) {
//
//                    case KeyEvent.VK_C -> clearGrid();
//                    case KeyEvent.VK_1 -> setTileType(1);
//                    case KeyEvent.VK_0 -> setTileType(0);
//                    case KeyEvent.VK_ESCAPE -> editMode = false;
//                }
//                repaint();
//            }
//        });
//
//        statusLabel = new JLabel("Modus: Edit | Typ: 1");
//        statusLabel.setForeground(Color.WHITE);
//        setLayout(new BorderLayout());
//        add(statusLabel, BorderLayout.SOUTH);
//    }
//
//    private void toggleEditMode() {
//        editMode = !editMode;
//        statusLabel.setText("Modus: " + (editMode ? "Edit" : "View") + " | Typ: " + currentTileType);
//    }
//
//    private void setTileType(int type) {
//        currentTileType = type;
//        statusLabel.setText("Modus: " + (editMode ? "Edit" : "View") + " | Typ: " + currentTileType);
//    }
//
//
