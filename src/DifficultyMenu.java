package src;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Collections;
import java.util.Objects;

public class DifficultyMenu extends JPanel {

    private final JFrame parentFrame;
    //   private Image backgroundImage;
        private Font titleFont;
       


        public DifficultyMenu(JFrame frame) {
            this.parentFrame = frame;
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
            JLabel title = new JLabel("Difficulty");
            title.setFont(titleFont);
            title.setForeground(new Color(220, 220, 250));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            title.setBorder(BorderFactory.createEmptyBorder(80, 0, 100, 0));
            // Menü-Buttons
            JButton easyButton = createMenuButton("Easy");
            JButton mediumButton = createMenuButton("Medium");
            JButton hardButton = createMenuButton("Hard");
            JButton backButton = createMenuButton("Back");

            // Button-Actions
            easyButton.addActionListener(e -> {
                frame.setContentPane(new SettingsMenu(frame)); // Wechsel zum Spiel
                frame.revalidate();
                DifficultySettings.setCurrentDifficulty(Difficulty.EASY);

            });
            mediumButton.addActionListener(e -> {
                frame.setContentPane(new SettingsMenu(frame)); // Wechsel zu Settings
                frame.revalidate();
                DifficultySettings.setCurrentDifficulty(Difficulty.MEDIUM);
            });
            hardButton.addActionListener(e -> {
                frame.setContentPane(new SettingsMenu(frame));
                frame.revalidate();
                DifficultySettings.setCurrentDifficulty(Difficulty.HARD);
            });

            backButton.addActionListener(e -> {
                parentFrame.setContentPane(new SettingsMenu(parentFrame));
                parentFrame.revalidate();
                parentFrame.repaint();});


            // Zusammensetzen von    Komponenten
            add(title);
            add(easyButton);
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(mediumButton);
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(hardButton);
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(backButton);
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
