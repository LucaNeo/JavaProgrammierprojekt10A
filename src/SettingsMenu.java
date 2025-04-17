package src;


import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Collections;

public class SettingsMenu extends JPanel {
         private final JFrame parentFrame;

         public SettingsMenu(JFrame frame) {
             this.parentFrame = frame;
             setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
             setBackground(new Color(30, 30, 40));
             Font titleFont;
             try {
                 titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("fonts/SomeCoolFont.ttf"))
                         .deriveFont(72f);
                 // Für Unterstrich-Effekt:
                 titleFont = titleFont.deriveFont(Collections.singletonMap(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DASHED));
             } catch (Exception e) {
                 titleFont = new Font("Arial", Font.BOLD, 48);
            }
             // Titel setzen 
             JLabel title = new JLabel("Settings");
             title.setFont(new Font("Arial", Font.BOLD, 36));
             title.setForeground(Color.WHITE);
             title.setAlignmentX(Component.CENTER_ALIGNMENT);
             title.setBorder(BorderFactory.createEmptyBorder(80, 0, 40, 0));

             JButton soundButton = styleButton("Sound");
             JButton difficultyButton = styleButton("Difficulty");
             JButton AutoStartButton = styleButton("Auto-Start");

            //TODO Button-Actions
             JButton backButton = styleButton("Back");
             backButton.addActionListener(e -> {
                 parentFrame.setContentPane(new MainMenu(parentFrame));
                 parentFrame.revalidate();
                 parentFrame.repaint();
             });
             // Zusammensetzen der Komponenten 
             add(title);
             add(soundButton);
             add(Box.createRigidArea(new Dimension(0, 20)));
             add(difficultyButton);
             add(Box.createRigidArea(new Dimension(0, 20)));
             add(AutoStartButton);
             add(Box.createRigidArea(new Dimension(0, 20)));
             add(backButton);

         }

         private JButton styleButton(String text) { // mainMenu copy 
             JButton button = new JButton(text);
             button.setAlignmentX(Component.CENTER_ALIGNMENT);
             button.setFont(new Font("Arial", Font.PLAIN, 24));
             button.setForeground(Color.WHITE);
             button.setBackground(new Color(70, 70, 90));
             button.setBorder(BorderFactory.createCompoundBorder(
                     BorderFactory.createLineBorder(new Color(100, 100, 120), 2),
                     BorderFactory.createEmptyBorder(10, 40, 10, 40)));
             button.setFocusPainted(false);
             return button;
         }




}
