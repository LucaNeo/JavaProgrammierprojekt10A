package src;


import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Collections;
import java.util.Objects;

public class SettingsMenu extends JPanel {
         private final JFrame parentFrame;

         public SettingsMenu(JFrame frame) {
             this.parentFrame = frame;
             setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
             setBackground(new Color(30, 30, 40));
             Font titleFont;
             try {
                 titleFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("fonts/SomeCoolFont.ttf")))
                         .deriveFont(72f);
                 // Für Unterstrich-Effekt:
                 titleFont = titleFont.deriveFont(Collections.singletonMap(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DASHED));
             } catch (Exception e) {
                 titleFont = new Font("Arial", Font.BOLD, 48); // fehler abfangen
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

             difficultyButton.addActionListener(e -> showDifficultyDialog());

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
         private void showDifficultyDialog() {
             String[] options = { "Easy", "Medium", "Hard" };
             int choice = JOptionPane.showOptionDialog(
                     parentFrame,
                     "Select Difficulty :",
                     "Difficulty Settings",
                     JOptionPane.DEFAULT_OPTION,
                     JOptionPane.QUESTION_MESSAGE,
                     null,
                     options,
                     options[1]
             );
             switch (choice) {
                 case 0 -> DifficultySettings.setDifficulty(Difficulty.EASY);
                 case 1 -> DifficultySettings.setDifficulty(Difficulty.MEDIUM);
                 case 2 -> DifficultySettings.setDifficulty(Difficulty.HARD);
                 default -> {}//nichts ändern wenn abruch
             }
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
