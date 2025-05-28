package src;

import java.util.Arrays;
import java.util.Random;

//Autor Basiscode: Luca; Implementierung einzelner Waves: Titus

public class Wave {
    private final GamePanel gamePanel;
    private final Random random = new Random();
    public int waveCompleted = 0;

    public Enemy1[] enemy1 = new Enemy1[10];

    public Wave(GamePanel gamepanel) {
        this.gamePanel = gamepanel;
    }

    public void createWave1(){
        for (int i = 0; i < enemy1.length; i++) {
            double r = random.doubles(1, 1.25).findFirst().getAsDouble();
            enemy1[i] = new Enemy1(2, Double.parseDouble(String.format("%.1f", i * -r).replace(',', '.')), gamePanel); // r = Abstand der Enemies in Chunks
        }
    }

    public void clearWave(){
        //clear Wave 1
        Arrays.fill(enemy1, null);
    }
}
