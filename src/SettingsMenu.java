package src;


import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Collections;
import java.util.Objects;

// Autor: Jakob
public class SettingsMenu extends JPanel {

         private final JFrame parentFrame;
         private final Image backgroundImage;

         public SettingsMenu(JFrame frame, Image backgroundImage) {
             this.parentFrame = frame;
             this.backgroundImage = backgroundImage;

             Font titleFont;

             setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
             setBackground(new Color(30, 30, 40));
             try {
                 titleFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("fonts/SomeCoolFont.ttf"))).deriveFont(72f);
                 // Für Unterstrich-Effekt:
                 titleFont = titleFont.deriveFont(Collections.singletonMap(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DASHED));
             } catch (Exception e) {
                 titleFont = new Font("Arial", Font.BOLD, 48); // fehler abfangen
            }
             // Titel setzen 
             JLabel title = new JLabel("Settings");
             title.setFont(titleFont);
             title.setForeground(new Color(220, 220, 250));
             title.setAlignmentX(Component.CENTER_ALIGNMENT);
             title.setBorder(BorderFactory.createEmptyBorder(80, 0, 40, 0));

             JButton difficultyButton = styleButton("Difficulty");
             difficultyButton.setMultiClickThreshhold(15);
             difficultyButton.addActionListener(_ -> {
                 parentFrame.setContentPane(new DifficultyMenu(frame, backgroundImage));
                 parentFrame.revalidate();
                 parentFrame.repaint();
             });

            //TODO Button-Actions
             JButton backButton = styleButton("Back");
             backButton.setMultiClickThreshhold(15);
             backButton.addActionListener(_ -> {
                 parentFrame.setContentPane(new MainMenu(parentFrame));
                 parentFrame.revalidate();
                 parentFrame.repaint();
             });
             // Zusammensetzen der Komponenten 
             add(title);
             add(difficultyButton);
             add(Box.createRigidArea(new Dimension(0, 20)));
             add(backButton);
         }

         private JButton styleButton(String text) { // mainMenu copy 
             JButton button = new JButton(text);
             button.setAlignmentX(Component.CENTER_ALIGNMENT);
             button.setFont(new Font("Arial", Font.PLAIN, 24));
             button.setForeground(Color.WHITE);
             button.setBackground(new Color(70, 70, 90));
             button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(100, 100, 120), 2), BorderFactory.createEmptyBorder(10, 40, 10, 40)));
             button.setFocusPainted(false);
             return button;
         }

         @Override
         protected void paintComponent(Graphics g) {
             g.drawImage(backgroundImage, 0, 0, null);
         }
}
