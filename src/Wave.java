package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Autor Basiscode: Luca; Implementierung einzelner Waves: Titus

public class Wave {
    private final Random random = new Random();
    public int waveCompleted = 0;

    final List<Enemy> enemy1 = new ArrayList<>();

    public Wave() {
    }

    public void createWave1(){
        for (int i = 0; i < 10; i++) {
            double r = random.doubles(1, 1.25).findFirst().getAsDouble();
            enemy1.add(new Enemy2(2, Double.parseDouble(String.format("%.1f", i * -r).replace(',', '.')))); // r = Abstand der Enemies in Chunks
        }
    }

    public void clearWave(){
        //clear Wave 1
        enemy1.clear();
        waveCompleted++;
    }
}
