package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Autor Basiscode: Luca, Implementierung der Waves: Titus

public class Wave {

    private final Random random = new Random();
    private int waveCompleted = 0;

    private final List<Enemy> enemy = new ArrayList<>();

    public Wave() {}

    public void createWave1() {
        for (int i = 0; i < 10; i++) {
            double r = random.doubles(1, 1.25).findFirst().getAsDouble();
            enemy.add(new Enemy1(2, Double.parseDouble(String.format("%.1f", i * -r).replace(',', '.')))); // r = Abstand der Enemies in Chunks
        }
    }

    public void createWave2() {
        for (int i = 0; i < 13; i++) {
            if (Math.random() < 0.7) {
                double r = random.doubles(1, 1.25).findFirst().getAsDouble();
                enemy.add(new Enemy2(2, Double.parseDouble(String.format("%.1f", i * -r).replace(',', '.')))); // r = Abstand der Enemies in Chunks
            } else {
                double r = random.doubles(1, 1.25).findFirst().getAsDouble();
                enemy.add(new Enemy1(2, Double.parseDouble(String.format("%.1f", i * -r).replace(',', '.')))); // r = Abstand der Enemies in Chunks
            }
        }
    }

    public void createWave3() {
        for (int i = 0; i < 15; i++) {
            if (Math.random() < 0.5) {
                double r = random.doubles(1, 1.25).findFirst().getAsDouble();
                enemy.add(new Enemy2(2, Double.parseDouble(String.format("%.1f", i * -r).replace(',', '.')))); // r = Abstand der Enemies in Chunks
            } else {
                double r = random.doubles(1, 1.25).findFirst().getAsDouble();
                enemy.add(new Enemy1(2, Double.parseDouble(String.format("%.1f", i * -r).replace(',', '.')))); // r = Abstand der Enemies in Chunks
            }
        }
    }

    public void createWave4() {
        for (int i = 0; i < 19; i++) {
            if (Math.random() < 0.2) {
                double r = random.doubles(1, 1.25).findFirst().getAsDouble();
                enemy.add(new Enemy2(2, Double.parseDouble(String.format("%.1f", i * -r).replace(',', '.')))); // r = Abstand der Enemies in Chunks
            } else {
                double r = random.doubles(1, 1.25).findFirst().getAsDouble();
                enemy.add(new Enemy1(2, Double.parseDouble(String.format("%.1f", i * -r).replace(',', '.')))); // r = Abstand der Enemies in Chunks
            }
        }
    }

    public void createWave5() {
        for (int i = 0; i < 1; i++) {
            double r = random.doubles(1, 1.25).findFirst().getAsDouble();
            enemy.add(new Enemy3(2, Double.parseDouble(String.format("%.1f", i * -r).replace(',', '.')))); // r = Abstand der Enemies in Chunks
        }
    }

    public void clearWave(){
        enemy.clear();
        waveCompleted++;
    }

    public void removeEnemy(Enemy e) {
        enemy.remove(e);
    }

    public Enemy getSpecificEnemy(int index) { return enemy.get(index); }
    public List getEnemyArrayList() { return enemy; }
    public int getWavesCompleted(){ return waveCompleted; }
    public void setWavesCompleted(int value) { waveCompleted = value; }
}
