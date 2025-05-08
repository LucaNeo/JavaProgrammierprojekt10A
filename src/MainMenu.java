package src;
// Autor Jakob
import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Collections;
import java.util.Objects;

public class MainMenu extends JPanel {
 //   private Image backgroundImage;
    private Font titleFont;


    public MainMenu(JFrame frame) {
        // Layout & Hintergrund
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(30, 30, 40));


        // Titelstil mit Custom Font //Tower defens GROß
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("fonts/SomeCoolFont.ttf")))
                    .deriveFont(72f);
            // Für Unterstrich-Effekt:
            titleFont = titleFont.deriveFont(Collections.singletonMap(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DASHED));
        } catch (Exception e) {
            titleFont = new Font("Arial", Font.BOLD, 48);
        }
    //Titel setzen
        JLabel title = new JLabel("Tower Defense");
        title.setFont(titleFont);
        title.setForeground(new Color(220, 220, 250));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(80, 0, 100, 0));
        // Menü-Buttons
        JButton startButton = createMenuButton("Start Game");
        JButton settingsButton = createMenuButton("Settings");
        JButton exitButton = createMenuButton("Exit");

        // Button-Actions
        startButton.addActionListener(e -> {
            frame.setContentPane(new GamePanel(frame)); // Wechsel zum Spiel
            frame.revalidate();
        });
        settingsButton.addActionListener(e -> {
            frame.setContentPane(new SettingsMenu(frame)); // Wechsel zu Settings
            frame.revalidate();
        });

        exitButton.addActionListener(e -> System.exit(0));

        // Zusammensetzen von    Komponenten
        add(title);
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(settingsButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(exitButton);
    }
// optimierter button für Settings/Menu
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 70, 90));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 120),2),
                BorderFactory.createEmptyBorder(10, 40, 10, 40)));
        button.setFocusPainted(false);
    return button;
    }


}
